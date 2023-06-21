package blue.endless.ccubes.mixin;

import java.util.Collection;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.include.com.google.common.base.Objects;

import blue.endless.ccubes.ConventionalCubesMod;
import blue.endless.ccubes.block.CCubesBlocks;
import blue.endless.ccubes.block.GroupedVariant;
import blue.endless.ccubes.block.SyntheticRecipeHaver;
import blue.endless.ccubes.block.AbstractGroupedVariant;
import net.minecraft.block.Block;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.util.Identifier;

@Mixin(StonecutterScreenHandler.class)
public class StonecutterRecipeMixin {
	@Shadow
	private List<StonecuttingRecipe> availableRecipes;
	
	@Inject(at = @At("RETURN"), method="updateInput")
	private void updateInput(Inventory input, ItemStack stack, CallbackInfo ci) {
		if (stack.isEmpty()) return;
		
		if (stack.getItem() instanceof BlockItem item) {
			Block block = item.getBlock();
			if (block instanceof SyntheticRecipeHaver haver) {
				if (!AbstractGroupedVariant.RECIPE_STONECUTTER.equals(haver.getRecipeKey())) return;
				
				if (item.getBlock() instanceof GroupedVariant grouped) {
					//This is a GroupedBlock with the stonecutter key. Add all other bloccks
					//from the same group.
					
					Collection<AbstractGroupedVariant> blocksInGroup = CCubesBlocks.byGroup.get(grouped.getGroupName());
					for(AbstractGroupedVariant other : blocksInGroup) {
						if (Objects.equal(other, grouped)) continue;
						Identifier recipeId = new Identifier(ConventionalCubesMod.MODID, "stonecutter_"+grouped.getIdPath()+"_to_"+other.getIdPath());
						StonecuttingRecipe recipe = new StonecuttingRecipe(recipeId, ConventionalCubesMod.MODID+"_"+grouped.getGroupName(), Ingredient.ofItems(block), new ItemStack(other));
						availableRecipes.add(recipe);
					}
				}
			}
		}
	}
}
