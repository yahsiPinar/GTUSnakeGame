package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.GameScreenController.BLOCK_SIZE;
/**
 * Created by Mehmet Gürol Çay on 25/10/2017.
 */
public class Food extends Pane{

    public Food(double x, double y) {
        System.out.println("food is created");
        this.setTranslateX(x);
        this.setTranslateY(y);
        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.RED);

        super.getChildren().addAll(food);
    }
}
