package edu.smc.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The Client class represents a client that can communicate with a server using sockets.
 * It uses a Socket to connect to the server and communicates with the server using
 * BufferedReader and PrintWriter.
 */
public class Client implements AutoCloseable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Constructs a new Client.
     *
     * @param serverAddress the server's IP address or hostname
     * @param port the port number
     * @throws IOException if an I/O error occurs when creating the socket, or
     *                     if the socket could not be opened, or the socket could not
     *                     connect to the server and port specified
     */
    public Client(String serverAddress, int port) throws IOException {
        this.socket = new Socket(serverAddress, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send to the server
     */
    public void sendToServer(String message) {
        out.println(message);
    }

    /**
     * Reads a line of text from the server.
     *
     * @return a String containing the line read from the server
     * @throws IOException if an I/O error occurs when reading from the server
     */
    public String readFromServer() throws IOException {
        return in.readLine();
    }

    /**
     * Closes this client, releasing all resources associated with it.
     * If the client is already closed, calling this method has no effect.
     *
     * @throws IOException if an I/O error occurs when closing the client
     */
    @Override
    public void close() throws IOException {
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        if (socket != null) {
            socket.close();
        }
    }
}

