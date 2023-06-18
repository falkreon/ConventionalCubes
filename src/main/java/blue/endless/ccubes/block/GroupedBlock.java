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
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;

public class GroupedBlock extends Block implements SyntheticDataBlock {
	public static String RECIPE_STONECUTTER = "stonecutter";
	public static String RECIPE_SAWMILL = "sawmill";
	
	protected final String group;
	protected final String id;
	protected String recipeKey = RECIPE_STONECUTTER;
	
	public GroupedBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id) {
		super(
				QuiltBlockSettings
				.copyOf(Blocks.STONE)
				.mapColor(color)
				.strength(1.0f, 15.0f)
				.sounds(soundGroup)
				);
		this.group = group;
		this.id = id;
	}
	
	public GroupedBlock(Settings settings, String group, String id) {
		super(settings);
		this.group = group;
		this.id = id;
	}
	
	@ClientOnly
	@Override
	public void appendTooltip(ItemStack var1, BlockView var2, List<Text> var3, TooltipContext var4) {

		String key = "conventional_cubes.blockgroup."+group+".tip";
		
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
	
	public String getGroup() {
		return group;
	}
	
	public String getId() {
		return id;
	}
	
	public GroupedBlock setRecipeKey(String key) {
		this.recipeKey = key;
		return this;
	}
	
	public String getRecipeKey() {
		return recipeKey;
	}
}

