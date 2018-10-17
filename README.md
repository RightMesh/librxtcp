# librxtcp

[![](https://jitpack.io/v/RightMesh/librxtcp.svg)](https://jitpack.io/#RightMesh/librxtcp)

RxTCP is a small single-threaded TCP library using Java NIO and RxJava. You can use this library
in your project with gradle using jitpack 

```java
repositories {
    maven { url 'https://jitpack.io' }
}
```

```java
dependencies {
   implementation 'com.github.RightMesh:librxtcp:master-SNAPSHOT'
}
```

# create a RxTCP.Server 

To run a TCP server on a given port just do the following:

```java
RxTCP.Server<RxTCP.Connection> s = new RxTCP.Server<>(port);
s.start().subscribe(
        connection -> {
            /* connection is a new client socket */
        },
        e -> {
            /* the server has stopped */
        },
        () -> {
            /* the server has stopped */
        }
        );
```

You can also provide a factory to the server that must create a RxTCP.Connection object:

```java
RxTCP.Server<MyOwnTCPConnection> s = new RxTCP.Server<>(port, MyOwnTCPConnection::new);
s.start().subscribe(
        connection -> {
            /* connection is a new client socket of type MyOwnTCPConnection */
        },
        e -> {
            /* the server has stopped */
        },
        () -> {
            /* the server has stopped */
        }
        );
```

# create a client with RxTCP.ConnectionRequest

to connect to a TCP Server do the following:

```java
    new RxTCP.ConnectionRequest<>(inetAddress.getHostAddress(), port)
    .connect()
    .subscribe(
            connection -> {
                /* connection socket of type RxTCP.Connection */
            },
            e -> {
                /* connection failed */
            });
```

similarly, you can also provide a Fqactory:

```java
    new RxTCP.ConnectionRequest<>(inetAddress.getHostAddress(), port, MyOwnTCPConnection::new)
    .connect()
    .subscribe(
            connection -> {
                /* connection socket of type MyOwnTCPConnection */
            },
            e -> {
                /* connection failed */
            });
```

# send and recv data using RxTCP.Connection

## Receiving ByteBuffer

you can recv data using recv:

```java
con.recv().subscribe(
                    byteBuffer -> {
                        /* new bytebuffer received */
                    },
                    e -> {
                        /* connection has closed */
                    },
                    () -> {
                        /* connection has closed
                    });
```

few points:
* the same ByteBuffer is reused for every new packet received so you must not modify it outside of this thread.
* the callback for the bytebuffer runs on the NIO single thread so any processing that happens here
  must be kept minimal and returns as soon as possible to prevent slowing down the other sockets.
* when recv terminates, it means that the TCP connection has closed.

## Sending ByteBuffer

You can send ByteBuffer like so:

```java
    ByteBuffer b = ByteBuffer.wrap("Hello World".getBytes());
    con.order(b).subscribe(
        i -> {
            /* track the number of bytes sent from this order */
        },
        e -> {
            /* an error happened during transmission */
        },
        () -> {
            /* transmission done */
        }
```

Every order returns an Observable that you can use to track the order. If you do not subscribe to this Observable, no packets are sent.
you can also order a Flowable:

```java
    ByteBuffer b = Flowable.just(ByteBuffer.wrap("Hello World".getBytes()));
    con.order(b).subscribe(
        i -> {
            /* track the number of bytes sent from this order */
        },
        e -> {
            /* an error happened during transmission */
        },
        () -> {
            /* transmission done */
        }
```

# License

    Copyright 2018 Lucien Loiseau

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


