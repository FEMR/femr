import speedtest
import psutil
import time

import tkinter
from tkinter import messagebox

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

def bandwidth():
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



def main():
	bandwidth()

	st = speedtest.Speedtest()

	servernames = []
	st.get_servers(servernames)

	down = humansize(st.download())
	up = humansize(st.upload())
	ping = st.results.ping

	out = (f"""Your current internet speed:""") + "\n\n" + (f"""Download {down}ps""") + "\n" + (f"""Upload: {up}ps""") + "\n" +(f"""Ping: {ping} ms""") + "\n\n" + (f"""Would you like to merge your local data?""")


	# This code is to hide the main tkinter window
	root = tkinter.Tk()
	root.withdraw()

	root.option_add('*Dialog.msg.font', 'Helvetica 12')
	msgbox = tkinter.messagebox.askquestion ('Database merge', out, icon = 'warning')

	if (msgbox == "yes"):
		# merge data
		print("yes")
	else:
		# dont merge data
		print("no")

main()
