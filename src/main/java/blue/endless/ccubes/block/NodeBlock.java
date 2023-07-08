package blue.endless.ccubes.block;

import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class NodeBlock extends AbstractGroupedVariant {

	public NodeBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String variantName) {
		super(soundGroup, color, group, variantName);
	}
	
	public NodeBlock(Settings settings, String group, String variantName) {
		super(settings, group, variantName);
	}
	
	@Override
	public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}
}
