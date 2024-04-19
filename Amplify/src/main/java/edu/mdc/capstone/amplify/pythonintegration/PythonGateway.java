package edu.mdc.capstone.amplify.pythonintegration;

import py4j.GatewayServer;

public class PythonGateway {
    private GatewayServer gatewayServer;

    public PythonGateway() {
        // Start the Py4J GatewayServer to let Python access Java objects.
        // This will make Java objects and methods available to Python.
        gatewayServer = new GatewayServer(this);
    }

    public void start() {
        try {
            gatewayServer.start();
            System.out.println("Gateway Server Started");
        } catch (Exception e) {
            System.err.println("Error starting Gateway Server: " + e.getMessage());
        }
    }

    public void stop() {
        if (gatewayServer != null) {
            gatewayServer.shutdown();
            System.out.println("Gateway Server Stopped");
        }
    }

    // You can define methods here that you want to call from Python.
    // ...

    public static void main(String[] args) {
        PythonGateway gateway = new PythonGateway();
        gateway.start();
    }
}
