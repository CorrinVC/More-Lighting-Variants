package com.github.corrinvc.block;

import java.util.Optional;
import java.util.function.Predicate;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.coppergolem.CopperGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.neoforged.neoforge.common.DataMapHooks;

public class CustomPumpkinBlock extends CarvedPumpkinBlock {

	private BlockPattern snowGolemFull, ironGolemFull, copperGolemFull;
	private static final Predicate<BlockState> MODDED_PUMPKINS = block -> block != null
		&& (block.is(ModBlocks.COPPER_JACK_O_LANTERN.get()) || block.is(ModBlocks.SOUL_JACK_O_LANTERN.get()));
	
	public CustomPumpkinBlock(Properties p_51375_) {
		super(p_51375_);
		//DispenserBlock.registerBehavior(null, null);
	}
	
	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moving) {
		if(!oldState.is(state.getBlock())) {
			this.trySpawnGolem(level, pos);
		}
	}
	
	private void trySpawnGolem(Level level, BlockPos pos) {
		if(trySpawnSnowGolem(level, pos)) return;
		if(trySpawnIronGolem(level, pos)) return;
		if(trySpawnCopperGolem(level, pos)) return;
	}
	
	private boolean trySpawnSnowGolem(Level level, BlockPos pos) {
		boolean successfulSpawn = false;
		BlockPattern.BlockPatternMatch snowGolemMatch = this.createSnowGolemFull().find(level, pos);
		if(snowGolemMatch != null) {
			SnowGolem snowGolem = EntityType.SNOW_GOLEM.create(level, EntitySpawnReason.TRIGGERED);
			if(snowGolem != null) {
				spawnGolemInWorld(level, snowGolemMatch, snowGolem, snowGolemMatch.getBlock(0, 2, 0).getPos());
				successfulSpawn = true;
			}
		}
		return successfulSpawn;
	}
	
	private boolean trySpawnIronGolem(Level level, BlockPos pos) {
		boolean successfulSpawn = false;
		BlockPattern.BlockPatternMatch ironGolemMatch = this.createIronGolemFull().find(level, pos);
		if(ironGolemMatch != null) {
			IronGolem ironGolem = EntityType.IRON_GOLEM.create(level, EntitySpawnReason.TRIGGERED);
			if(ironGolem != null) {
				spawnGolemInWorld(level, ironGolemMatch, ironGolem, ironGolemMatch.getBlock(1, 2, 0).getPos());
				successfulSpawn = true;
			}
		}
		
		return successfulSpawn;
	}
	
	private boolean trySpawnCopperGolem(Level level, BlockPos pos) {
		boolean successfulSpawn = false;
		BlockPattern.BlockPatternMatch copperGolemMatch = this.createCopperGolemFull().find(level, pos);
		if(copperGolemMatch != null) {
			CopperGolem copperGolem = EntityType.COPPER_GOLEM.create(level, EntitySpawnReason.TRIGGERED);
			if(copperGolem != null) {
				spawnGolemInWorld(level, copperGolemMatch, copperGolem, copperGolemMatch.getBlock(0, 0, 0).getPos());
				this.replaceCopperBlockWithChest(level, copperGolemMatch);
				copperGolem.spawn(this.getWeatherStateFromPattern(copperGolemMatch));
			}
		}
		
		return successfulSpawn;
	}
	
	private WeatheringCopper.WeatherState getWeatherStateFromPattern(BlockPattern.BlockPatternMatch match) {
		BlockState blockState = match.getBlock(0, 1, 0).getState();
		return blockState.getBlock() instanceof WeatheringCopper weatheringCopper
			? weatheringCopper.getAge()
			: Optional.ofNullable(DataMapHooks.INVERSE_WAXABLES_DATAMAP.get(blockState.getBlock()))
				.filter(block -> block instanceof WeatheringCopper)
				.map(block -> (WeatheringCopper)block)
				.orElse((WeatheringCopper)Blocks.COPPER_BLOCK)
				.getAge();
	}
	
	private static void spawnGolemInWorld(Level level, BlockPattern.BlockPatternMatch match, Entity golem, BlockPos pos) {
		clearPatternBlocks(level, match);
		golem.snapTo(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0.0f, 0.0f);
		level.addFreshEntity(golem);
		
		for(ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, golem.getBoundingBox().inflate(5.0))) {
			CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, golem);
		}
		
		updatePatternBlocks(level, match);
	}
	
	private BlockPattern createSnowGolemFull() {
		if(this.snowGolemFull == null) {
			this.snowGolemFull = BlockPatternBuilder.start()
				.aisle("^", "#", "#")
				.where('^', BlockInWorld.hasState(MODDED_PUMPKINS))
				.where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK)))
				.build();
		}
		return this.snowGolemFull;
	}
	
	private BlockPattern createIronGolemFull() {
		if(this.ironGolemFull == null) {
			this.ironGolemFull = BlockPatternBuilder.start()
				.aisle("~^~", "###", "~#~")
				.where('^', BlockInWorld.hasState(MODDED_PUMPKINS))
				.where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.IRON_BLOCK)))
				.where('~', block -> block.getState().isAir())
				.build();
		}
		return this.ironGolemFull;
	}
	
	private BlockPattern createCopperGolemFull() {
		if(this.copperGolemFull == null) {
			this.copperGolemFull = BlockPatternBuilder.start()
				.aisle("^", "#")
				.where('^', BlockInWorld.hasState(MODDED_PUMPKINS))
				.where('#', BlockInWorld.hasState(block -> block.is(BlockTags.COPPER)))
				.build();
		}
		return this.copperGolemFull;
	}

}
