# bacnet-commands-cli
Find devices, read their names and get present value from sensors.

## Installation

```
mvn clean install
./install.sh
```

## Getting Started

1. Start terminal window
```
bacnet listen -ip="<your ip address>"
```
2. Start another terminal window
```
bacnet devices find <local broadcast address>
eg:
bacnet devices find 192.168.2.255
or
bacnet devices -ip=192.168.2.255 find
```

3. Terminal window no 1:
```
list
```
Expect output similar to:

```
List of Devices:
instance: null, ipAddress: 192.168.2.116, port: 47808, lastSeen: 2021-04-01T05:50:11.222019Z
instance: 8, ipAddress: 192.168.2.118, port: 47808, lastSeen: 2021-04-01T05:50:11.485128Z
```

4. Terminal window no 2:
```
bacnet devices objectName -ip=192.168.2.118 -i=8
```
5. Terminal window no 1:
Repeat the `list` command.
Expect the list to be updated with the objectName from device with instanceId 8.


## Subscribe to observations

Subscribe to Change Of Value from a output named identified as AnalogValue, instance 1 on bacnet device on ip 192.168.2.118
``` 
bacnet observations subscribe -ip=192.168.2.118 analogValue 1
```

