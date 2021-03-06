package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sungro.api.*;
import sungro.api.Stock;

import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;

public class StockInfo {
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
    private Text quantityTxt;
    @FXML
    private Text expiryDateTxt;
    @FXML
    private Text createdByUserNameTxt;
    @FXML
    private Text createdOnTxt;
    @FXML
    private Text quantityVaried;
    @FXML
    private Text remarkTxt;
    @FXML
    private Text transactionIDTxt;
    @FXML
    private TableView<StockTrx> tableView;
    @FXML
    private Text currentPageTxt;
    @FXML
    private Text maxPageTxt;
    @FXML
    private TextField pageInput;

    public StockInfo(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetManyStockTrx param) {
        try {
//            ParamForGetManyStockTrx param = new ParamForGetManyStockTrx();
//            param.setSessionId("0123456789abcdef");
//            param.setSku(param.getSku());
            ResultForGetManyStockTrx result = router.getRepo().getManyStockTrx(param);

            tableView.getItems().clear();
            for (sungro.api.StockTrx stockTrx : result.getStockTrx()) {
                tableView.getItems().add(new StockTrx(stockTrx)); // put the information via (for loop)
            }

            ParamForGetOneStock param1 = new ParamForGetOneStock();
            param1.setSessionId("0123456789abcdef");
            param1.setSku(param.getSku());
            ResultForGetOneStock result1 = router.getRepo().getOneStock(param1);

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

            Stock stock = result1.getStock();

            SKUTxt.setText(String.valueOf(stock.getSku()));
            productIDTxt.setText(String.valueOf(stock.getProductId()));
            nameTxt.setText(stock.getProductName());
            categoryTxt.setText(stock.getProductCategory());
            quantityTxt.setText(String.valueOf(stock.getQuantity()));
            expiryDateTxt.setText(String.valueOf(stock.getExpiryDate()));
            createdByUserNameTxt.setText(stock.getCreatedByUserName());
            createdOnTxt.setText(stock.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ParamForGetManyStockTrx generateParam() {
        ParamForGetManyStockTrx param = new ParamForGetManyStockTrx();

        param.setSessionId("0123456789abcdef");
        param.setSku(SKUTxt.getText());
        param.setPage(Integer.parseInt(currentPageTxt.getText().trim()));

        return param;
    }

    @FXML
    protected void handleEditBtnAction() {
        StockTrx stock = tableView.getSelectionModel().getSelectedItem();

        if (stock == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetOneStock param = new ParamForGetOneStock();
        param.setSessionId("0123456789abcdef");
        param.setSku(stock.getSku());

        if (router.getStockEditing().render(param)) {
            router.getStockInfoRoot().setVisible(false);
            router.getStockEditingRoot().setVisible(true);
        }
    }

    @FXML
    protected void handleBackBtnAction() {
        router.getStockInfoRoot().setVisible(false);
        router.getStockListingRoot().setVisible(true);
    }

    @FXML
    protected void handlePrevBtnAction() {
        ParamForGetManyStockTrx param = generateParam();

        param.setPage(param.getPage() - 1);
        if (param.getPage() < 1) {
            param.setPage(1);
        }

        render(param);
    }

    @FXML
    protected void handleNextBtnAction() {
        ParamForGetManyStockTrx param = generateParam();

        param.setPage(param.getPage() + 1);

        render(param);
    }

    @FXML
    protected void handlePageInputAction() {
        ParamForGetManyStockTrx param = generateParam();

        try {
            param.setPage(Integer.parseInt(pageInput.getText().trim()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid page number").showAndWait();
        }

        render(param);
    }
}
