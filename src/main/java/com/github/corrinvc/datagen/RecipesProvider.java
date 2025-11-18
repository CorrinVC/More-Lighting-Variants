package com.github.corrinvc.datagen;

import java.util.concurrent.CompletableFuture;

import com.github.corrinvc.block.ModBlocks;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class RecipesProvider extends RecipeProvider {

	protected RecipesProvider(Provider registries, RecipeOutput output) {
		super(registries, output);
	}

	@Override
	protected void buildRecipes() {
		
		// COPPER JACK-O-LANTERN
		ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.COPPER_JACK_O_LANTERN)
			.pattern("A").pattern("B")
			.define('A', Blocks.CARVED_PUMPKIN)
			.define('B', Items.COPPER_TORCH)
			.unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN))
			.unlockedBy("has_copper_torch", has(Items.COPPER_TORCH))
			.save(this.output);	
		
		// SOUL JACK-O-LANTERN
		ShapedRecipeBuilder.shaped(registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SOUL_JACK_O_LANTERN)
			.pattern("A").pattern("B")
			.define('A', Blocks.CARVED_PUMPKIN)
			.define('B', Items.SOUL_TORCH)
			.unlockedBy("has_carved_pumpkin", has(Blocks.CARVED_PUMPKIN))
			.unlockedBy("has_soul_torch", has(Items.SOUL_TORCH))
			.save(this.output);	
	}
	
	public static class Runner extends RecipeProvider.Runner {
		public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(output, lookupProvider);
		}
		
		@Override
		protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
			return new RecipesProvider(provider, output);
		}

		@Override
		public String getName() {
			return "";
		}
	}

}
