package com.github.corrinvc.datagen;

import com.github.corrinvc.Util;
import com.github.corrinvc.block.ModBlocks;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;

public class ModelGenerator extends ModelProvider {

	public ModelGenerator(PackOutput output) {
		super(output, Util.MODID);
	}
	
	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		blockModels.createPumpkinVariant(ModBlocks.COPPER_JACK_O_LANTERN.get(), TextureMapping.column(Blocks.PUMPKIN));
		blockModels.createPumpkinVariant(ModBlocks.SOUL_JACK_O_LANTERN.get(), TextureMapping.column(Blocks.PUMPKIN));
	}

}
