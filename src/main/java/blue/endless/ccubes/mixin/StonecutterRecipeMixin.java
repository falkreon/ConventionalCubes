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
import blue.endless.ccubes.block.GroupedBlock;
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
			if (item.getBlock() instanceof GroupedBlock grouped) {
				if (!GroupedBlock.RECIPE_STONECUTTER.equals(grouped.getRecipeKey())) return;
					
				//This is a GroupedBlock with the stonecutter key. Add all other bloccks
				//from the same group.
				
				Collection<GroupedBlock> blocksInGroup = CCubesBlocks.byGroup.get(grouped.getGroup());
				for(GroupedBlock other : blocksInGroup) {
					if (Objects.equal(other, grouped)) continue;
					Identifier recipeId = new Identifier(ConventionalCubesMod.MODID, "stonecutter_"+grouped.getId()+"_to_"+other.getId());
					StonecuttingRecipe recipe = new StonecuttingRecipe(recipeId, ConventionalCubesMod.MODID+"_"+grouped.getGroup(), Ingredient.ofItems(grouped), new ItemStack(other));
					availableRecipes.add(recipe);
				}
			}
		}
	}
}
