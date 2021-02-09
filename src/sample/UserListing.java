package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sungro.api.ParamForGetManyUsers;
import sungro.api.ParamForGetOneUser;
import sungro.api.ResultForGetManyUsers;

import java.rmi.RemoteException;

public class UserListing {
    private final Router router;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField idNumberInput;
    @FXML
    private ChoiceBox<String> roleInput;
    @FXML
    private TableView<User> tableView;
    @FXML
    private Text currentPageTxt;
    @FXML
    private Text maxPageTxt;
    @FXML
    private TextField pageInput;

    public UserListing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetManyUsers param) {
        try {
            ResultForGetManyUsers result = router.getRepo().getManyUsers(param);

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

            tableView.getItems().clear();
            for (sungro.api.User user : result.getUsers()) {
                tableView.getItems().add(new User(user));
            }

            currentPageTxt.setText(String.valueOf(result.getCurrentPage()));
            maxPageTxt.setText(String.valueOf(result.getMaxPage()));

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ParamForGetManyUsers generateParam() {
        ParamForGetManyUsers param = new ParamForGetManyUsers();

        param.setSessionId("0123456789abcdef");
        param.setName(nameInput.getText());
        param.setEmail(emailInput.getText());
        param.setIdNumber(idNumberInput.getText());
        param.setRole(roleInput.getValue());
        param.setPage(Integer.parseInt(currentPageTxt.getText().trim()));

        return param;
    }

    @FXML
    protected void handleAddUserBtnAction() {
        router.getUserAdding().render();
        router.getUserListingRoot().setVisible(false);
        router.getUserAddingRoot().setVisible(true);
    }

    @FXML
    protected void handleGoBtnAction() {
        render(generateParam());
    }

    @FXML
    protected void handleViewBtnAction() {
        User user = tableView.getSelectionModel().getSelectedItem();

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetOneUser param = new ParamForGetOneUser();
        param.setSessionId("0123456789abcdef");
        param.setUserId(user.getUserId());

        if (router.getUserInfo().render(param)) {
            router.getUserListingRoot().setVisible(false);
            router.getUserInfoRoot().setVisible(true);
        }
    }

    @FXML
    protected void handleEditBtnAction() {
        User user = tableView.getSelectionModel().getSelectedItem();

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetOneUser param = new ParamForGetOneUser();
        param.setSessionId("0123456789abcdef");
        param.setUserId(user.getUserId());

        if (router.getUserEditing().render(param)) {
            router.getUserListingRoot().setVisible(false);
            router.getUserEditingRoot().setVisible(true);
        }
    }

    @FXML
    protected void handleToggleStatusBtnAction() {

    }

    @FXML
    protected void handlePrevBtnAction() {
        ParamForGetManyUsers param = generateParam();

        param.setPage(param.getPage() - 1);
        if (param.getPage() < 1) {
            param.setPage(1);
        }

        render(param);
    }

    @FXML
    protected void handleNextBtnAction() {
        ParamForGetManyUsers param = generateParam();

        param.setPage(param.getPage() + 1);

        render(param);
    }

    @FXML
    protected void handlePageInputAction() {
        ParamForGetManyUsers param = generateParam();

        try {
            param.setPage(Integer.parseInt(pageInput.getText().trim()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid page number").showAndWait();
        }

        render(param);
    }
}
