package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sungro.api.*;

import java.rmi.RemoteException;

public class Dashboard {
    private final Router router;

    @FXML
    private TableView salesTbl;

    @FXML
    private TableView userTbl;

    public Dashboard(Router router) {
        this.router = router;
    }

    public boolean render() {
        try {
            ParamForGetManyUsers param = new ParamForGetManyUsers();
            param.setSessionId(router.getSessionId());
            ResultForGetManyUsers result = router.getRepo().getManyUsers(param); //collect user information from server

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

            userTbl.getItems().clear();
            for (sungro.api.User user : result.getUsers()) {
                userTbl.getItems().add(new User(user)); // put the information via (for loop)
            }

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

        try {
            ParamForGetManySales param = new ParamForGetManySales();
            param.setSessionId(router.getSessionId());
            ResultForGetManySales result = router.getRepo().getManySales(param);

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

            salesTbl.getItems().clear();
            for (sungro.api.Sale sales : result.getSales()) {
                salesTbl.getItems().add(new Sales(sales) );
            }

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

}
