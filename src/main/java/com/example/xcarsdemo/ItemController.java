package com.example.xcarsdemo;

import com.example.xcarsdemo.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(car);
    }

    private Car car;
    private MyListener myListener;

    public void setData(Car car, MyListener myListener) {
        this.car = car;
        this.myListener = myListener;
        nameLabel.setText(car.getName());
        priceLable.setText(Main.CURRENCY + car.getPrice());
        Image image = new Image(getClass().getResourceAsStream(car.getImgSrc()));
        img.setImage(image);
    }
}
