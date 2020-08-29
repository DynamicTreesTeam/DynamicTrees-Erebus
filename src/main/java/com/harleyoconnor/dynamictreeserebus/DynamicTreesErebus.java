package com.harleyoconnor.dynamictreeserebus;

import com.ferreusveritas.dynamictrees.ModConstants;

import com.harleyoconnor.dynamictreeserebus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid= DynamicTreesErebus.MODID, name= DynamicTreesErebus.NAME, dependencies = DynamicTreesErebus.DEPENDENCIES)
public class DynamicTreesErebus {
	
	public static final String MODID = "dynamictreeserebus";
	public static final String NAME = "Dynamic Trees for The Erebus";
	public static final String DEPENDENCIES = "required-after:" + ModConstants.DYNAMICTREES_LATEST
			+ ";required-after:erebus";
	
	@Mod.Instance
	public static DynamicTreesErebus instance;
	
	@SidedProxy(clientSide = "com.harleyoconnor.dynamictreeserebus.proxy.ClientProxy", serverSide = "com.harleyoconnor.dynamictreeserebus.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		proxy.postInit();
	}
	
}
