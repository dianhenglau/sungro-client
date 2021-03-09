package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sungro.api.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class UserEditing {
    private final Router router;

    @FXML
    private ImageView profilePicView;
    @FXML
    private TextField profilePicInput;
    @FXML
    private Text userIdTxt;
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

    public UserEditing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetOneUser param) {
        try {
            ResultForGetOneUser result = router.getRepo().getOneUser(param);

            switch (result.getStatus()) {
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return false;
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return false;
                case NOT_FOUND:
                    new Alert(Alert.AlertType.ERROR, "User not found").showAndWait();
                    return false;
                case SUCCESS:
                    break;
            }

            sungro.api.User user = result.getUser();

            if (user.getProfilePic().length == 0) {
                profilePicView.setImage(new Image("profile.png"));
            } else {
                profilePicView.setImage(new Image(new ByteArrayInputStream(user.getProfilePic())));
            }

            userIdTxt.setText(String.valueOf(user.getUserId()));
            firstNameInput.setText(user.getFirstName());
            lastNameInput.setText(user.getLastName());
            emailInput.setText(user.getEmail());
            idNumberInput.setText(user.getIdNumber());
            idTypeInput.setValue(user.getIdType());
            roleInput.setValue(user.getRole());
            statusInput.setValue(user.getStatus());

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getUserEditingRoot().setVisible(false);
        router.getUserListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {
        if (!passwordInput.getText().equals(confirmPasswordInput.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password and Confirm Password does not match").showAndWait();
            return;
        }

        ParamForSetUser param = new ParamForSetUser();
        param.setSessionId(router.getSessionId());
        param.setUserId(Integer.parseInt(userIdTxt.getText()));
        param.setFirstName(firstNameInput.getText());
        param.setLastName(lastNameInput.getText());
        param.setEmail(emailInput.getText());
        param.setIdNumber(idNumberInput.getText());
        param.setIdType(idTypeInput.getValue());
        param.setRole(roleInput.getValue());
        param.setStatus(statusInput.getValue());

        if (!passwordInput.getText().isEmpty()) {
            param.setPassword(passwordInput.getText());
        }

        if (!profilePicInput.getText().isEmpty()) {
            try {
                param.setProfilePic(Files.readAllBytes(Paths.get(profilePicInput.getText())));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            ResultForSetUser result = router.getRepo().setUser(param);

            switch (result.getStatus()) {
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return;
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
                case NOT_FOUND:
                    new Alert(Alert.AlertType.ERROR, "User not found.").showAndWait();
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
                case MISSING_STATUS:
                    new Alert(Alert.AlertType.ERROR, "Status is required.").showAndWait();
                    return;
                case INVALID_STATUS:
                    new Alert(Alert.AlertType.ERROR, "Invalid status.").showAndWait();
                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "User updated.").showAndWait();
                    break;
            }

            router.getUserListing().render(router.getUserListing().generateParam());
            router.getUserEditingRoot().setVisible(false);
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
