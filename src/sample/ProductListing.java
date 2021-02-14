package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sungro.api.ParamForGetManyProducts;
import sungro.api.ParamForGetOneProduct;
import sungro.api.ResultForGetManyProducts;

import java.rmi.RemoteException;

public class ProductListing {
    private final Router router;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField categoryInput;
    @FXML
    private ChoiceBox<String> statusInput;
    @FXML
    private TableView<Product> tableView;
    @FXML
    private Text currentPageTxt;
    @FXML
    private Text maxPageTxt;
    @FXML
    private TextField pageInput;

    public ProductListing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetManyProducts param) {
        try {
            ResultForGetManyProducts result = router.getRepo().getManyProducts(param);

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
            for (sungro.api.Product product : result.getProducts()) {
                tableView.getItems().add(new Product(product));
            }

            currentPageTxt.setText(String.valueOf(result.getCurrentPage()));
            maxPageTxt.setText(String.valueOf(result.getMaxPage()));

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ParamForGetManyProducts generateParam() {
        ParamForGetManyProducts param = new ParamForGetManyProducts();

        param.setSessionId("0123456789abcdef");
        param.setName(nameInput.getText());
//        param.setEmail(categoryInput.getText());
//        param.setIdNumber(idNumberInput.getText());
//        param.setRole(statusInput.getValue());
        param.setPage(Integer.parseInt(currentPageTxt.getText().trim()));

        return param;
    }

    @FXML
    protected void handleAddProductBtnAction() {
        router.getProductAdding().render();
        router.getProductListingRoot().setVisible(false);
        router.getProductAddingRoot().setVisible(true);
    }

    @FXML
    protected void handleGoBtnAction() {
        render(generateParam());
    }

    @FXML
    protected void handleViewBtnAction() {
        Product product = tableView.getSelectionModel().getSelectedItem();

        if (product == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetOneProduct param = new ParamForGetOneProduct();
        param.setSessionId("0123456789abcdef");
        param.setProductId(product.getProductId());

//        if (router.getProductInfo().render(param)) {
//            router.getProductListingRoot().setVisible(false);
//            router.getProductInfoRoot().setVisible(true);
//        }
    }

    @FXML
    protected void handleEditBtnAction() {
        Product product = tableView.getSelectionModel().getSelectedItem();

        if (product == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetOneProduct param = new ParamForGetOneProduct();
        param.setSessionId("0123456789abcdef");
        param.setProductId(product.getProductId());

//        if (router.getProductEditing().render(param)) {
//            router.getProductListingRoot().setVisible(false);
//            router.getProductEditingRoot().setVisible(true);
//        }
    }

    @FXML
    protected void handleToggleStatusBtnAction() {

    }

    @FXML
    protected void handlePrevBtnAction() {
        ParamForGetManyProducts param = generateParam();

        param.setPage(param.getPage() - 1);
        if (param.getPage() < 1) {
            param.setPage(1);
        }

        render(param);
    }

    @FXML
    protected void handleNextBtnAction() {
        ParamForGetManyProducts param = generateParam();

        param.setPage(param.getPage() + 1);

        render(param);
    }

    @FXML
    protected void handlePageInputAction() {
        ParamForGetManyProducts param = generateParam();

        try {
            param.setPage(Integer.parseInt(pageInput.getText().trim()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid page number").showAndWait();
        }

        render(param);
    }
}
