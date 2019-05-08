@echo off
netsh interface ip set address "Ethernet" source = dhcp
ipconfig /renew
netsh interface ip set dns "Ethernet" source = dhcp
netsh interface ip show addresses
netsh interface ip set address "Ethernet" static 192.168.1.112 255.255.255.0 192.168.0.0 1
netsh interface ip add dns "Ethernet" 192.168.1.1
netsh interface ip add dns "Ethernet" 192.168.1.1 index=1
@pause
