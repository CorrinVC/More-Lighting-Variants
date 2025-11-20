package com.github.corrinvc.datagen;

import java.util.List;
import java.util.Set;

import com.github.corrinvc.Util;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Util.MODID)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createProvider((output, lookupProvider) -> new LootTableProvider(output, Set.of(),
				List.of(new SubProviderEntry(BlockLootTableGenerator::new, LootContextParamSets.BLOCK)),
				lookupProvider));
		
		event.createProvider(BlockTagProvider::new);
		
		event.createProvider(ModelGenerator::new);
		
		event.createProvider(RecipesProvider.Runner::new);
	}

}
