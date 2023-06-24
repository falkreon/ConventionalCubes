package blue.endless.ccubes.block;

import java.util.ArrayList;
import java.util.List;

import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import blue.endless.ccubes.WordWrap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;

public class AbstractGroupedVariant extends Block implements GroupedVariant, SyntheticRecipeHaver {
	protected final String groupName;
	protected final String variantName;
	protected String recipeKey = SyntheticRecipeHaver.RECIPE_STONECUTTER;
	
	public AbstractGroupedVariant(BlockSoundGroup soundGroup, DyeColor color, String group, String variantName) {
		super(
				QuiltBlockSettings
				.copyOf(Blocks.STONE)
				.mapColor(color)
				.strength(1.0f, 15.0f)
				.sounds(soundGroup)
				);
		this.groupName = group;
		this.variantName = variantName;
	}
	
	public AbstractGroupedVariant(Settings settings, String group, String variantName) {
		super(settings);
		this.groupName = group;
		this.variantName = variantName;
	}
	
	@ClientOnly
	@Override
	public void appendTooltip(ItemStack var1, BlockView var2, List<Text> var3, TooltipContext var4) {

		String key = "blockgroup.conventional_cubes."+groupName+".tip";
		
		for(String s : WordWrap.translateAndWrap(key, 128)) {
			var3.add(Text.literal(s).formatted(Formatting.GRAY, Formatting.ITALIC));
		}
		
		super.appendTooltip(var1, var2, var3, var4);
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState var1, LootContextParameterSet.Builder var2) {
		@SuppressWarnings("deprecation")
		List<ItemStack> superStacks = super.getDroppedStacks(var1, var2);
		if (!superStacks.isEmpty()) return superStacks; //If there's a json, use it
		
		ArrayList<ItemStack> result = new ArrayList<>(); //Otherwise just drop itself
		result.add(new ItemStack(this, 1));
		return result;
	}
	
	@Override
	public MutableText getName() {
		return Text.translatable(
				"conventional_cubes.template.block_title",
				Text.translatable("blockgroup.conventional_cubes."+groupName),
				Text.translatable("block.conventional_cubes."+groupName+"_"+variantName)
				);
	}

	@Override
	public String getGroupName() {
		return groupName;
	}

	@Override
	public String getVariantName() {
		
		return null;
	}
	
	@Override
	public String getIdPath() {
		return this.groupName + "_" + this.variantName;
	}

	@Override
	public String getRecipeKey() {
		return recipeKey;
	}
	
	public AbstractGroupedVariant setRecipeKey(String key) {
		this.recipeKey = key;
		return this;
	}
}

