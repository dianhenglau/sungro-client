package sample;

import javafx.beans.property.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private final IntegerProperty productId;
    private final StringProperty name;
    private final StringProperty category;
    private final ObjectProperty<BigDecimal> productPrice;
    private final ObjectProperty<ImageView> productPic;
    private final StringProperty status;
    private final IntegerProperty createdByUserId;
    private final StringProperty createdByUserName;
    private final ObjectProperty<LocalDateTime> createdOn;

    public Product(){
        productId = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        category = new SimpleStringProperty();
        productPrice = new SimpleObjectProperty<>(BigDecimal.valueOf(0, 2));
        productPic = new SimpleObjectProperty<>(new ImageView());
        status = new SimpleStringProperty();
        createdByUserId = new SimpleIntegerProperty();
        createdByUserName = new SimpleStringProperty();
        createdOn = new SimpleObjectProperty<>(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
    }


    public Product(sungro.api.Product product){
        productId = new SimpleIntegerProperty(product.getProductId());
        name = new SimpleStringProperty(product.getName());
        category = new SimpleStringProperty(product.getCategory());
        productPrice = new SimpleObjectProperty<>(product.getProductPrice());
        productPic = new SimpleObjectProperty<>(new ImageView(
                new Image(new ByteArrayInputStream(product.getProductPic()),100,100,true,false)));
        status = new SimpleStringProperty(product.getStatus());
        createdByUserId = new SimpleIntegerProperty(product.getCreatedByUserId());
        createdByUserName = new SimpleStringProperty(product.getCreatedByUserName());
        createdOn = new SimpleObjectProperty<>(product.getCreatedOn());
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

    public ImageView getProductPic() {
        return productPic.get();
    }

    public ObjectProperty<ImageView> productPicProperty() {
        return productPic;
    }

    public void setProductPic(ImageView productPic) {
        this.productPic.set(productPic);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
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
}
