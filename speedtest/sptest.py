import speedtest
import psutil #not pipinstalled
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

def bandwidth(numSeconds):
	old_value = 0
	total = 0
	i = 0
	while i <= numSeconds:
		new_value = psutil.net_io_counters().bytes_sent + psutil.net_io_counters().bytes_recv

		if old_value:
			total += (new_value - old_value)

		old_value = new_value

		time.sleep(1)
		i += 1

	average_bandwidth = total/numSeconds

	return average_bandwidth

def main():

	try:
		st = speedtest.Speedtest()

		servernames = []
		st.get_servers(servernames)

		down = humansize(st.download())
		up = humansize(st.upload())
		ping = st.results.ping

		out = (f"""{down}\n{up}\n{ping}""")
		print(out)

	except speedtest.ConfigRetrievalError:
		print(f"""{0.0}\n{0.0}\n{0.0}""")

main()
