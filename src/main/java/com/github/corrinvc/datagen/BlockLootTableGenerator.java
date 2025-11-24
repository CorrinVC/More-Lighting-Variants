package com.github.corrinvc.datagen;

import java.util.Set;

import com.github.corrinvc.block.ModBlocks;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class BlockLootTableGenerator extends BlockLootSubProvider {
	
	protected BlockLootTableGenerator(Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected void generate() {
		//VanillaBlockLoot.class
		
		dropSelf(ModBlocks.COPPER_JACK_O_LANTERN.get());
		dropSelf(ModBlocks.SOUL_JACK_O_LANTERN.get());
		this.add(ModBlocks.COPPER_CAMPFIRE.get(), 
			block -> this.createSilkTouchDispatchTable(block, 
				(LootPoolEntryContainer.Builder<?>)this.applyExplosionCondition(block, 
					LootItem.lootTableItem(Items.COPPER_INGOT).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))));
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(e -> (Block) e.value()).toList();
	}

}
