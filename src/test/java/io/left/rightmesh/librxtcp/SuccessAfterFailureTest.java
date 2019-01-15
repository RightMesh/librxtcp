package io.left.rightmesh.librxtcp;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Lucien Loiseau on 15/01/19.
 */
public class SuccessAfterFailureTest {
    
    private String[] hosts = {"nonexistent", "127.0.0.1"};

    private RxTCP.Server<RxTCP.Connection> server;

    @Test
    public void testNonExistingHost() throws InterruptedException {
        server = new RxTCP.Server<>(8080);
        server.start().subscribe(
                connection -> {
                    System.out.println("[+] Server: connection from: " + connection.getRemoteHost());
                },
                error -> {
                    System.out.println("[!] Server stopped");
                }
        );

        final int[] count = new int[1];

        for (int i = 0; i < hosts.length; i++) {
            CountDownLatch latch = new CountDownLatch(1);
            System.out.println("[+] tries to connect to: " + hosts[i]);
            new RxTCP.ConnectionRequest<>(hosts[i], 8080).connect()
                    .subscribe(
                            connection -> {
                                count[0]++;
                                System.out.println("[+] Connection succeed to: " + connection.getRemoteHost());
                                latch.countDown();
                            },
                            error -> {
                                System.out.println("[!] Error on subscribing to an outgoing connection: " + error.getMessage());
                                latch.countDown();
                            });
            latch.await(5, TimeUnit.SECONDS);
        }

        assertEquals(1, count[0]);

        server.stop();
    }
}
