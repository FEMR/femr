START cmd.exe /C "netsh wlan set hostednetwork mode=allow ssid=fEMR key=12345678"
START cmd.exe /C "netsh wlan start hostednetwork"
START cmd.exe /C "netsh interface ip set address "Local Area Connection* 12" static 192.168.0.1 255.255.255.0"
