package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenController /*implements EventHandler<KeyEvent>*/{

    private Stage stage;

    @FXML
    private Button newGame;

    @FXML
    private Button load;

    @FXML
    private Button exit;

    @FXML
    public void exitGame(ActionEvent event) {

    }

    @FXML
    public void loadGame(ActionEvent event) {
        stage = (Stage) load.getScene().getWindow();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("loadScreen.fxml"));
            Scene gameScene = new Scene(root);
            stage.setScene(gameScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void startGame(ActionEvent event) {
        System.out.println(event.toString());
        stage = (Stage) newGame.getScene().getWindow();

        GameScreenController gameScreenController = new GameScreenController();

        Scene gameScene = null;
        try {
            gameScene = new Scene(gameScreenController.createContent());
            gameScreenController.recursKey(gameScene);
            stage.setScene(gameScene);
            stage.show();
            gameScreenController.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
