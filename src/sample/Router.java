package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import sungro.api.Repo;

import java.io.IOException;

public class Router {
    private String sessionId;

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
    private final ProductInfo productInfo;
    private final Parent productInfoRoot;
    private final ProductEditing productEditing;
    private final Parent productEditingRoot;

    private final StockListing stockListing;
    private final Parent stockListingRoot;
    private final StockAdding stockAdding;
    private final Parent stockAddingRoot;
    private final StockInfo stockInfo;
    private final Parent stockInfoRoot;
    private final StockEditing stockEditing;
    private final Parent stockEditingRoot;

    private final SalesListing salesListing;
    private final Parent salesListingRoot;
    private final SalesAdding salesAdding;
    private final Parent salesAddingRoot;

    private final Profile profile;
    private final Parent profileRoot;

    private final Login login;
    private final Parent loginRoot;

    private final Dashboard dashboard;
    private final Parent dashboardRoot;

    public Router(Repo repo) throws IOException {
        this.repo = repo;

        sessionId = "";

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

        productInfo = new ProductInfo(this);
        FXMLLoader productInfoLoader = new FXMLLoader(getClass().getResource("ProductInfo.fxml"));
        productInfoLoader.setController(productInfo);
        productInfoRoot = productInfoLoader.load();

        productEditing = new ProductEditing(this);
        FXMLLoader productEditingLoader = new FXMLLoader(getClass().getResource("productEditing.fxml"));
        productEditingLoader.setController(productEditing);
        productEditingRoot = productEditingLoader.load();

        /*Stock*/
        stockListing = new StockListing(this);
        FXMLLoader stockListingLoader = new FXMLLoader(getClass().getResource("StockListing.fxml"));
        stockListingLoader.setController(stockListing);
        stockListingRoot = stockListingLoader.load();

        stockAdding = new StockAdding(this);
        FXMLLoader stockAddingLoader = new FXMLLoader(getClass().getResource("StockAdding.fxml"));
        stockAddingLoader.setController(stockAdding);
        stockAddingRoot = stockAddingLoader.load();

        stockInfo = new StockInfo(this);
        FXMLLoader stockInfoLoader = new FXMLLoader(getClass().getResource("StockInfo.fxml"));
        stockInfoLoader.setController(stockInfo);
        stockInfoRoot = stockInfoLoader.load();

        stockEditing = new StockEditing(this);
        FXMLLoader stockEditingLoader = new FXMLLoader(getClass().getResource("StockEditing.fxml"));
        stockEditingLoader.setController(stockEditing);
        stockEditingRoot = stockEditingLoader.load();


        /*Sales*/
        salesListing = new SalesListing(this);
        FXMLLoader salesListingLoader = new FXMLLoader(getClass().getResource("SalesListing.fxml"));
        salesListingLoader.setController(salesListing);
        salesListingRoot = salesListingLoader.load();

        salesAdding = new SalesAdding(this);
        FXMLLoader salesAddingLoader = new FXMLLoader(getClass().getResource("SalesAdding.fxml"));
        salesAddingLoader.setController(salesAdding);
        salesAddingRoot = salesAddingLoader.load();


        /*User*/
        profile = new Profile(this);
        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        profileLoader.setController(profile);
        profileRoot = profileLoader.load();
        
        login = new Login(this);
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        loginLoader.setController(login);
        loginRoot = loginLoader.load();

        dashboard = new Dashboard(this);
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        dashboardLoader.setController(dashboard);
        dashboardRoot = dashboardLoader.load();
        
        layout.addNode(userListingRoot);
        layout.addNode(userInfoRoot);
        layout.addNode(userAddingRoot);
        layout.addNode(userEditingRoot);
        layout.addNode(profileRoot);


        /*Product*/
        layout.addNode(productListingRoot);
        layout.addNode(productAddingRoot);
        layout.addNode(productInfoRoot);
        layout.addNode(productEditingRoot);

        /*Stock*/
        layout.addNode(stockListingRoot);
        layout.addNode(stockAddingRoot);
        layout.addNode(stockInfoRoot);
        layout.addNode(stockEditingRoot);

        /*Sales*/
        layout.addNode(salesListingRoot);
        layout.addNode(salesAddingRoot);

        /*Dashboard*/
        layout.addNode(dashboardRoot);

        StackPane stackpane = new StackPane();
        stackpane.getChildren().add(layoutRoot);
        stackpane.getChildren().add(loginRoot);

        scene = new Scene(stackpane);
        scene.getStylesheets().add("app.css");
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

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

    /*User*/
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

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public Parent getProductInfoRoot() {
        return productInfoRoot;
    }

    public ProductEditing getProductEditing() {
        return productEditing;
    }

    public Parent getProductEditingRoot() {
        return productEditingRoot;
    }

    /*Stock*/
    public StockListing getStockListing() {
        return stockListing;
    }

    public Parent getStockListingRoot() {
        return stockListingRoot;
    }

    public StockAdding getStockAdding() {
        return stockAdding;
    }

    public Parent getStockAddingRoot() {
        return stockAddingRoot;
    }

    public StockInfo getStockInfo() {
        return stockInfo;
    }

    public Parent getStockInfoRoot() {
        return stockInfoRoot;
    }

    public StockEditing getStockEditing() {
        return stockEditing;
    }

    public Parent getStockEditingRoot() {
        return stockEditingRoot;
    }

    /*Sales*/
    public SalesListing getSalesListing() {
        return salesListing;
    }

    public Parent getSalesListingRoot() {
        return salesListingRoot;
    }

    public SalesAdding getSalesAdding() {
        return salesAdding;
    }

    public Parent getSalesAddingRoot() {
        return salesAddingRoot;
    }


    public Profile getProfile() {
        return profile;
    }

    public Parent getProfileRoot() {
        return profileRoot;
    }

    public Login getLogin() {
        return login;
    }

    public Parent getLoginRoot() {
        return loginRoot;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public Parent getDashboardRoot() {
        return dashboardRoot;
    }
}
