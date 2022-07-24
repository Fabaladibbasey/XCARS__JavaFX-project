package com.example.xcarsdemo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CarService {
	private List<Car> cars = new ArrayList<>();
	public List<Car> getData() {
		Car car;

		car = new Car();
		car.setName("CLA");
		car.setBrand("Mercedes");
		car.setPrice(79677);
		car.setImgSrc("img/Mercedes_CLA.jpeg");
		car.setColor("6A7324");
		cars.add(car);

		car = new Car();
		car.setName("Corsa");
		car.setPrice(347548.99);
		car.setImgSrc("img/Opel_Corsa.jpg");
		car.setColor("A7745B");
		car.setBrand("Opel");
		cars.add(car);

		car = new Car();
		car.setName("Crossland");
		car.setBrand("Opel");
		car.setPrice(45743.50);
		car.setImgSrc("img/Opel_Crossland.jpg");
		car.setColor("F16C31");
		cars.add(car);

		car = new Car();
		car.setName("Coupe");
		car.setBrand("Mercedes");
		car.setPrice(45789476.99);
		car.setImgSrc("img/mercedes_Coupe.jpeg");
		car.setColor("291D36");
		cars.add(car);

		car = new Car();
		car.setName("Carrola");
		car.setBrand("Toyota");
		car.setPrice(498759.99);
		car.setImgSrc("img/toyota_Carrola.png");
		car.setColor("22371D");
		cars.add(car);

		car = new Car();
		car.setName("Astra");
		car.setBrand("Opel");
		car.setPrice(5794.57);
		car.setImgSrc("img/Opel_ASTRA.jpeg");
		car.setColor("FB5D03");
		cars.add(car);

		car = new Car();
		car.setName("Benz Suv");
		car.setBrand("Mercedes");
		car.setPrice(67486.99);
		car.setImgSrc("img/mercedes_BenzSuv.jpg");
		car.setColor("80080C");
		cars.add(car);

		car = new Car();
		car.setName("Corsa");
		car.setBrand("Opel");
		car.setPrice(47984.99);
		car.setImgSrc("img/Opel.jpeg");
		car.setColor("FFB605");
		cars.add(car);

		car = new Car();
		car.setName("L");
		car.setBrand("Toyota");
		car.setPrice(475984.99);
		car.setImgSrc("img/toyota2.jpeg");
		car.setColor("5F060E");
		cars.add(car);

		car = new Car();
		car.setName("LE");
		car.setBrand("Toyota");
		car.setPrice(45874.99);
		car.setImgSrc("img/toyota.jpeg");
		car.setColor("E7C00F");
		cars.add(car);

		return cars;
	}

	public List<Car> carsInCart(){
		List<Car> cartProducts = new ArrayList<>();
		for (Car car :
				this.cars) {
			if (car.isInCart()) {
				cartProducts.add(car);
			}
		}
		return  cartProducts;
	}

	public double getTotal(){
		long sum = 0;
		for (Car car :
				carsInCart()) {
			sum += car.getPrice();
		}
		return sum;
	}

	public List<Car> getCarsByCategory(String category){
		List<Car> categories = new ArrayList<>();
		for (Car car :
				cars) {
			if (Objects.equals(car.getBrand(), category)) {
				categories.add(car);
			}
			}
		return  categories;
	}
}
