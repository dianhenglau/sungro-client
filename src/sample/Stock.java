package sample;

import javafx.beans.property.*;
import javafx.util.converter.BigDecimalStringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Stock {
    private final StringProperty sku;
    private final IntegerProperty productId;
    private final StringProperty productName;
    private final StringProperty productCategory;
    private final ObjectProperty<BigDecimal> productPrice;
//    private final ObjectProperty<byte[]> productPic;
    private final IntegerProperty quantity;
    private final ObjectProperty<LocalDate> expiryDate;
    private final IntegerProperty createdByUserId;
    private final StringProperty createdByUserName;
    private final ObjectProperty<LocalDateTime> createdOn;
    private final StringProperty sessionId;
//    private final IntegerProperty quantityVaried;
//    private final StringProperty remark;

    public Stock() {
        sku = new SimpleStringProperty();
        productId = new SimpleIntegerProperty();
        productName = new SimpleStringProperty();
        productCategory = new SimpleStringProperty();
        productPrice = new SimpleObjectProperty<>(BigDecimal.valueOf(0, 2));
//        productPic = new SimpleObjectProperty<>();
        quantity = new SimpleIntegerProperty();
        expiryDate = new SimpleObjectProperty<>(LocalDate.of(1970, 1, 1));
        createdByUserId = new SimpleIntegerProperty();
        createdByUserName = new SimpleStringProperty();
        createdOn = new SimpleObjectProperty<>(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
        sessionId = new SimpleStringProperty();
//        quantityVaried = new SimpleIntegerProperty();
//        remark = new SimpleStringProperty();
    }

    public Stock (sungro.api.Stock stock){
        sku = new SimpleStringProperty(stock.getSku());
        productId = new SimpleIntegerProperty(stock.getProductId());
        productName = new SimpleStringProperty(stock.getProductName());
        productCategory = new SimpleStringProperty(stock.getProductCategory());
        productPrice = new SimpleObjectProperty<>(stock.getProductPrice());
//        productPic = new SimpleObjectProperty<>(stock.getProductPic());
        quantity = new SimpleIntegerProperty(stock.getQuantity());
        expiryDate = new SimpleObjectProperty<>(stock.getExpiryDate());
        createdByUserId = new SimpleIntegerProperty(stock.getCreatedByUserId());
        createdByUserName = new SimpleStringProperty(stock.getCreatedByUserName());
        createdOn = new SimpleObjectProperty<>(stock.getCreatedOn());
        sessionId = new SimpleStringProperty();
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

    public int getProductId() {
        return productId.get();
    }

    public IntegerProperty productIdProperty() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public String getProductName() {
        return productName.get();
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getProductCategory() {
        return productCategory.get();
    }

    public StringProperty productCategoryProperty() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory.set(productCategory);
    }

    public BigDecimal getProductPrice() {
        return productPrice.get();
    }

    public ObjectProperty<BigDecimal> productPriceProperty() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice.set(productPrice);
    }

//    public byte[] getProductPic() {
//        return productPic.get();
//    }
//
//    public ObjectProperty<byte[]> productPicProperty() {
//        return productPic;
//    }
//
//    public void setProductPic(byte[] productPic) {
//        this.productPic.set(productPic);
//    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public LocalDate getExpiryDate() {
        return expiryDate.get();
    }

    public ObjectProperty<LocalDate> expiryDateProperty() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate.set(expiryDate);
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

    public String getSessionId() {
        return sessionId.get();
    }

    public StringProperty sessionIdProperty() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId.set(sessionId);
    }
}


