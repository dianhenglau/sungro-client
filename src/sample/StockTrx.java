package sample;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockTrx {
    private final IntegerProperty stockTrxId;
    private final StringProperty sku;
    private final IntegerProperty quantityVaried;
    private final StringProperty remark;
    private final IntegerProperty createdByUserId;
    private final StringProperty createdByUserName;
    private final ObjectProperty<LocalDateTime> createdOn;
    private final StringProperty createdByUserNOn;

    public StockTrx() {
        stockTrxId = new SimpleIntegerProperty();
        sku = new SimpleStringProperty();
        quantityVaried = new SimpleIntegerProperty();
        remark = new SimpleStringProperty();
        createdByUserId = new SimpleIntegerProperty();
        createdByUserName = new SimpleStringProperty();
        createdOn = new SimpleObjectProperty<>(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
        createdByUserNOn = new SimpleStringProperty();
    }

    public StockTrx(sungro.api.StockTrx stockTrx) {
        stockTrxId = new SimpleIntegerProperty(stockTrx.getStockTrxId());
        sku = new SimpleStringProperty(stockTrx.getSku());
        quantityVaried = new SimpleIntegerProperty(stockTrx.getQuantityVaried());
        remark = new SimpleStringProperty(stockTrx.getRemark());
        createdByUserId = new SimpleIntegerProperty(stockTrx.getCreatedByUserId());
        createdByUserName = new SimpleStringProperty(stockTrx.getCreatedByUserName());
        createdOn = new SimpleObjectProperty<>(stockTrx.getCreatedOn());
        createdByUserNOn = new SimpleStringProperty(stockTrx.getCreatedByUserName()+"\n"+
                stockTrx.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public int getStockTrxId() {
        return stockTrxId.get();
    }

    public IntegerProperty stockTrxIdProperty() {
        return stockTrxId;
    }

    public void setStockTrxId(int stockTrxId) {
        this.stockTrxId.set(stockTrxId);
    }

    public String getSku() {
        return sku.get();
    }

    public StringProperty skuProperty() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku.set(sku);
    }

    public int getQuantityVaried() {
        return quantityVaried.get();
    }

    public IntegerProperty quantityVariedProperty() {
        return quantityVaried;
    }

    public void setQuantityVaried(int quantityVaried) {
        this.quantityVaried.set(quantityVaried);
    }

    public String getRemark() {
        return remark.get();
    }

    public StringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public int getCreatedByUserId() {
        return createdByUserId.get();
    }

    public IntegerProperty createdByUserIdProperty() {
        return createdByUserId;
    }

    public void setCreatedByUserId(int createdByUserId) {
        this.createdByUserId.set(createdByUserId);
    }

    public String getCreatedByUserName() {
        return createdByUserName.get();
    }

    public StringProperty createdByUserNameProperty() {
        return createdByUserName;
    }

    public void setCreatedByUserName(String createdByUserName) {
        this.createdByUserName.set(createdByUserName);
    }

    public LocalDateTime getCreatedOn() {
        return createdOn.get();
    }

    public ObjectProperty<LocalDateTime> createdOnProperty() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn.set(createdOn);
    }

    public String getCreatedByUserNOn() {
        return createdByUserNOn.get();
    }

    public StringProperty createdByUserNOnProperty() {
        return createdByUserNOn;
    }

    public void setCreatedByUserNOn(String createdByUserNOn) {
        this.createdByUserNOn.set(createdByUserNOn);
    }
}
