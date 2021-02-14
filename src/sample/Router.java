package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import sungro.api.Repo;

import java.io.IOException;

public class Router {
    private final Repo repo;
    private final Scene scene;

    private final Layout layout;
    private final Parent layoutRoot;
    private final UserListing userListing;
    private final Parent userListingRoot;
    private final UserInfo userInfo;
    private final Parent userInfoRoot;
    private final UserAdding userAdding;
    private final Parent userAddingRoot;
    private final UserEditing userEditing;
    private final Parent userEditingRoot;

    private final ProductListing productListing;
    private final Parent productListingRoot;
    private final ProductAdding productAdding;
    private final Parent productAddingRoot;

    private final StockListing stockListing;
    private final Parent stockListingRoot;

    private final SalesListing salesListing;
    private final Parent salesListingRoot;

    public Router(Repo repo) throws IOException {
        this.repo = repo;

        layout = new Layout(this);
        FXMLLoader layoutLoader = new FXMLLoader(getClass().getResource("Layout.fxml"));
        layoutLoader.setController(layout);
        layoutRoot = layoutLoader.load();
        
        /*User*/
        userListing = new UserListing(this);
        FXMLLoader userListingLoader = new FXMLLoader(getClass().getResource("UserListing.fxml"));
        userListingLoader.setController(userListing);
        userListingRoot = userListingLoader.load();

        userInfo = new UserInfo(this);
        FXMLLoader userInfoLoader = new FXMLLoader(getClass().getResource("UserInfo.fxml"));
        userInfoLoader.setController(userInfo);
        userInfoRoot = userInfoLoader.load();

        userAdding = new UserAdding(this);
        FXMLLoader userAddingLoader = new FXMLLoader(getClass().getResource("UserAdding.fxml"));
        userAddingLoader.setController(userAdding);
        userAddingRoot = userAddingLoader.load();

        userEditing = new UserEditing(this);
        FXMLLoader userEditingLoader = new FXMLLoader(getClass().getResource("UserEditing.fxml"));
        userEditingLoader.setController(userEditing);
        userEditingRoot = userEditingLoader.load();

        /*Product*/
        productListing = new ProductListing(this);
        FXMLLoader productListingLoader = new FXMLLoader(getClass().getResource("ProductListing.fxml"));
        productListingLoader.setController(productListing);
        productListingRoot = productListingLoader.load();

        productAdding = new ProductAdding(this);
        FXMLLoader productAddingLoader = new FXMLLoader(getClass().getResource("ProductAdding.fxml"));
        productAddingLoader.setController(productAdding);
        productAddingRoot = productAddingLoader.load();

        /*Sales*/
        salesListing = new SalesListing(this);
        FXMLLoader salesListingLoader = new FXMLLoader(getClass().getResource("SalesListing.fxml"));
        salesListingLoader.setController(salesListing);
        salesListingRoot = salesListingLoader.load();
        /*Stock*/
        stockListing = new StockListing(this);
        FXMLLoader stockListingLoader = new FXMLLoader(getClass().getResource("StockListing.fxml"));
        stockListingLoader.setController(stockListing);
        stockListingRoot = stockListingLoader.load();

        /*User*/
        layout.addNode(userListingRoot);
        layout.addNode(userInfoRoot);
        layout.addNode(userAddingRoot);
        layout.addNode(userEditingRoot);

        /*Product*/
        layout.addNode(productListingRoot);
        layout.addNode(productAddingRoot);

        /*Stock*/
        layout.addNode(stockListingRoot);
        layout.addNode(salesListingRoot);


        scene = new Scene(layoutRoot);
        scene.getStylesheets().add("app.css");
    }
    /*User*/
    public Repo getRepo() {
        return repo;
    }

    public Scene getScene() {
        return scene;
    }

    public Layout getLayout() {
        return layout;
    }

    public Parent getLayoutRoot() {
        return layoutRoot;
    }

    public UserListing getUserListing() {
        return userListing;
    }

    public Parent getUserListingRoot() {
        return userListingRoot;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public Parent getUserInfoRoot() {
        return userInfoRoot;
    }

    public UserAdding getUserAdding() {
        return userAdding;
    }

    public Parent getUserAddingRoot() {
        return userAddingRoot;
    }

    public UserEditing getUserEditing() {
        return userEditing;
    }

    public Parent getUserEditingRoot() {
        return userEditingRoot;
    }

    /*Product*/
    public ProductListing getProductListing() {
        return productListing;
    }

    public Parent getProductListingRoot() {
        return productListingRoot;
    }
    public ProductAdding getProductAdding() {
        return productAdding;
    }

    public Parent getProductAddingRoot() {
        return productAddingRoot;
    }

    /*Stock*/
    public StockListing getStockListing() {
        return stockListing;
    }

    public Parent getStockListingRoot() {
        return stockListingRoot;
    }

    /*Sales*/
    public SalesListing getSalesListing() {
        return salesListing;
    }

    public Parent getSalesListingRoot() {
        return salesListingRoot;
    }
}
