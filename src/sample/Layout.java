package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import sungro.api.ParamForGetCurrentUser;

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

        router.getUserListing().render(router.getUserListing().generateParam());
        router.getUserListingRoot().setVisible(true);
    }
    @FXML
    protected void handleProductBtnAction() {
        for (Node node : stackPane.getChildren()) {
            node.setVisible(false);
        }

        router.getProductListing().render(router.getProductListing().generateParam());
        router.getProductListingRoot().setVisible(true);
    }
    @FXML
    protected void handleStockBtnAction() {
        for (Node node : stackPane.getChildren()) {
            node.setVisible(false);
        }

        router.getStockListing().render(router.getStockListing().generateParam());
        router.getStockListingRoot().setVisible(true);
    }
    @FXML
    protected void handleSalesBtnAction() {
        for (Node node : stackPane.getChildren()) {
            node.setVisible(false);
        }

        router.getSalesListing().render(router.getSalesListing().generateParam());
        router.getSalesListingRoot().setVisible(true);
    }
    @FXML
    protected void handleProfileBtnAction() {
        for (Node node : stackPane.getChildren()) {
            node.setVisible(false);
        }

        ParamForGetCurrentUser param = new ParamForGetCurrentUser();
        param.setSessionId("0123456789abcdef");
        router.getProfile().render(param);
        router.getProfileRoot().setVisible(true);
    }
}
