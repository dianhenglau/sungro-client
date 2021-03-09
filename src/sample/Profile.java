package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sungro.api.ParamForGetCurrentUser;
import sungro.api.ParamForGetOneUser;
import sungro.api.ResultForGetCurrentUser;
import sungro.api.ResultForGetOneUser;

import java.io.ByteArrayInputStream;
import java.rmi.RemoteException;

public class Profile {
    private final Router router;

    @FXML
    private ImageView profilePicView;
    @FXML
    private Text userIdTxt;
    @FXML
    private Text firstNameTxt;
    @FXML
    private Text lastNameTxt;
    @FXML
    private Text emailTxt;
    @FXML
    private Text idNumberTxt;
    @FXML
    private Text idTypeTxt;
    @FXML
    private Text roleTxt;
    @FXML
    private Text statusTxt;
    @FXML
    private Text createdByUserNameTxt;
    @FXML
    private Text createdOnTxt;

    public Profile(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetCurrentUser param) {
        try {
            ResultForGetCurrentUser result = router.getRepo().getCurrentUser(param);

            switch (result.getStatus()) {
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return false;
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
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
            firstNameTxt.setText(user.getFirstName());
            lastNameTxt.setText(user.getLastName());
            emailTxt.setText(user.getEmail());
            idNumberTxt.setText(user.getIdNumber());
            idTypeTxt.setText(user.getIdType());
            roleTxt.setText(user.getRole());
            statusTxt.setText(user.getStatus());
            createdByUserNameTxt.setText(user.getCreatedByUserName());
            createdOnTxt.setText(user.getCreatedOn().toString());

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getProfileRoot().setVisible(false);
        router.getDashboardRoot().setVisible(true);
    }


}
