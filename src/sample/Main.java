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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Good Bye");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1099);
        Repo repo = (Repo) registry.lookup("Repo");

        ParamForGetManyUsers param = new ParamForGetManyUsers();
        ResultForGetManyUsers result = repo.getManyUsers(param);
        System.out.println(result.getStatus());

        ArrayList<User> users = result.getUsers();
        System.out.println(users.size());
        for (User user : users) {
            System.out.println(user.getFirstName());
        }

        launch(args);
    }
}
