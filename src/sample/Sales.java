package sample;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Sales {
    private final IntegerProperty saleId;
    private final StringProperty sku;
    private final IntegerProperty productId;
    private final StringProperty productName;
    private final StringProperty productCategory;
    private final ObjectProperty<BigDecimal> unitPrice;
    private final IntegerProperty soldQuantity;
    private final ObjectProperty<BigDecimal> subTotalPrice;
    private final IntegerProperty soldByUserId;
    private final StringProperty soldByUserName;
    private final ObjectProperty<LocalDateTime> soldOn;
    private final StringProperty product;

    public Sales(sungro.api.Sale sales){
        saleId = new SimpleIntegerProperty(sales.getSaleId());
        sku = new SimpleStringProperty(sales.getSku());
        productId = new SimpleIntegerProperty(sales.getProductId());
        productName = new SimpleStringProperty(sales.getProductName());
        product = new SimpleStringProperty("SKU: "+sales.getSku() +"\n"+"Product ID "+sales.getProductId()
                +"\n"+"Product Name: "+sales.getProductName());
        productCategory = new SimpleStringProperty(sales.getProductCategory());
        unitPrice = new SimpleObjectProperty<>(BigDecimal.valueOf(0, 2));
        soldQuantity = new SimpleIntegerProperty(sales.getSoldQuantity());
        subTotalPrice = new SimpleObjectProperty<>(sales.getSubTotalPrice());
        soldByUserId = new SimpleIntegerProperty(sales.getSoldByUserId());
        soldByUserName = new SimpleStringProperty(sales.getSoldByUserName());
        soldOn = new SimpleObjectProperty<>(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
    }

    public int getSaleId() {
        return saleId.get();
    }

    public IntegerProperty saleIdProperty() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId.set(saleId);
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

    public BigDecimal getUnitPrice() {
        return unitPrice.get();
    }

    public ObjectProperty<BigDecimal> unitPriceProperty() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public int getSoldQuantity() {
        return soldQuantity.get();
    }

    public IntegerProperty soldQuantityProperty() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity.set(soldQuantity);
    }

    public BigDecimal getSubTotalPrice() {
        return subTotalPrice.get();
    }

    public ObjectProperty<BigDecimal> subTotalPriceProperty() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(BigDecimal subTotalPrice) {
        this.subTotalPrice.set(subTotalPrice);
    }

    public int getSoldByUserId() {
        return soldByUserId.get();
    }

    public IntegerProperty soldByUserIdProperty() {
        return soldByUserId;
    }

    public void setSoldByUserId(int soldByUserId) {
        this.soldByUserId.set(soldByUserId);
    }

    public String getSoldByUserName() {
        return soldByUserName.get();
    }

    public StringProperty soldByUserNameProperty() {
        return soldByUserName;
    }

    public void setSoldByUserName(String soldByUserName) {
        this.soldByUserName.set(soldByUserName);
    }

    public LocalDateTime getSoldOn() {
        return soldOn.get();
    }

    public ObjectProperty<LocalDateTime> soldOnProperty() {
        return soldOn;
    }

    public void setSoldOn(LocalDateTime soldOn) {
        this.soldOn.set(soldOn);
    }
}
