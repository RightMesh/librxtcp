package io.left.rightmesh.librxtcp;

import io.reactivex.Observable;

/**
 * A Non-Blocking Reactive TCP ServerAPI using NIOEngine event loop.
 *
 * @author Lucien Loiseau on 26/10/18.
 */
public interface ServerAPI<T extends RxTCP.Connection> {

    /**
     * create a new Observable that whenever subscribed to, starts the server and
     * emits a new Connection every time a client connects to the server.
     * The new connection event is emitted in a new thread so as not to block the NIO thread.
     *
     * @return an Observable to keep track of every new Connection
     */
    Observable<T> start();

    /**
     * return the TCP port this server is listening to. returns -1 if the server is not started.
     *
     * @return
     */
    int getPort();


    /**
     * stop the server from listening for connection.
     */
    void stop();
}
