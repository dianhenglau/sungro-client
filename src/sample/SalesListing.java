package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sungro.api.*;

import java.rmi.RemoteException;

public class SalesListing {
    private final Router router;

    @FXML
    private TextField categoryInput;

    //    private TextField idNumberInput;
    @FXML
    private DatePicker expiryDateFromInput;
    @FXML
    private DatePicker expiryDateToInput;
    @FXML
    private TextField SKUInput;
    @FXML
    private TableView<Sales> tableView;
    @FXML
    private Text currentPageTxt;
    @FXML
    private Text maxPageTxt;
    @FXML
    private TextField pageInput;

    public SalesListing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetManySales param) {
        try {
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

            tableView.getItems().clear();
            for (sungro.api.Sale sales : result.getSales()) {
        //        tableView.getItems().add(new Sale(sales));
            }

            currentPageTxt.setText(String.valueOf(result.getCurrentPage()));
            maxPageTxt.setText(String.valueOf(result.getMaxPage()));

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ParamForGetManySales generateParam() {
        ParamForGetManySales param = new ParamForGetManySales();

        param.setSessionId("0123456789abcdef");
        //param.setName(nameInput.getText());
        param.setPage(Integer.parseInt(currentPageTxt.getText().trim()));
//        param.setSku(SKUInput.getText());

        return param;
    }

    @FXML
    protected void handleAddSalesBtnAction() {
        router.getSalesAdding().render();
        router.getSalesListingRoot().setVisible(false);
        router.getSalesAddingRoot().setVisible(true);
    }

    @FXML
    protected void handleGoBtnAction() {
        render(generateParam());
    }

//    @FXML
//    protected void handleViewBtnAction() {
//        User user = tableView.getSelectionModel().getSelectedItem();
//
//        if (user == null) {
//            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
//            return;
//        }
//
//        ParamForGetOneSales param = new ParamForGetOneSales();
//        param.setSessionId("0123456789abcdef");
//        param.setUserId(user.getUserId());
//
//        if (router.getUserInfo().render(param)) {
//            router.getUserListingRoot().setVisible(false);
//            router.getUserInfoRoot().setVisible(true);
//        }
//    }

//    @FXML
//    protected void handleEditBtnAction() {
//        User user = tableView.getSelectionModel().getSelectedItem();
//
//        if (user == null) {
//            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
//            return;
//        }
//
//        ParamForGetOneUser param = new ParamForGetOneUser();
//        param.setSessionId("0123456789abcdef");
//        param.setUserId(user.getUserId());
//
//        if (router.getUserEditing().render(param)) {
//            router.getUserListingRoot().setVisible(false);
//            router.getUserEditingRoot().setVisible(true);
//        }
//    }

    @FXML
    protected void handleDeleteBtnAction() {
        Sales sales = tableView.getSelectionModel().getSelectedItem();

        if (sales == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForDeleteSale param = new ParamForDeleteSale();
        param.setSessionId("0123456789abcdef");
        param.setSaleId(sales.getSaleId());

        try {
            ResultForDeleteSale result = router.getRepo().deleteSale(param);
            switch (result.getStatus()) {
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return;
                case SUCCESS:
                    break;
            }

            render(generateParam());

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void handlePrevBtnAction() {
        ParamForGetManySales param = generateParam();

        param.setPage(param.getPage() - 1);
        if (param.getPage() < 1) {
            param.setPage(1);
        }

        render(param);
    }

    @FXML
    protected void handleNextBtnAction() {
        ParamForGetManySales param = generateParam();

        param.setPage(param.getPage() + 1);

        render(param);
    }

    @FXML
    protected void handlePageInputAction() {
        ParamForGetManySales param = generateParam();

        try {
            param.setPage(Integer.parseInt(pageInput.getText().trim()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid page number").showAndWait();
        }

        render(param);
    }
}
