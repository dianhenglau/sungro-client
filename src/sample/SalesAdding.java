package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sungro.api.*;
import sungro.api.Product;
import sungro.api.Stock;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SalesAdding {
    private final Router router;

    @FXML
    private TextField SKUInput;
    @FXML
    private TextField quantityInput;
    @FXML
    private Text skuTxt;
    @FXML
    private ImageView productPicView;
    @FXML
    private Text productIdTxt;
    @FXML
    private Text nameTxt;
    @FXML
    private Text categoryTxt;
    @FXML
    private Text quantityTxt;
    @FXML
    private Text expirydateTxt;

    public SalesAdding(Router router) {
        this.router = router;
    }


    public boolean render() {
        productPicView.setImage(new Image("profile.png"));
        skuTxt.setText("");
        productIdTxt.setText("");
        nameTxt.setText("");
        categoryTxt.setText("");
        quantityTxt.setText("");
        expirydateTxt.setText("");

        return true;
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
                case  NOT_FOUND:
                    new Alert(Alert.AlertType.ERROR, "Item not found.").showAndWait();
                    return false;
                case SUCCESS:
                    break;
            }

            Stock stock = result.getStock();

            if (stock.getProductPic().length == 0) {
                productPicView.setImage(new Image("profile.png"));
            } else {
                productPicView.setImage(new Image(new ByteArrayInputStream(stock.getProductPic())));
            }

            skuTxt.setText(String.valueOf(stock.getSku()));
            productIdTxt.setText(String.valueOf(stock.getProductId()));
            nameTxt.setText(String.valueOf(stock.getProductName()));
            categoryTxt.setText(String.valueOf(stock.getProductCategory()));
            quantityTxt.setText(String.valueOf(stock.getQuantity()));
            expirydateTxt.setText(stock.getExpiryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    protected void handleBackBtnAction() {
        router.getSalesAddingRoot().setVisible(false);
        router.getSalesListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {

        ParamForAddSale param = new ParamForAddSale();
        param.setSessionId(router.getSessionId());
        param.setSku(SKUInput.getText());
        param.setSoldQuantity(Integer.parseInt(quantityInput.getText()));

        try {
            ResultForAddSale result = router.getRepo().addSale(param);

            switch (result.getStatus()) {
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return;
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
                case MISSING_SKU:
                    new Alert(Alert.AlertType.ERROR, "SKU is required.").showAndWait();
                    return;
                case INVALID_SKU:
                    new Alert(Alert.AlertType.ERROR, "Invalid SKU.").showAndWait();
                    return;
                case MISSING_QUANTITY:
                    new Alert(Alert.AlertType.ERROR, "Quantity is required.").showAndWait();
                    return;
                case  INVALID_QUANTITY:
                    new Alert(Alert.AlertType.ERROR, "Invalid quantity.").showAndWait();
                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "New product item added.").showAndWait();
                    break;
            }

            router.getSalesListing().render(router.getSalesListing().generateParam());
            router.getSalesAddingRoot().setVisible(false);
            router.getSalesListingRoot().setVisible(true);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    protected void handleChooseBtnAction() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Choose profile picture");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
//                new FileChooser.ExtensionFilter("All Files", "*.*")
//        );
//
//        File selectedFile = fileChooser.showOpenDialog(router.getScene().getWindow());
//        if (selectedFile != null) {
//            productPicView.setImage(new Image(selectedFile.toURI().normalize().toString()));
//            ProductPicInput.setText(selectedFile.getPath());
//        }
//    }
        public ParamForGetOneStock generateParam() {
            ParamForGetOneStock param = new ParamForGetOneStock();

            param.setSessionId(router.getSessionId());
            param.setSku(SKUInput.getText());

            return param;
        }
        @FXML
        protected void handleGoBtnAction() {
            render(generateParam());
        }
}
