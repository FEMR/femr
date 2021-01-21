import speedtest
import subprocess
import psutil
import time

suffixes = ['b', 'kb', 'mb', 'gb', 'tb', 'pb']
def humansize(nbytes):
	i = 0
	while nbytes >= 1024 and i < len(suffixes)-1:
	  nbytes /= 1024.
	  i += 1
	f = ('%.2f' % nbytes).rstrip('0').rstrip('.')
	return '%s %s' % (f, suffixes[i])
	
def convert_to_mbit(value):
	return value/1024./1024.*8

def send_stat(value):
	print ("%0.2f" % convert_to_mbit(value))

def main():
	old_value = 0  
	total = 0  
	i = 0
	while i <= 10:
		new_value = psutil.net_io_counters().bytes_sent + psutil.net_io_counters().bytes_recv

		if old_value:
			send_stat(new_value - old_value)
			total += (new_value - old_value)

		old_value = new_value

		time.sleep(1)
		i += 1
		
	average_bandwidth = total/10
	print("Average bandwidth: %0.2f mb" % convert_to_mbit(average_bandwidth))
		
	st = speedtest.Speedtest()

	servernames = []
	st.get_servers(servernames)

	down = humansize(st.download())
	up = humansize(st.upload())
	ping = st.results.ping

	out = (f"""Download {down}ps""") + "\n" + (f"""Upload: {up}ps""") + "\n" +(f"""Ping: {ping} ms""")

	subprocess.Popen(['notify-send', '--icon=error', 'RESULTS', out])

main()
