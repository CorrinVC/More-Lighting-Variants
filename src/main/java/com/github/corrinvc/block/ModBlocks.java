package com.github.corrinvc.block;

import java.util.function.Function;

import com.github.corrinvc.Util;
import com.github.corrinvc.item.ModItems;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
	
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Util.MODID);
	
	public static final DeferredBlock<Block> COPPER_JACK_O_LANTERN = registerBlock(
		"copper_jack_o_lantern",
		properties -> new CustomPumpkinBlock(properties),
		BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN)
	);
	
	public static final DeferredBlock<Block> SOUL_JACK_O_LANTERN = registerBlock(
		"soul_jack_o_lantern",
		properties -> new CustomPumpkinBlock(properties),
		BlockBehaviour.Properties.ofFullCopy(Blocks.JACK_O_LANTERN).lightLevel(blockstate -> 10)
	);
	
//	public static final DeferredBlock<Block> COPPER_CAMPFIRE = registerBlock(
//		"copper_campfire",
//		properties -> new CustomCampfireBlock(false, 1, properties),
//		BlockBehaviour.Properties.ofFullCopy(Blocks.CAMPFIRE)
//	);			
			
	private static <B extends Block> DeferredBlock<B> registerBlock(
			String name, Function<BlockBehaviour.Properties, ? extends B> blockFactory, BlockBehaviour.Properties properties) {
		DeferredBlock<B> block = BLOCKS.registerBlock(name, blockFactory, () -> properties);
		registerBlockItem(name, block);
		return block;
	}
	
	private static <B extends Block> void registerBlockItem(String name, DeferredBlock<B> block) {
		ModItems.ITEMS.registerSimpleBlockItem(name, block);
	}
	
	public static void register(IEventBus eventBus) {
		//Blocks.
		BLOCKS.register(eventBus);
	}

}
