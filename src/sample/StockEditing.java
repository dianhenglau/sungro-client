package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sungro.api.*;
import sungro.api.Stock;
import java.rmi.RemoteException;

public class StockEditing {
    private final Router router;

    @FXML
    private Text SKUTxt;
    @FXML
    private Text productIDTxt;
    @FXML
    private Text nameTxt;
    @FXML
    private Text categoryTxt;
    @FXML
    private TextField changeInput;
    @FXML
    private DatePicker expiryDateInput;

    public StockEditing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetOneStock param) {
        try {
            ResultForGetOneStock result = router.getRepo().getOneStock(param);

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

            Stock stock = result.getStock();

            SKUTxt.setText(String.valueOf(stock.getSku()));
            productIDTxt.setText(String.valueOf(stock.getProductId()));
            nameTxt.setText(stock.getProductName());
            categoryTxt.setText(stock.getProductCategory());
            changeInput.setText(String.valueOf(stock.getQuantity()));
            expiryDateInput.setValue(stock.getExpiryDate());

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void handleBackBtnAction() {

        router.getStockEditingRoot().setVisible(false);
        router.getStockListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {

        ParamForSetStock param = new ParamForSetStock();
        param.setSessionId("0123456789abcdef");
        param.setSku(SKUTxt.getText());
        if (changeInput.getText().isBlank() ){
            new Alert(Alert.AlertType.ERROR, "quantity varied is empty.").showAndWait();
            return; //the bottom code do not run, so put return (结束）
        }
        param.setQuantityVaried(Integer.parseInt(changeInput.getText()));
//      param.setRemark(emailInput.getText());

        try {
            ResultForSetStock result = router.getRepo().setStock(param);

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
                case INVALID_QUANTITY_VARIED:
                    new Alert(Alert.AlertType.ERROR, "Invalid quality varied.").showAndWait();
                    return;
//                case MISSING_REMARK:
//                    new Alert(Alert.AlertType.ERROR, "Remark is required.").showAndWait();
//                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "User updated.").showAndWait();
                    break;
            }

            router.getStockListing().render(router.getStockListing().generateParam());
            router.getStockEditingRoot().setVisible(false);
            router.getStockListingRoot().setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
