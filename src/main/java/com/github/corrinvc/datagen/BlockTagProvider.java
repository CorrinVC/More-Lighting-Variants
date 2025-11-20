package com.github.corrinvc.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.corrinvc.Util;
import com.github.corrinvc.block.ModBlocks;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

public class BlockTagProvider extends BlockTagsProvider {

	public BlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider) {
		super(output, lookupProvider, Util.MODID);
	}

	@Override
	protected void addTags(Provider provider) {
		tag(BlockTags.MINEABLE_WITH_AXE)
			.add(/*ModBlocks.COPPER_CAMPFIRE.get(), */
				ModBlocks.COPPER_JACK_O_LANTERN.get(), 
				ModBlocks.SOUL_JACK_O_LANTERN.get());
	}

}
