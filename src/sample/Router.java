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

    public Router(Repo repo) throws IOException {
        this.repo = repo;

        layout = new Layout(this);
        FXMLLoader layoutLoader = new FXMLLoader(getClass().getResource("Layout.fxml"));
        layoutLoader.setController(layout);
        layoutRoot = layoutLoader.load();

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

        layout.addNode(userListingRoot);
        layout.addNode(userInfoRoot);
        layout.addNode(userAddingRoot);

        scene = new Scene(layoutRoot);
        scene.getStylesheets().add("app.css");
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
}
