package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sungro.api.ParamForLogin;
import sungro.api.ResultForLogin;

import java.rmi.RemoteException;

public class Login {
    private final Router router;

    @FXML
    private TextField emailTxt;
    @FXML
    private TextField passwordTxt;


    public Login(Router router) {
        this.router = router;
    }

    public boolean render() {
        return true;
    }

    @FXML
    protected void handleLoginBtnAction() {
        if (passwordTxt.getText().isEmpty() || emailTxt.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Email address or Password are required!").showAndWait();
            return;
        }

        ParamForLogin param = new ParamForLogin();
        param.setEmail(emailTxt.getText());
        param.setPassword(passwordTxt.getText());


        try {
            ResultForLogin result = router.getRepo().login(param);

            switch (result.getStatus()) {
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
                case MISSING_EMAIL:
                    new Alert(Alert.AlertType.ERROR, "Email is required.").showAndWait();
                    return;
                case MISSING_PASSWORD:
                    new Alert(Alert.AlertType.ERROR, "Password is required.").showAndWait();
                    return;
                case INVALID_CREDENTIAL:
                    new Alert(Alert.AlertType.ERROR, "Credential is invalid.").showAndWait();
                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "Welcome.").showAndWait();
                    break;
            }
            router.setSessionId(result.getSessionId());

            router.getLayout().render(result.getUser());
            router.getDashboard().render();
            router.getLoginRoot().setVisible(false);
            router.getLayoutRoot().setVisible(true);
            router.getDashboardRoot().setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        passwordTxt.setText("");
        emailTxt.setText("");
    }
}
