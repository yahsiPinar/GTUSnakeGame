/**
 * Created by Mehmet Gürol Çay on 24/10/2017.
 */
package sample;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.w3c.dom.events.Event;

import java.util.ArrayList;
import java.util.ListIterator;

public class GameScreenController extends Parent /*implements EventHandler<KeyEvent>*/{

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction direction = Direction.RIGHT;

    private Scene gameScene;
    private Timeline timeline = new Timeline();
    //    private BorderPane gamePane;
    private ObservableList<Node> snake;

    private boolean running = false;  // snake
    private boolean moved = false; // application
    private boolean isEndless = true;
    private ArrayList<Integer> allScores = new ArrayList<Integer>();
    private int currentScore = 0;

    public static final int BLOCK_SIZE = 20; // part of snake

    // TODO: bunlar otomatik get edilmeli
    private double gameAreaWidth = 600;
    private double gameAreaHeight = 342;

    private double snakeSpeed = 0.15;

    @FXML
    private Button pauseButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Text scoreArea;

    @FXML
    private Text timeArea;

    @FXML
    void pauseGame(ActionEvent event) {

    }

    @FXML
    void saveGame(ActionEvent event) {

    }

    public GameScreenController() {

    }

    public void startGame() {
        direction = Direction.RIGHT;
        Body head = new Body(100, 100, Color.BLACK);
        snake.add(head);
        timeline.play();
        running = true;
    }

    public Parent createContent() throws Exception {
        Parent gameRoot = FXMLLoader.load(getClass().getResource("gameScreen.fxml"));
        Pane gamePane = (Pane) gameRoot.lookup("#gameArea");

        recursKey(gamePane);
        // TODO : eger gecmis oyun skorlari varsa burada yukle

        double getRootX = gamePane.getTranslateX();
        double getRootY = gamePane.getTranslateY();


        System.out.println("gameAreaWidth: " + gameAreaWidth);
        System.out.println("gameAreaHeight: " + gameAreaHeight);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        Text currentScoreVal = (Text) gameRoot.lookup("#scoreArea");
        currentScoreVal.setText("" + currentScore);

        Food food = new Food(getRootX, getRootY);
        foodRand(food);

        KeyFrame frame = new KeyFrame(Duration.seconds(snakeSpeed), event -> {
            if (!running)
                return;

            boolean toRemove = snake.size() > 1;
            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (direction) {
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (toRemove)
                snake.add(0, tail);

            for (Node rect : snake) {
                // checking collision for own body
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX() && tail.getTranslateY() == rect.getTranslateY()) {
                    allScores.add(currentScore);
                    currentScore = 0;
                    currentScoreVal.setText("" + currentScore);
                    restartGame();
                    break;
                }
            }

            if (isEndless) {
                fieldIsEndless((Body) tail); // duvardan gecme
            } else {
                fieldNOTEndless((Body) tail, currentScoreVal, food); // duvarda takilma
            }

            // checking collision for food
            if(tail.getTranslateX() == food.getTranslateX() && getTranslateY() == food.getTranslateY()) {
                foodRand(food);
                currentScore += 20;
                // TODO: buraya bizim foodlar gelecek, 5 yem yediginde yeni gorev mesela
                Body rect = new Body(tailX, tailY,Color.BLACK);
                snake.add(rect);
            }
        });

        timeline.getKeyFrames().addAll(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        gamePane.getChildren().addAll(food, snakeBody);

        return gameRoot;
    }

    private void restartGame() {

    }


    private void foodRand(Food food) {
        System.out.println("foodRand");
        food.setTranslateX((int) (Math.random() * (gameAreaWidth - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (gameAreaHeight - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        foodReset(snake, food);
    }

    private void foodReset(ObservableList<Node> snake, Food food) {
        boolean flag = true;
        while (flag) {
            flag = false;
            ListIterator<Node> iterator = snake.listIterator();
            while (iterator.hasNext()) {
                Node x = iterator.next();
                boolean match = x.getTranslateX() == food.getTranslateX() && x.getTranslateY() == food.getTranslateY();
                if (match) {
                    foodRand(food);
                    while (iterator.hasPrevious()) {
                        iterator.previous();
                    }
                }
            }
        }
    }

    // burasi duvardan gecmesini sagliyor.
    private void fieldIsEndless(Body tail) {

        // to the left screeen
        if (tail.getTranslateX() < 0)
            tail.setTranslateX(gameAreaWidth - BLOCK_SIZE);

        // right screen
        if (tail.getTranslateX() >= gameAreaWidth)
            tail.setTranslateX(0.0);

        //top screen
        if (tail.getTranslateY() < 0)
            tail.setTranslateY(gameAreaHeight - BLOCK_SIZE);

        //down screen
        if (tail.getTranslateY() >= gameAreaHeight)
            tail.setTranslateY(0.0);
    }

    // burasi duvardan gecmesin diye
    private void fieldNOTEndless(Body tail, Text scoresVal, Food food /*, Label lastScor, Label bestScor*/) {
        // below is code for field with EDGES. you can comment the block which is above and uncomment block below to make field with EDGES
        if (tail.getTranslateX() < 0 || tail.getTranslateX() >= gameAreaWidth || tail.getTranslateY() < 29 || tail.getTranslateY() >= gameAreaHeight) {
            allScores.add(currentScore);
            currentScore = 0;
            scoresVal.setText("" + currentScore);
            //  TODO : collision oldu oyunu yeniden baslat
//            restartGame();
            foodRand(food);
        }
    }

    public void recursKey(Pane gamePane) {
        gamePane.setOnKeyPressed(event -> {
            if (!moved)
                return;

            switch (event.getCode()) {
                case UP:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case DOWN:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case LEFT:
                    System.out.println("Left Arrow");
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case RIGHT:
                    System.out.println("Right Arrow");
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
            }

            moved = false;
        });
    }
}
