package com.zyp.springboot.learn.test.bean;

import org.springframework.beans.factory.annotation.Value;

public class Car {

	private int price;

	@Value("${brand}")
	private String brand;

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Car{" +
				"price=" + price +
				", brand='" + brand + '\'' +
				'}';
	}
}
