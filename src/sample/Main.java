package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sungro.api.ParamForGetManyUsers;
import sungro.api.Repo;
import sungro.api.ResultForGetManyUsers;
import sungro.api.User;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.getRegistry(1099);
        Repo repo = (Repo) registry.lookup("Repo");

        Router router = new Router(repo);
        router.getLayout().render();
        router.getUserListing().render(router.getUserListing().generateParam());
        router.getUserListingRoot().setVisible(true);

        primaryStage.setTitle("Sun Grocery");
        primaryStage.setScene(router.getScene());
        primaryStage.show();
    }
}
