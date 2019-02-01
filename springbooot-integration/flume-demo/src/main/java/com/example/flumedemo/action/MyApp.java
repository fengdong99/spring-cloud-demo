package com.example.flumedemo.action;


import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.api.RpcClient;
import org.apache.flume.api.RpcClientFactory;
import org.apache.flume.event.EventBuilder;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyApp {

    public static void main(String[] args) {
        MyRpcClientFacade client = new MyRpcClientFacade();
        // Initialize client with the remote Flume agent's host and port
        MyRpcClientFacade.init(client, "192.168.1.7", 50000);

        // Send 10 events to the remote Flume agent. That agent should be
        // configured to listen with an AvroSource.


        for (int i = 0; i < 10; i++) {
            String sampleData = "Hello Flume!";
            Map<String, String> headers = new HashMap<>();
            System.out.println("i:" + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sampleData = sampleData + i;
            headers.put(sampleData,sampleData);
            client.sendDataToFlume(sampleData,headers);
        }

        client.cleanUp();
    }
}

class MyRpcClientFacade {
    private RpcClient client;
    private String hostname;
    private int port;

    public static void init(MyRpcClientFacade myRpcClientFacade, String hostname, int port) {
        // Setup the RPC connection
        myRpcClientFacade.hostname = hostname;
        myRpcClientFacade.port = port;
        myRpcClientFacade.client = RpcClientFactory.getDefaultInstance(hostname, port);
        // Use the following method to create a thrift client (instead of the above line):
        // this.client = RpcClientFactory.getThriftInstance(hostname, port);

        // Setup properties for the load balancing
        Properties props = new Properties();
        props.put("client.type", "default_loadbalance");

        // List of hosts (space-separated list of user-chosen host aliases)
        props.put("hosts", "h1 h2 h3");

        // host/port pair for each host alias
        String host1 = "127.0.0.1:50000";
        String host2 = "127.0.0.1:50001";
        String host3 = "127.0.0.1:50002";
        props.put("hosts.h1", host1);
        props.put("hosts.h2", host2);
        props.put("hosts.h3", host3);

        props.put("host-selector", "random"); // For random host selection
        // props.put("host-selector", "round_robin"); // For round-robin host
        //                                            // selection
        props.put("backoff", "true"); // Disabled by default.

        props.put("maxBackoff", "10000"); // Defaults 0, which effectively
        // becomes 30000 ms

        // Create the client with load balancing properties
//        client = RpcClientFactory.getInstance(props);
    }

    public void sendDataToFlume(String data,Map<String, String> map) {
        // Create a Flume Event object that encapsulates the sample data
        Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"),map);

        // Send the event
        try {
            client.append(event);
        } catch (EventDeliveryException e) {
            // clean up and recreate the client
            client.close();
            client = null;
            client = RpcClientFactory.getDefaultInstance(hostname, port);
            // Use the following method to create a thrift client (instead of the above line):
            // this.client = RpcClientFactory.getThriftInstance(hostname, port);
        }
    }

    public void cleanUp() {
        // Close the RPC connection
        client.close();
    }
}
