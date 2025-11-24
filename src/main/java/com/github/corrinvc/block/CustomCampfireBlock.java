package com.github.corrinvc.block;

import javax.annotation.Nullable;

import com.github.corrinvc.block.entity.CustomCampfireBlockEntity;
import com.github.corrinvc.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipePropertySet;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CustomCampfireBlock extends CampfireBlock {

	public CustomCampfireBlock(boolean spawnParticles, int fireDamage, Properties properties) {
		super(spawnParticles, fireDamage, properties);
	}
	
	@Override
	protected InteractionResult useItemOn(
			ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result
	) {
		if(level.getBlockEntity(pos) instanceof CustomCampfireBlockEntity blockEntity) {
			ItemStack item = player.getItemInHand(hand);
			if(level.recipeAccess().propertySet(RecipePropertySet.CAMPFIRE_INPUT).test(item)) {
				if(level instanceof ServerLevel serverLevel && blockEntity.placeFood(serverLevel, player, item)) {
					player.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
					return InteractionResult.SUCCESS;
				}
				return InteractionResult.CONSUME;
			}
		}
		
		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CustomCampfireBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if(level instanceof ServerLevel serverLevel) {
			if(state.getValue(LIT)) {
				RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedCheck = 
					RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
				return createTickerHelper(
					type, ModBlockEntities.MODDED_CAMPFIRE_TYPE.get(), 
					(par1, pos, bState, blockEntity) -> CustomCampfireBlockEntity.cookTick(serverLevel, pos, bState, blockEntity, cachedCheck)
				);
			} else {
				return createTickerHelper(type, ModBlockEntities.MODDED_CAMPFIRE_TYPE.get(), CustomCampfireBlockEntity::cooldownTick);
			}
		} else {
			return state.getValue(LIT) ? createTickerHelper(type, ModBlockEntities.MODDED_CAMPFIRE_TYPE.get(), CustomCampfireBlockEntity::particleTick) : null;
		}
	}

}
