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
bacnet listen
```
2. Start another terminal window
```
bacnet devices find <local broadcast address>
eg:
bacnet devices find 192.168.2.255
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

