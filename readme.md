# Simple Open Port Checker

## Restrictions

Only UDP is supported for now.

## How to use

### Run a server

     java -jar sopc-server.jar [port number]

Default port number is 12345.

### Run a client

    java -jar sopc-client.jar [server hostname] [server port number] [Interval of each request(in Millis)]

Default hostname is "127.0.0.1", port number is 12345 and inverval is 10000 msec.

Client sends ping message to server at a specified interval and server echoes back to client if received message from client. 

If client received response from server successfully, then prints following message.

    Received response. RTT: 21 millis.
