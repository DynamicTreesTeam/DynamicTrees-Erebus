package com.harleyoconnor.dynamictreeserebus.proxy;


import com.harleyoconnor.dynamictreeserebus.growth.CustomCellKits;

public class CommonProxy {
	
	public void preInit() {
		// Initialise custom cell kits.
		new CustomCellKits();
	}
	
	public void init() {

	}
	
	public void postInit() {
	}
	
}
