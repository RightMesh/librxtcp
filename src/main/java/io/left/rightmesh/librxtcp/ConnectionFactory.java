package io.left.rightmesh.librxtcp;

import java.io.IOException;

/**
 * Connection factory, this is used by the server to create a new Connection whenever a client
 * is connected.
 *
 * @author Lucien Loiseau on 26/10/18.
 */

public interface ConnectionFactory<T extends RxTCP.Connection> {

    T create() throws IOException;

}