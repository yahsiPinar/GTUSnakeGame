package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.Collection;

import static sample.GameScreenController.BLOCK_SIZE;

/**
 * Created by Mehmet Gürol Çay on 25/10/2017.
 */
public class Body extends Pane{

    private Rectangle rectangle;

    public Body(double x, double y, Color color) {
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.rectangle = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        this.rectangle.setFill(color);

        super.getChildren().addAll(rectangle);
    }
}
