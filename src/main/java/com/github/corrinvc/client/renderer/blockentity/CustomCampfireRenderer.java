package com.github.corrinvc.client.renderer.blockentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.github.corrinvc.block.CustomCampfireBlock;
import com.github.corrinvc.block.entity.CustomCampfireBlockEntity;
import com.github.corrinvc.client.renderer.blockentity.state.CustomCampfireRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;

public class CustomCampfireRenderer implements BlockEntityRenderer<CustomCampfireBlockEntity, CustomCampfireRenderState> {
	private final ItemModelResolver itemModelResolver;
	
	public CustomCampfireRenderer(BlockEntityRendererProvider.Context context) {
		this.itemModelResolver = context.itemModelResolver();
	}
	
	@Override
	public CustomCampfireRenderState createRenderState() {
		return new CustomCampfireRenderState();
	}
	
	@Override
	public void extractRenderState(CustomCampfireBlockEntity blockEntity, CustomCampfireRenderState renderState,
			float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay overlay) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPos, overlay);
		renderState.facing = blockEntity.getBlockState().getValue(CustomCampfireBlock.FACING);
		int i = (int)blockEntity.getBlockPos().asLong();
		renderState.items = new ArrayList<>();
		
		for(int j = 0; j < blockEntity.getItems().size(); j++) {
			ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
			this.itemModelResolver.updateForTopItem(
				itemStackRenderState, blockEntity.getItems().get(j), ItemDisplayContext.FIXED, 
				blockEntity.getLevel(), null, i + j
			);
			renderState.items.add(itemStackRenderState);
		}
	}

	@Override
	public void submit(CustomCampfireRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector,
			CameraRenderState cameraRenderState) {
		Direction direction = renderState.facing;
		List<ItemStackRenderState> items = renderState.items;
		
		for(int i = 0; i < items.size(); i++) {
			ItemStackRenderState itemStackRenderState = items.get(i);
			if(!itemStackRenderState.isEmpty()) {
				poseStack.pushPose();
				poseStack.translate(0.5F, 0.44921875F, 0.5F);
				Direction direction1 = Direction.from2DDataValue((i + direction.get2DDataValue()) % 4);
				float f = -direction1.toYRot();
				poseStack.mulPose(Axis.YP.rotationDegrees(f));
				poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
				poseStack.translate(-0.3125F, -0.3125F, 0.0F);
				poseStack.scale(0.375F, 0.375F, 0.375F);
				itemStackRenderState.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
				poseStack.popPose();
			}
		}
		
	}

}
