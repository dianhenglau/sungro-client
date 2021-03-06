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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class ProductEditing {
    private final Router router;

    @FXML
    private ImageView productPicView;
    @FXML
    private TextField productPicInput;
    @FXML
    private Text productIdTxt;
    @FXML
    private TextField productNameInput;
    @FXML
    private ChoiceBox<String> categoryInput;
    @FXML
    private TextField priceInput;
    @FXML
    private ChoiceBox<String> statusInput;

    public ProductEditing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetOneProduct param) {
        try {
            ResultForGetOneProduct result = router.getRepo().getOneProduct(param);

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

            sungro.api.Product product = result.getProduct();

//            if (product.getProductPic().length == 0) {
//                productPicView.setImage(new Image("profile.png"));
//            } else {
//                productPicView.setImage(new Image(new ByteArrayInputStream(product.getProductPic())));
//            }

            productIdTxt.setText(String.valueOf(product.getProductId()));
            productNameInput.setText(product.getName());
            categoryInput.setValue(product.getCategory());
            priceInput.setText(String.valueOf(product.getProductPrice()));

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getProductEditingRoot().setVisible(false);
        router.getProductListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {

        ParamForSetProduct param = new ParamForSetProduct();
        param.setSessionId("0123456789abcdef");
        param.setProductId(Integer.parseInt(productIdTxt.getText()));
        param.setName(productNameInput.getText());
        param.setCategory(categoryInput.getValue());
        param.setProductPrice(new BigDecimal(priceInput.getText()));
        param.setStatus(statusInput.getValue());

//        if (!productPicInput.getText().isEmpty()) {
//            try {
//                param.setProductPic(Files.readAllBytes(Paths.get(productPicInput.getText())));
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//        }

        try {
            ResultForSetProduct result = router.getRepo().setProduct(param);

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
                case MISSING_NAME:
                    new Alert(Alert.AlertType.ERROR, "Name is required.").showAndWait();
                    return;
                case REPEATED_NAME:
                    new Alert(Alert.AlertType.ERROR, "This name has been used.").showAndWait();
                    return;
                case MISSING_CATEGORY:
                    new Alert(Alert.AlertType.ERROR, "Category is required.").showAndWait();
                    return;
                case INVALID_CATEGORY:
                    new Alert(Alert.AlertType.ERROR, "Invalid category format.").showAndWait();
                    return;
                case MISSING_PRODUCT_PRICE:
                    new Alert(Alert.AlertType.ERROR, "Product price is required.").showAndWait();
                    return;
                case INVALID_PRODUCT_PRICE:
                    new Alert(Alert.AlertType.ERROR, "Invalid product price.").showAndWait();
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

            router.getProductListing().render(router.getProductListing().generateParam());
            router.getProductEditingRoot().setVisible(false);
            router.getProductListingRoot().setVisible(true);

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
            productPicView.setImage(new Image(selectedFile.toURI().normalize().toString()));
            productPicInput.setText(selectedFile.getPath());
        }
    }
}
