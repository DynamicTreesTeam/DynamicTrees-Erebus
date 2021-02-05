package com.harleyoconnor.dynamictreeserebus;

import com.harleyoconnor.dynamictreeserebus.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Main mod class.
 *
 * @author Harley O'Connor
 */
@Mod(modid= AddonConstants.MOD_ID, name= AddonConstants.MOD_NAME, dependencies = AddonConstants.MOD_DEPENDENCIES)
public final class DynamicTreesErebus {

	@SidedProxy(clientSide = AddonConstants.PACKAGE_GROUP + ".proxy.ClientProxy", serverSide = AddonConstants.PACKAGE_GROUP + ".proxy.CommonProxy")
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
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
}
