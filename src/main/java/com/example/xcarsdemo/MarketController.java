package com.example.xcarsdemo;

import com.example.xcarsdemo.model.Car;
import com.example.xcarsdemo.model.CarService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
	@FXML
	private VBox chosenFruitCard;

	@FXML
	private Label fruitNameLable;

	@FXML
	private Label fruitPriceLabel;

	@FXML
	private ImageView fruitImg;

	@FXML
	private ScrollPane scroll;

	@FXML
	private GridPane grid;

	@FXML
	private Button btnAddToCart;

	@FXML
	private ComboBox<String> categories;
	private List<Car> cars = new ArrayList<>();
	private Image image;

	@FXML
	private Label nItems;
	@FXML
	private Button purchase;

	@FXML
	private Label shoppingLabel;

	@FXML
	private Label purchaseAlert;

	@FXML
	private HBox shopNow;
	private CarService service;

	private Car chosenCar;
	private String[] categoryList = {"All", "Toyota", "Opel", "Mercedes"};
	@FXML
	private Label model;
	private MyListener myListener;

	private void setChosenFruit(Car car) {
		fruitNameLable.setText(car.getName());
		fruitPriceLabel.setText(Main.CURRENCY + car.getPrice());
		image = new Image(getClass().getResourceAsStream(car.getImgSrc()));
		fruitImg.setImage(image);
		model.setText(car.getBrand());
		chosenFruitCard.setStyle("-fx-background-color: #" + car.getColor() + ";\n" +
				"    -fx-background-radius: 30;");
		if (car.isInCart()) {
			btnAddToCart.setText("Remove From Cart");
		} else {
			btnAddToCart.setText("Add To Cart");
		}
		chosenCar = car;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		service = new CarService();
		nItems.setText(service.carsInCart().size() + "");
		cars = service.getData();
		if (cars.size() > 0) {
			int chosenIndex = (int) (Math.random() * cars.size());
			setChosenFruit(cars.get(chosenIndex));
			myListener = new MyListener() {
				@Override
				public void onClickListener(Car car) {
					setChosenFruit(car);
				}
			};
		}
		try {
			displayProduct(cars);
			for (int i = 0; i < categoryList.length; i++) {
				categories.getItems().add(categoryList[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayProduct(List<Car> cars) throws IOException {
		displayProduct(cars, false, false);
	}

	public void displayProduct(List<Car> cars, boolean cartDisplay, boolean isPurchased) throws IOException {
		if (isPurchased) {
			shopNow.setDisable(true);
//            shopNow.setBackground(Background.fill(Color.valueOf("#2596be")));
			shoppingLabel.setText("Congrats! purchase successfully!");
//            shoppingLabel.setStyle("#ffffff");
			shopNow.setOpacity(2.5);
			purchaseAlert.setText("Your receipts is available for download");
//            cars = service.getData();
		} else if (cartDisplay && service.carsInCart().size() > 0) {
			shopNow.setDisable(false);
//            shopNow.setBackground(Background.fill(Color.valueOf("#2596be")));
			shoppingLabel.setText("Order now!");
//            shoppingLabel.setStyle("#ffffff");
			shopNow.setOpacity(2.5);
			purchaseAlert.setText("click if you're sure to continue");
		} else {
			shopNow.setDisable(true);
			shopNow.setOpacity(0.7);
			shoppingLabel.setText("Shopping online");
			purchaseAlert.setText("get your shopping in same day");
		}
		String id = grid.getId();
		grid = new GridPane();
		grid.setId(id);
		scroll.setContent(grid);
		int column = 0;
		int row = 1;
		for (int i = 0; i < cars.size(); i++) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("item.fxml"));
			AnchorPane anchorPane = fxmlLoader.load();

			ItemController itemController = fxmlLoader.getController();
			itemController.setData(cars.get(i), myListener);

			if (column == 3) {
				column = 0;
				row++;
			}

			grid.add(anchorPane, column++, row); //(child,column,row)
			//set grid width
			grid.setMinWidth(Region.USE_COMPUTED_SIZE);
			grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
			grid.setMaxWidth(Region.USE_PREF_SIZE);

			//set grid height
			grid.setMinHeight(Region.USE_COMPUTED_SIZE);
			grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
			grid.setMaxHeight(Region.USE_PREF_SIZE);

			GridPane.setMargin(anchorPane, new Insets(10));
		}
	}

	public void showCategory() throws IOException {
		var category = categories.getValue();
		List<Car> categoryCars = service.getCarsByCategory(categories.getValue());
		if (category == null || category == "All") {
			categoryCars = this.cars;
		}

		displayProduct(categoryCars);
	}

	public void onAddToCart(ActionEvent actionEvent) {
		chosenCar.setInCart(!chosenCar.isInCart());
		setChosenFruit(chosenCar);
		nItems.setText(service.carsInCart().size() + "");
	}

	public void onPurchase(MouseEvent mouseEvent) throws IOException {

		Writer writer = new FileWriter("Receipts/" + generateFileName(8));
		writer.write("Receipt for the Transaction.............\n");
		for (Car car :
				service.carsInCart()) {
			String content = car.getBrand() + " " + car.getName() + " D" + car.getPrice() + "\n";
			writer.write(content);
		}

		writer.write("Total: D" + service.getTotal() + "\n");
		writer.write("End of the transactions..........\n");
		writer.close();
		displayProduct(service.carsInCart(), true, true);
	}

	public void showCart() throws IOException {
		displayProduct(service.carsInCart(), true, false);
	}

	private String generateFileName(int length) {
		String strOptions = "1234567890abcdefghijklmnopqrstyvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str = "";
		while (str.length() < length) {
			str += strOptions.charAt((int) (Math.random() * strOptions.length()));
		}
		return str + ".txt";
	}

//
}
