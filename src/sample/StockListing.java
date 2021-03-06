package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sungro.api.*;

import java.rmi.RemoteException;

public class StockListing {
    private final Router router;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField categoryInput;
//    @FXML
//    private ChoiceBox<String> statusInput;
    @FXML
    private TextField SKUInput;
    @FXML
    private DatePicker expiryDateFromInput;
    @FXML
    private DatePicker expiryDateToInput;
    @FXML
    private TableView<Stock> tableView;
    @FXML
    private Text currentPageTxt;
    @FXML
    private Text maxPageTxt;
    @FXML
    private TextField pageInput;

    public StockListing(Router router) {
        this.router = router;
    }

    public boolean render(ParamForGetManyStock param) {
        try {
            ResultForGetManyStock result = router.getRepo().getManyStock(param);

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
            for (sungro.api.Stock stock : result.getStock()) {
                tableView.getItems().add(new Stock (stock));
            }

            currentPageTxt.setText(String.valueOf(result.getCurrentPage()));
            maxPageTxt.setText(String.valueOf(result.getMaxPage()));

            return true;

        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ParamForGetManyStock generateParam() {
        ParamForGetManyStock param = new ParamForGetManyStock();

        param.setSessionId("0123456789abcdef");
        param.setName(nameInput.getText());
        param.setCategory(categoryInput.getText());
        if (expiryDateFromInput.getValue() != null){
            param.setExpiryDateFrom(expiryDateFromInput.getValue());

        }
        if (expiryDateToInput.getValue() != null){
            param.setExpiryDateTo(expiryDateToInput.getValue());
        }
        param.setSku(SKUInput.getText());
        param.setPage(Integer.parseInt(currentPageTxt.getText().trim()));

        return param;
    }

    @FXML
    protected void handleAddStockBtnAction() {
        router.getStockAdding().render();
        router.getStockListingRoot().setVisible(false);
        router.getStockAddingRoot().setVisible(true);
    }

    @FXML
    protected void handleGoBtnAction() {
        render(generateParam());
    }

    @FXML
    protected void handleViewBtnAction() {
        Stock stock = tableView.getSelectionModel().getSelectedItem();

        if (stock == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetManyStockTrx param = new ParamForGetManyStockTrx();
        param.setSessionId("0123456789abcdef");
        param.setSku(stock.getSku());

        if (router.getStockInfo().render(param)) {
            router.getStockListingRoot().setVisible(false);
            router.getStockInfoRoot().setVisible(true);
        }
    }

    @FXML
    protected void handleEditBtnAction() {
        Stock stock = tableView.getSelectionModel().getSelectedItem();

        if (stock == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForGetOneStock param = new ParamForGetOneStock();
        param.setSessionId("0123456789abcdef");
        param.setSku(stock.getSku());

        if (router.getStockEditing().render(param)) {
            router.getStockListingRoot().setVisible(false);
            router.getStockEditingRoot().setVisible(true);
        }
    }

    @FXML
    protected void handleDeleteBtnAction() {
        Stock stock = tableView.getSelectionModel().getSelectedItem();

        if (stock == null) {
            new Alert(Alert.AlertType.ERROR, "No item selected").showAndWait();
            return;
        }

        ParamForDeleteStock param = new ParamForDeleteStock();
        param.setSessionId("0123456789abcdef");
        param.setSku(stock.getSku());

        try {
            ResultForDeleteStock result = router.getRepo().deleteStock(param);
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
        ParamForGetManyStock param = generateParam();

        param.setPage(param.getPage() - 1);
        if (param.getPage() < 1) {
            param.setPage(1);
        }

        render(param);
    }

    @FXML
    protected void handleNextBtnAction() {
        ParamForGetManyStock param = generateParam();

        param.setPage(param.getPage() + 1);

        render(param);
    }

    @FXML
    protected void handlePageInputAction() {
        ParamForGetManyStock param = generateParam();

        try {
            param.setPage(Integer.parseInt(pageInput.getText().trim()));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid page number").showAndWait();
        }

        render(param);
    }
}
