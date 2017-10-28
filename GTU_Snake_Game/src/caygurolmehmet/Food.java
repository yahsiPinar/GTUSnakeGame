package caygurolmehmet;

/**
 * Created by Mehmet Gürol Çay on 25/10/2017.
 */

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static caygurolmehmet.GameScreenController.FOOD_BLOCK_SIZE;

public class Food extends Pane{

    public Food(double x, double y) {
        System.out.println("food is created");
        this.setTranslateX(x);
        this.setTranslateY(y);
        Rectangle food = new Rectangle(FOOD_BLOCK_SIZE, FOOD_BLOCK_SIZE);
        food.setFill(Color.RED);

        super.getChildren().addAll(food);
    }
}
