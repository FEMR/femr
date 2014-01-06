START cmd.exe /C "netsh wlan set hostednetwork mode=allow ssid=fEMR key=12345678"
START cmd.exe /C "netsh wlan start hostednetwork"
START cmd.exe /C "netsh interface ip set address "Wireless Network Connection 4" static 169.254.0.1 255.255.0.0"
