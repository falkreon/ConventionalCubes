package blue.endless.ccubes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

public class ColumnBlock extends AbstractGroupedVariant {
	public static final EnumProperty<Direction.Axis> AXIS = Properties.AXIS;
	
	public ColumnBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id) {
		super(soundGroup, color, group, id);
	}
	
	public ColumnBlock(Block.Settings settings, String group, String id) {
		super(settings, group, id);
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(AXIS);
	}
	
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return PillarBlock.changeRotation(state, rotation);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(AXIS, ctx.getSide().getAxis());
	}
}
