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

/**
 * This is a JavaFX controller class that manages the interactions between the user interface and the backend.
 */
public class JavaFXController {
    /**
     * Constants used in the different methods of this class.
     */
    private static final String CMD_LOGIN = "login#";
    private static final String CMD_ADD = "add#";
    private static final String CMD_REMOVE = "remove#";
    private static final String CMD_LIST = "list";
    private static final String SUCCESS = "true";
    private static final String FAIL = "false";
    private static final String ADMIN = "admin";
    private static final String STUDENT = "student";
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 5000;

    /**
     * JavaFX fields that are set in the FXML file and used in methods to get and set values.
     */
    @FXML
    private ComboBox<String> dropDown;
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
    private CheckBox isAdmin;
    @FXML
    private Label message;

    /**
     * Key used in request to identify user.
     */
    private String userKey;
    private Client client;

    /**
     * Method that runs when the Login button is clicked.
     * @param event the action event
     */
    @FXML
    protected void onLoginAction(ActionEvent event) {
        try {
            this.client = new Client(SERVER_IP, SERVER_PORT);
            if (username.getText().trim().isEmpty() || password.getText().trim().isEmpty()) {
                message.setText("Please fill out all fields");
                client.close();
            } else {
                StringBuilder request = new StringBuilder(CMD_LOGIN);
                userKey = username.getText() + "#";
                request.append(userKey).append(password.getText()).append("#");
                if (isAdmin.isSelected()) {
                    request.append(ADMIN);
                    client.sendToServer(request.toString());
                    if (client.readFromServer().equals(SUCCESS)) {
                        client.close();
                        loadAdminView(((Node) event.getSource()).getScene().getWindow());
                    } else {
                        message.setText("Login failed");
                        client.close();
                    }
                } else {
                    request.append(STUDENT);
                    client.sendToServer(request.toString());
                    if (client.readFromServer().equals(SUCCESS)) {
                        client.close();
                        loadStudentView(((Node) event.getSource()).getScene().getWindow());
                    } else {
                        message.setText("Login failed");
                        client.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the Admin view.
     * @param window the current window
     */
    private void loadAdminView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("admin-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Modify button action.
     * @param event the action event
     */
    @FXML
    public void onModifyAction(ActionEvent event) {
        loadModifyView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Loads the Modify view.
     * @param window the current window
     */
    private void loadModifyView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("modify-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Remove button action.
     * @param event the action event
     */
    @FXML
    public void onRemoveAction(ActionEvent event) {
        loadRemoveView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Loads the Remove view.
     * @param window the current window
     */
    public void loadRemoveView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("remove-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Add button action in the modify view.
     * @param event the action event
     */
    @FXML
    public void onAddAction(ActionEvent event) {
        loadAddView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Loads the Add view.
     * @param window the current window
     */
    private void loadAddView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("add-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the List button action.
     * @param event the action event
     */
    @FXML
    public void onListAction(ActionEvent event) {
        loadListView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Loads the List view.
     * @param window the current window
     */
    private void loadListView(Window window) {
        try {
            this.client = new Client(SERVER_IP, SERVER_PORT);
            client.sendToServer(CMD_LIST);
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(3));
            String[] students = client.readFromServer().split("#");
            String topLine = "[First Name, Last Name, Student ID, Phone Number, Address, Major]";
            String line = "------------------------------------------------------------------------";
            vbox.getChildren().add(new Label(topLine));
            vbox.getChildren().add(new Label(line));
            for (String student : students) {
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
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Add Student button action.
     * @param event the action event
     */
    @FXML
    public void onAddStudentAction(ActionEvent event) {
        try {
            this.client = new Client(SERVER_IP, SERVER_PORT);
            if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty() ||
                    phoneNumber.getText().trim().isEmpty() || address.getText().trim().isEmpty() ||
                    dropDown.getValue() == null) {
                message.setText("Please fill out all fields");
                client.close();
            } else {
                StringBuilder request = new StringBuilder();
                request.append(CMD_ADD);
                request.append(firstName.getText().trim()).append("#");
                request.append(lastName.getText().trim()).append("#");
                request.append(phoneNumber.getText().trim()).append("#");
                request.append(address.getText().trim()).append("#");
                request.append(dropDown.getValue());
                client.sendToServer(request.toString());
                if (client.readFromServer().equals(SUCCESS)) {
                    message.setText("Student added");
                } else {
                    message.setText("Student already exists");
                }
                client.close();
                PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Delay for 2 seconds
                pause.setOnFinished(e -> loadAdminView(((Node) event.getSource()).getScene().getWindow()));
                pause.play();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Remove Student button action.
     * @param event the action event
     */
    @FXML
    public void onRemoveStudentAction(ActionEvent event) {
        try {
            this.client = new Client(SERVER_IP, SERVER_PORT);
            if (studentID.getText().trim().isEmpty()) {
                message.setText("Please fill out all fields");
                client.close();
            } else {
                int isIntegerValue = Integer.parseInt(studentID.getText().trim());
                StringBuilder request = new StringBuilder();
                request.append(CMD_REMOVE);
                request.append(studentID.getText());
                client.sendToServer(request.toString());
                if (client.readFromServer().equals(SUCCESS)) {
                    message.setText("Student removed");
                    client.close();
                    PauseTransition pause = new PauseTransition(Duration.seconds(2)); // Delay for 2 seconds
                    pause.setOnFinished(e -> loadAdminView(((Node) event.getSource()).getScene().getWindow()));
                    pause.play();
                } else {
                    message.setText("Student does not exist");
                    client.close();
                }
            }
        } catch (IOException ignored) {
            // Handle ignored exception
        } catch (NumberFormatException e) {
            message.setText("Input integer value");
        }
        try {
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads the Student view.
     * @param window the current window
     */
    public void loadStudentView(Window window) {
        try {
            this.client = new Client(SERVER_IP, SERVER_PORT);
            String studentID = userKey.substring(0, 7);
            StringBuilder request = new StringBuilder("info#");
            request.append(studentID);
            client.sendToServer(request.toString());
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(3));
            String[] students = client.readFromServer().split("#");
            for (String student : students) {
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
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Logout button action.
     * @param event the action event
     */
    @FXML
    public void onLogoutAction(ActionEvent event) {
        loadInitialView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Loads the Initial view.
     * @param window the current window
     */
    public void loadInitialView(Window window) {
        FXMLLoader loader = new FXMLLoader(JavaFXController.class.getResource("initial-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 320, 240);
            ((Stage) window).setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Event handler for the Back button action in various views.
     * @param event the action event
     */
    @FXML
    public void onBackAction(ActionEvent event) {
        loadAdminView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Event handler for the Back button action in the Remove view.
     * @param event the action event
     */
    @FXML
    public void onRemoveBackAction(ActionEvent event) {
        loadModifyView(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * Event handler for the Back button action in the Add view.
     * @param event the action event
     */
    @FXML
    public void onAddBackAction(ActionEvent event) {
        loadAdminView(((Node) event.getSource()).getScene().getWindow());
    }
}
