package com.github.corrinvc.block.entity;

import java.util.function.Supplier;

import com.github.corrinvc.Util;
import com.github.corrinvc.block.ModBlocks;
import com.github.corrinvc.client.renderer.blockentity.CustomCampfireRenderer;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber(modid = Util.MODID)
public class ModBlockEntities {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = 
			DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Util.MODID);
	
	public static final Supplier<BlockEntityType<CustomCampfireBlockEntity>> MODDED_CAMPFIRE_TYPE = BLOCK_ENTITY_TYPES.register(
		"custom_campfire", 
		() -> new BlockEntityType<>(
			CustomCampfireBlockEntity::new,
			ModBlocks.COPPER_CAMPFIRE.get()
		)
	);
	
	public static void register(IEventBus eventBus) {
		BLOCK_ENTITY_TYPES.register(eventBus);
	}
	
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(
				MODDED_CAMPFIRE_TYPE.get(), CustomCampfireRenderer::new);
	}
	
//	private  <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.BlockEntitySupplier<? extends T> factory, Block... validBlocks) {
//		// blockEntity = BLOCK_ENTITY_TYPES.register(name, () -> new BlockEntityType<>(factory, Set.of(validBlocks))).get();
//	}

}
