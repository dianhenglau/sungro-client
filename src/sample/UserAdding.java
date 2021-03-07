package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sungro.api.ParamForAddUser;
import sungro.api.ResultForAddUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class UserAdding {
    private final Router router;

    @FXML
    private ImageView profilePicView;
    @FXML
    private TextField profilePicInput;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField idNumberInput;
    @FXML
    private ChoiceBox<String> idTypeInput;
    @FXML
    private ChoiceBox<String> roleInput;
    @FXML
    private ChoiceBox<String> statusInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField confirmPasswordInput;

    public UserAdding(Router router) {
        this.router = router;
    }

    public boolean render() {
        profilePicView.setImage(new Image("profile.png"));
        profilePicInput.setText("");
        firstNameInput.setText("");
        lastNameInput.setText("");
        emailInput.setText("");
        idNumberInput.setText("");
        idTypeInput.setValue(idTypeInput.getItems().get(0));
        roleInput.setValue(roleInput.getItems().get(0));
        statusInput.setValue(statusInput.getItems().get(0));
        passwordInput.setText("");
        confirmPasswordInput.setText("");

        return true;
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getUserAddingRoot().setVisible(false);
        router.getUserListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {
        if (!passwordInput.getText().equals(confirmPasswordInput.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password and Confirm Password does not match").showAndWait();
            return;
        }

        ParamForAddUser param = new ParamForAddUser();
        param.setSessionId("0123456789abcdef");
        param.setFirstName(firstNameInput.getText());
        param.setLastName(lastNameInput.getText());
        param.setEmail(emailInput.getText());
        param.setIdNumber(idNumberInput.getText());
        param.setIdType(idTypeInput.getValue());
        param.setRole(roleInput.getValue());
        param.setStatus(statusInput.getValue());
        param.setPassword(passwordInput.getText());

        if (!profilePicInput.getText().isEmpty()) {
            try {
                param.setProfilePic(Files.readAllBytes(Paths.get(profilePicInput.getText())));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            ResultForAddUser result = router.getRepo().addUser(param);

            switch (result.getStatus()) {
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return;
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
                case MISSING_FIRST_NAME:
                    new Alert(Alert.AlertType.ERROR, "First name is required.").showAndWait();
                    return;
                case MISSING_LAST_NAME:
                    new Alert(Alert.AlertType.ERROR, "Last name is required.").showAndWait();
                    return;
                case MISSING_EMAIL:
                    new Alert(Alert.AlertType.ERROR, "Email is required.").showAndWait();
                    return;
                case INVALID_EMAIL:
                    new Alert(Alert.AlertType.ERROR, "Invalid email format.").showAndWait();
                    return;
                case REPEATED_EMAIL:
                    new Alert(Alert.AlertType.ERROR, "This email has been used.").showAndWait();
                    return;
                case MISSING_ID_NUMBER:
                    new Alert(Alert.AlertType.ERROR, "ID number is required.").showAndWait();
                    return;
                case REPEATED_ID_NUMBER:
                    new Alert(Alert.AlertType.ERROR, "This ID number has been used.").showAndWait();
                    return;
                case MISSING_ID_TYPE:
                    new Alert(Alert.AlertType.ERROR, "ID type is required.").showAndWait();
                    return;
                case INVALID_ID_TYPE:
                    new Alert(Alert.AlertType.ERROR, "Invalid ID type.").showAndWait();
                    return;
                case MISSING_ROLE:
                    new Alert(Alert.AlertType.ERROR, "Role is required.").showAndWait();
                    return;
                case INVALID_ROLE:
                    new Alert(Alert.AlertType.ERROR, "Invalid role.").showAndWait();
                    return;
                case MISSING_PASSWORD:
                    new Alert(Alert.AlertType.ERROR, "Password is required.").showAndWait();
                    return;
                case MISSING_STATUS:
                    new Alert(Alert.AlertType.ERROR, "Status is required.").showAndWait();
                    return;
                case INVALID_STATUS:
                    new Alert(Alert.AlertType.ERROR, "Invalid status.").showAndWait();
                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "New user added.").showAndWait();
                    break;
            }

            router.getUserListing().render(router.getUserListing().generateParam());
            router.getUserAddingRoot().setVisible(false);
            router.getUserListingRoot().setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleChooseBtnAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose profile picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(router.getScene().getWindow());
        if (selectedFile != null) {
            profilePicView.setImage(new Image(selectedFile.toURI().normalize().toString()));
            profilePicInput.setText(selectedFile.getPath());
        }
    }
}
