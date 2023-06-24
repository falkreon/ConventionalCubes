package blue.endless.ccubes.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class VariantBlockItem extends BlockItem implements GroupedVariant {
	private final GroupedVariant variant;
	
	public <T extends Block & GroupedVariant> VariantBlockItem(T block, Settings settings) {
		super(block, settings);
		this.variant = block;
	}

	@Override
	public Text getName() {
		return getBlock().getName();
	}
	
	@Override
	public Text getName(ItemStack stack) {
		return getBlock().getName();
	}

	@Override
	public String getGroupName() {
		return variant.getGroupName();
	}

	@Override
	public String getVariantName() {
		return variant.getVariantName();
	}

	@Override
	public String getIdPath() {
		return variant.getIdPath();
	}
}
