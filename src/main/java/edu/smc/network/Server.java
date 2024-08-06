package edu.smc.network;

import edu.smc.data.Administrator;
import edu.smc.data.Database;
import edu.smc.data.Student;

import java.io.*;
import java.net.*;

/**
 * A server that handles various tasks such as login, add, remove and list.
 * Each task is processed by a respective function.
 */
public class Server {
    private static final String CMD_LOGIN = "login";
    private static final String CMD_ADD = "add";
    private static final String CMD_REMOVE = "remove";
    private static final String CMD_LIST = "list";
    private static final String SUCCESS = "true";
    private static final String FAIL = "false";
    private static final String ADMIN = "admin";
    private static final String STUDENT = "student";
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Database data = new Database();
    private Administrator admin = new Administrator("admin", "admin");


    /**
     * Starts the server and processes client requests.
     * @param port The port number.
     */
    public void start(int port) {
        try {
            prepareServer(port);
            processClientRequests();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
    /**
     * Prepares the server by loading data and creating a server socket.
     * @param port The port number.
     * @throws IOException In case of any IO errors.
     */
    private void prepareServer(int port) throws IOException {
        data.loadData();
        serverSocket = new ServerSocket(port);
    }
    /**
     * Waits for client connections and processes their messages.
     * @throws IOException In case of any IO errors.
     */
    private void processClientRequests() throws IOException {
        while (true) {
            establishClientConnection();
            processClientMessages();
        }
    }

    /**
     * Establishes a connection with the client.
     * @throws IOException if an I/O error occurs when waiting for a connection.
     */
    private void establishClientConnection() throws IOException {
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Reads and processes messages from the client.
     * @throws IOException if an I/O error occurs.
     */
    private void processClientMessages() throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            processClientMessage(inputLine);
        }
    }

    /**
     * Processes a client message based on its command.
     * @param inputLine the message from the client
     */
    private void processClientMessage(String inputLine){
        String[] parse = inputLine.split("#");
        if (parse[0].equals(CMD_LOGIN)) {
            processLoginCommand(parse);
        } else if (parse[0].equals(CMD_ADD)) {
            processAddCommand(parse);
        } else if (parse[0].equals(CMD_REMOVE)) {
            processRemoveCommand(parse);
        } else if (parse[0].equals(CMD_LIST)) {
            out.println(data.listStudents());
        } else if (parse[0].equals("info")) {
            processInfoCommand(parse);
        }
    }

    /**
     * Processes the login command from the client.
     * @param parse the parsed command from the client
     */
    private void processLoginCommand(String[] parse){
        if(parse[3].equals(ADMIN)) {
            if(admin.verify(parse[1], parse[2])){
                out.println(SUCCESS);
            } else{
                out.println(FAIL);
            }
        } else {
            if (data.verify(parse[1], parse[2])) {
                out.println(SUCCESS);
            } else{
                out.println(FAIL);
            }
        }
    }

    /**
     * Processes the add command from the client.
     * @param parse the parsed command from the client
     */
    private void processAddCommand(String[] parse){
        boolean studentExist = data.addStudent(parse[1], parse[2], parse[3],parse[4], parse[5]);
        if(studentExist){
            out.println(SUCCESS);
            data.saveData();
        } else{
            out.println(FAIL);
        }
    }

    /**
     * Processes the remove command from the client.
     * @param parse the parsed command from the client
     */
    private void processRemoveCommand(String[] parse){
        boolean studentRemoved = data.removeStudent(Integer.valueOf(parse[1]));
        if(studentRemoved){
            out.println(SUCCESS);
            data.saveData();
        } else{
            out.println(FAIL);
        }
    }

    /**
     * Processes the info command from the client.
     * @param parse the parsed command from the client
     */
    private void processInfoCommand(String[] parse){
        int studentID = Integer.valueOf(parse[1]);
        Student student = data.getStudent(studentID);
        if (student != null) {
            out.println(buildStudentInfo(student));
        } else {
            out.println(FAIL);
        }
    }

    /**
     * Constructs a student info string.
     * @param student the student whose info is to be built
     * @return the constructed info string
     */
    private String buildStudentInfo(Student student){
        StringBuilder info = new StringBuilder();
        info.append(student.getFirstName()+ "#");
        info.append(student.getLastName()+ "#");
        info.append(student.getStudentID()+ "#");
        info.append(student.getPhoneNumber()+ "#");
        info.append(student.getAddress()+ "#");
        info.append(student.getMajor());
        return info.toString();
    }
    /**
     * Stops the server and closes all resources.
     */
    public void stop() {

        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Entry point of the server application. Starts the server at port 5000.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        // Create a new Server instance
        Server server = new Server();
        // Start the server at port 5000
        server.start(5000);

    }
}

