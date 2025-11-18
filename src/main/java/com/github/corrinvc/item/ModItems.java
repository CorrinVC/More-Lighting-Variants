package com.github.corrinvc.item;

import com.github.corrinvc.Util;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
	
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Util.MODID);
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

}
