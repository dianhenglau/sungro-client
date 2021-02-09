package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Layout {
    private final Router router;

    @FXML
    private ImageView profilePicView;
    @FXML
    private StackPane stackPane;

    public Layout(Router router) {
        this.router = router;
    }

    public void addNode(Parent node) {
        node.setVisible(false);
        stackPane.getChildren().add(node);
    }

    public void render() {
        profilePicView.setImage(new Image("profile.png"));
    }

    @FXML
    protected void handleUsersBtnAction() {
        for (Node node : stackPane.getChildren()) {
            node.setVisible(false);
        }

        router.getUserListingRoot().setVisible(true);
    }
}
