package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import sungro.api.ParamForAddProduct;
import sungro.api.ParamForAddUser;
import sungro.api.ResultForAddProduct;
import sungro.api.ResultForAddUser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

public class ProductAdding {
    private final Router router;

    @FXML
    private ImageView productPicView;
    @FXML
    private TextField productPicInput;
    @FXML
    private TextField productNameInput;
    @FXML
    private ChoiceBox<String> categoryInput;
    @FXML
    private TextField priceInput;



    public ProductAdding(Router router) {
        this.router = router;
    }


    public boolean render() {
        productPicView.setImage(new Image("profile.png"));
        productPicInput.setText("");
        productNameInput.setText("");

        categoryInput.setValue(categoryInput.getItems().get(0));
        priceInput.setText("");

        return true;
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getUserAddingRoot().setVisible(false);
        router.getUserListingRoot().setVisible(true);
    }

    @FXML
    protected void handleSaveBtnAction() {

        ParamForAddProduct param = new ParamForAddProduct();
        param.setSessionId("0123456789abcdef");
        param.setName(productNameInput.getText());
        param.setCategory(categoryInput.getValue());

        param.setProductPrice(new BigDecimal (priceInput.getText()));


        if (!productPicInput.getText().isEmpty()) {
            try {
                param.setProductPic(Files.readAllBytes(Paths.get(productPicInput.getText())));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            ResultForAddProduct result = router.getRepo().addProduct(param);

            switch (result.getStatus()) {
                case INVALID_SESSION_ID:
                    new Alert(Alert.AlertType.ERROR, "Invalid session ID. Please login again.").showAndWait();
                    return;
                case SERVER_ERROR:
                    new Alert(Alert.AlertType.ERROR, "Server error. It's not your fault.").showAndWait();
                    return;
//                case MISSING_FIRST_NAME:
//                    new Alert(Alert.AlertType.ERROR, "First name is required.").showAndWait();
//                    return;
//                case MISSING_LAST_NAME:
//                    new Alert(Alert.AlertType.ERROR, "Last name is required.").showAndWait();
//                    return;
//                case MISSING_EMAIL:
//                    new Alert(Alert.AlertType.ERROR, "Email is required.").showAndWait();
//                    return;
//                case INVALID_EMAIL:
//                    new Alert(Alert.AlertType.ERROR, "Invalid email format.").showAndWait();
//                    return;
//                case REPEATED_EMAIL:
//                    new Alert(Alert.AlertType.ERROR, "This email has been used.").showAndWait();
//                    return;
//                case MISSING_ID_NUMBER:
//                    new Alert(Alert.AlertType.ERROR, "ID number is required.").showAndWait();
//                    return;
//                case REPEATED_ID_NUMBER:
//                    new Alert(Alert.AlertType.ERROR, "This ID number has been used.").showAndWait();
//                    return;
//                case MISSING_ID_TYPE:
//                    new Alert(Alert.AlertType.ERROR, "ID type is required.").showAndWait();
//                    return;
//                case INVALID_ID_TYPE:
//                    new Alert(Alert.AlertType.ERROR, "Invalid ID type.").showAndWait();
//                    return;
//                case MISSING_ROLE:
//                    new Alert(Alert.AlertType.ERROR, "Role is required.").showAndWait();
//                    return;
//                case INVALID_ROLE:
//                    new Alert(Alert.AlertType.ERROR, "Invalid role.").showAndWait();
//                    return;
//                case MISSING_PASSWORD:
//                    new Alert(Alert.AlertType.ERROR, "Password is required.").showAndWait();
//                    return;
                case MISSING_STATUS:
                    new Alert(Alert.AlertType.ERROR, "Status is required.").showAndWait();
                    return;
                case INVALID_STATUS:
                    new Alert(Alert.AlertType.ERROR, "Invalid status.").showAndWait();
                    return;
                case SUCCESS:
                    new Alert(Alert.AlertType.INFORMATION, "New product item added.").showAndWait();
                    break;
            }

            router.getProductListing().render(router.getProductListing().generateParam());
            router.getProductAddingRoot().setVisible(false);
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
