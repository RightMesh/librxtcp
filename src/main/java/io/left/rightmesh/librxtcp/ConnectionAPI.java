package io.left.rightmesh.librxtcp;

import java.nio.ByteBuffer;

import io.reactivex.Flowable;
import io.reactivex.Observable;

/**
 * Class Connection to send and receive ByteBuffer to a peer over TCP in a reactive way.
 *
 * @author Lucien Loiseau on 26/10/18.
 */
public interface ConnectionAPI {

    interface TrackOrder {
        Observable<Integer> observe();

        boolean cancel();
    }

    /**
     * return local host address for this connection
     *
     * @return tring
     */
    String getLocalHost();

    /**
     * return remote host address for this connection
     *
     * @return tring
     */
    String getRemoteHost();

    /**
     * return local port for this connection
     *
     * @return int
     */
    int getLocalPort();

    /**
     * return remote port for this connection
     *
     * @return int
     */
    int getRemotePort();

    /**
     * Close the current connection.
     */
    void closeNow();

    /**
     * Close the current connection.
     */
    void closeJobsDone();

    /**
     * return an Observable for the stream of ByteBuffer read from the socket. It will not be
     * reading the socket until an Observer subscribed to the stream. Note that there can be
     * only one Observer at any given time!
     * <p>
     * <p>The subscriber should try to return as fast as possible as the onNext() event is
     * emitted in the NIO thread. No read nor write operation can be performed until the method
     * returns
     *
     * @return Observable ByteBuffer stream read from the socket
     */
    Observable<ByteBuffer> recv();

    TrackOrder order(byte[] buffer);

    TrackOrder order(ByteBuffer buffer);

    /**
     * Order a job to be transmitted. It will not actually be queued until an Observer
     * subscribed to the jobhandle observe() method.
     *
     * @param job Flowable of ByteBuffer to sendBundle over the socket
     * @return an Observable to keep track of bytes sent
     */
    TrackOrder order(Flowable<ByteBuffer> job);


    void send(byte[] buffer);

    void send(ByteBuffer buffer);

    /**
     * order a job without actually tracking the order.
     *
     * @param job
     */
    void send(Flowable<ByteBuffer> job);

}
