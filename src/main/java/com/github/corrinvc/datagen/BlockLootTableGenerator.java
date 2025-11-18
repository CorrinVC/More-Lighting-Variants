package com.github.corrinvc.datagen;

import java.util.Set;

import com.github.corrinvc.block.ModBlocks;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class BlockLootTableGenerator extends BlockLootSubProvider {
	
	protected BlockLootTableGenerator(Provider registries) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
	}

	@Override
	protected void generate() {
		dropSelf(ModBlocks.COPPER_JACK_O_LANTERN.get());
		dropSelf(ModBlocks.SOUL_JACK_O_LANTERN.get());
	}
	
	@Override
	protected Iterable<Block> getKnownBlocks() {
		return ModBlocks.BLOCKS.getEntries().stream().map(e -> (Block) e.value()).toList();
	}

}
