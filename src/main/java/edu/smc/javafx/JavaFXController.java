package edu.smc.javafx;

import edu.smc.network.Client;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;

public class JavaFXController {
    private static final String CMD_LOGIN = "login#";
    private static final String CMD_ADD = "add#";
    private static final String CMD_REMOVE = "remove#";
    private static final String CMD_LIST = "list";
    private static final String SUCCESS = "true";
    private static final String Fail = "false";
    private static final String ADMIN = "admin";
    private static final String STUDENT = "student";

    @FXML
    private ComboBox dropDown;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;
    @FXML
    private TextField studentID;

    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField major;


    @FXML
    private CheckBox isAdmin;

    @FXML
    private Label message;

    private String userKey;
    private Client client;

    //initial view controller
    @FXML
    protected void onLoginAction(ActionEvent event) {
        try {
            this.client = new Client("localhost", 5000);
            if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
                message.setText("Please fill out all fields");
                client.close();
            }  else {
                StringBuilder request = new StringBuilder(CMD_LOGIN);
                request.append(userKey = username.getText() + "#");
                request.append(password.getText()+ "#");
                if(isAdmin.isSelected()){
                    request.append(ADMIN);
                    client.sendToServer(request.toString());
                    if (client.readFromServer().equals("true")) {
                        client.close();
                        loadAdminView(((Node) event.getSource()).getScene().getWindow());
                    } else {
                        message.setText("login failed");
                        client.close();
                    }
                }else{
                    request.append(STUDENT);
                    client.sendToServer(request.toString());
                    if(client.readFromServer().equals("true")){
                        client.close();
                        loadStudentView(((Node)event.getSource()).getScene().getWindow());
                    } else {
                        message.setText("login failed");
                        client.close();
                    }
                }

            }

        } catch (IOException e) {

        }
    }

    private void loadAdminView(Window window) {

        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("admin-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //admin view controller
    @FXML
    public void onModifyAction(ActionEvent event) {
        loadModifyView(((Node) event.getSource()).getScene().getWindow());
    }

    private void loadModifyView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("modify-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onRemoveAction(ActionEvent event){
        loadRemoveView(((Node)event.getSource()).getScene().getWindow());
    }

    public void loadRemoveView(Window window){
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("remove-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(),320, 240);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //modify view controller
    public void onAddAction(ActionEvent event) {

        loadAddView(((Node) event.getSource()).getScene().getWindow());
    }

    private void loadAddView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("add-view.fxml"));

        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onListAction(ActionEvent event) {
        loadListView(((Node) event.getSource()).getScene().getWindow());
    }

    private void loadListView(Window window) {
        try {
            this.client = new Client("localhost", 5000);
            client.sendToServer(CMD_LIST);
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(3));
            String[] students = client.readFromServer().split("#");
            String topLine = "[first name, last name, student ID, phone number, address, major]";
            String line = "------------------------------------------------------------------------";
            vbox.getChildren().add(new Label(topLine));
            vbox.getChildren().add(new Label(line));
            for(String student: students) {
                Label label = new Label(student);
                vbox.getChildren().add(label);
            }
            client.close();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vbox);
            VBox mainLayout = new VBox(scrollPane);
            Button button = new Button("Back");
            button.setOnAction(e -> loadAdminView(window));
            mainLayout.getChildren().add(button);
            Scene scene = new Scene(mainLayout, 640, 480);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //add student view controller
    public void onAddStudentAction(ActionEvent event) {
        try {
            this.client = new Client("localhost", 5000);
            if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty() ||
                    phoneNumber.getText().trim().isEmpty() || address.getText().trim().isEmpty() ||
                    dropDown.getValue() == null) {
                message.setText("Please fill out all fields");
                client.close();
            } else {
                StringBuilder request = new StringBuilder();
                request.append(CMD_ADD);
                request.append(firstName.getText().trim() + "#");
                request.append(lastName.getText().trim() + "#");
                request.append(phoneNumber.getText().trim() + "#");
                request.append(address.getText().trim() + "#");
                request.append(dropDown.getValue());
                client.sendToServer(request.toString());
                if (client.readFromServer().equals(SUCCESS)) {
                    message.setText("Student added");
                } else {
                    message.setText("Student already exist");
                }
                client.close();
                PauseTransition pause = new PauseTransition(Duration.seconds(2)); //Delay for 2 seconds
                pause.setOnFinished(e -> loadAdminView(((Node) event.getSource()).getScene().getWindow()));
                pause.play();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onRemoveStudentAction(ActionEvent event) {
        try{
            this.client = new Client("localhost", 5000);

            if(studentID.getText().trim().isEmpty()){
                message.setText("Please fill out all fields");
                client.close();
            }else{
                int isIntegerValue = Integer.valueOf(studentID.getText().trim());
                StringBuilder request = new StringBuilder();
                request.append(CMD_REMOVE);
                request.append(studentID.getText());
                client.sendToServer(request.toString());
                if(client.readFromServer().equals(SUCCESS)){
                    message.setText("Student removed");
                    client.close();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2)); //Delay for 2 seconds
                    pause.setOnFinished(e -> loadAdminView(((Node) event.getSource()).getScene().getWindow()));
                    pause.play();
                }else{
                    message.setText("Student does not exist");
                    client.close();
                }
            }
        }catch(IOException ignored){

        }catch (NumberFormatException e){
            message.setText("Input integer value");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadStudentView(Window window){
        try {
            this.client = new Client("localhost", 5000);
            String studentID = userKey.substring(0,7);
            StringBuilder request = new StringBuilder("info#");
            request.append(studentID);
            client.sendToServer(request.toString());
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(3));
            String[] students = client.readFromServer().split("#");
            for(String student: students) {
                Label label = new Label(student);
                vbox.getChildren().add(label);
            }
            client.close();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vbox);
            VBox mainLayout = new VBox(scrollPane);
            Button button = new Button("Logout");
            button.setOnAction(e -> loadInitialView(window));
            mainLayout.getChildren().add(button);
            Scene scene = new Scene(mainLayout, 320, 240);
            ((Stage)window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onLogoutAction(ActionEvent event) {
        loadInitialView(((Node) event.getSource()).getScene().getWindow());

    }
    public void loadInitialView(Window window){
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("initial-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(),320,240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onBackAction(ActionEvent event){
        loadAdminView((((Node) event.getSource()).getScene().getWindow()));

    }
    @FXML
    public void onRemoveBackAction(ActionEvent event) {
        loadModifyView((((Node) event.getSource()).getScene().getWindow()));

    }
    @FXML
    public void onAddBackAction(ActionEvent event) {
        loadAdminView(((Node) event.getSource()).getScene().getWindow());

    }


}