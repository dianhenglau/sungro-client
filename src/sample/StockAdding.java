package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sungro.api.ParamForAddProduct;
import sungro.api.ParamForAddStock;
import sungro.api.ResultForAddProduct;
import sungro.api.ResultForAddStock;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class StockAdding {
    private final Router router;

    @FXML
    private TextField productIdInput;
    @FXML
    private TextField quantityInput;
    @FXML
    private TextField remarkInput;
    @FXML
    private DatePicker expiryDateInput;
//    @FXML
//    private ChoiceBox<String> statusInput;


    public StockAdding(Router router) {
        this.router = router;
    }


    public boolean render() {
        productIdInput.setText("");
        quantityInput.setText("");
        remarkInput.setText("");
        expiryDateInput.setValue(expiryDateInput.getValue());
   //     statusInput.setValue(statusInput.getItems().get(0));

        return true;
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getStockAddingRoot().setVisible(false);
        router.getStockListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {

        ParamForAddStock param = new ParamForAddStock();
        param.setSessionId(router.getSessionId());
        param.setProductId(Integer.parseInt(productIdInput.getText()));
        param.setQuantity(Integer.parseInt(quantityInput.getText()));
        param.setExpiryDate(expiryDateInput.getValue());
        param.setRemark(remarkInput.getText());

        try {
            ResultForAddStock result = router.getRepo().addStock(param);

            switch (result.getStatus()) {
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return;
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
                case MISSING_PRODUCT_ID:
                    new Alert(Alert.AlertType.ERROR, "ProductID is required.").showAndWait();
                    return;
                case INVALID_PRODUCT_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid productID.").showAndWait();
                    return;
                case MISSING_QUANTITY:
                    new Alert(Alert.AlertType.ERROR, "Quantity is required.").showAndWait();
                    return;
                case  INVALID_QUANTITY:
                    new Alert(Alert.AlertType.ERROR, "Invalid quantity.").showAndWait();
                    return;
                case  MISSING_EXPIRY_DATE:
                    new Alert(Alert.AlertType.ERROR, "Expiry date is required.").showAndWait();
                    return;
                case INVALID_EXPIRY_DATE:
                    new Alert(Alert.AlertType.ERROR, "Invalid expiry date.").showAndWait();
                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "New product item added.").showAndWait();
                    break;
            }

            router.getStockListing().render(router.getStockListing().generateParam());
            router.getStockAddingRoot().setVisible(false);
            router.getStockListingRoot().setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
