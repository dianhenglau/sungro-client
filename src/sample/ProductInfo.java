package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sungro.api.ParamForGetOneProduct;
import sungro.api.Product;
import sungro.api.ResultForGetOneProduct;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public class ProductInfo {
    private final Router router;

    @FXML
    private ImageView productPicView;
    @FXML
    private Text productIdTxt;
    @FXML
    private Text productNameTxt;
    @FXML
    private Text categoryTxt;
    @FXML
    private Text priceTxt;
    @FXML
    private Text createdByUserNameTxt;
    @FXML
    private Text createdOnTxt;

    public ProductInfo(Router router) {
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

            Product product = result.getProduct();

            if (product.getProductPic().length == 0) {
                productPicView.setImage(new Image("profile.png"));
            } else {
                productPicView.setImage(new Image(new ByteArrayInputStream(product.getProductPic())));
            }

            productIdTxt.setText(String.valueOf(product.getProductId()));
            productNameTxt.setText(product.getName());
            categoryTxt.setText(product.getCategory());
            priceTxt.setText(String.valueOf(product.getProductPrice()));
            createdByUserNameTxt.setText(product.getCreatedByUserName());
            createdOnTxt.setText(product.getCreatedOn().toString());

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }

    }

    @FXML
    protected void handleEditBtnAction() {

        ParamForGetOneProduct param = new ParamForGetOneProduct();
        param.setSessionId(router.getSessionId());
        param.setProductId(Integer.parseInt(productIdTxt.getText()));

        if (router.getProductEditing().render(param)) {
            router.getProductInfoRoot().setVisible(false);
            router.getProductEditingRoot().setVisible(true);
        }
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getProductInfoRoot().setVisible(false);
        router.getProductListingRoot().setVisible(true);
    }
}
