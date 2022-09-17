package com.ygorod.design.builder.test;

import com.ygorod.design.builder.Computer;

public class TestBuilderPattern {

	public static void main(String[] args) {
		//Using builder to get the object in a single line of code and 
        //without any inconsistent state or arguments management issues	
		System.out.println("Hello!");
		Computer comp = new Computer.ComputerBuilder("500 GB", "2 GB")
				.setBluetoothEnabled(true)
				.setGraphicsCardEnabled(true)
				.build();
		System.out.println("Computer conf:" + 
				"\nHDD: " + comp.getHDD() + "\nRAM: " + comp.getRAM() + 
				"\nGraphic: " + comp.isGraphicsCardEnabled() + "\nBluetuth: " + comp.isBluetoothEnabled());
	}
}
