package io.left.rightmesh.librxtcp;

import io.reactivex.Single;

/**
 * ConnectionRequestAPI connects to a TCP ServerAPI.
 *
 * @author Lucien Loiseau on 26/10/18.
 */
public interface ConnectionRequestAPI<T extends RxTCP.Connection> {

    /**
     * connect() will perform the connection logic and return a new Connection upon success
     * or an error if it fails. The onSuccess event is emitted in a new Thread so as not to
     * block the NIO Thread.
     *
     * @return a new Connection upon success, an error otherwise
     */
    Single<T> connect();
}
