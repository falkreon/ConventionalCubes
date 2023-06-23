package blue.endless.ccubes.block;

import blue.endless.ccubes.VoxelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class SlopeBlock extends AbstractGroupedVariant {
	public static final VoxelShape[] SHAPES = new VoxelShape[4];
	
	static {
		final double PX = 1.0/16.0;
		
		VoxelShape stairN = VoxelShapes.empty();
		for(int i=0; i<16; i++) {
			double stairWidth = (15-i)*PX;
			double stairStart = (i+1)*PX;
			VoxelShape cur = VoxelShapes.cuboid(0, i*PX, stairStart, 1, (i+1)*PX, stairStart+stairWidth);
			stairN = VoxelShapes.union(stairN, cur);
		}
		
		VoxelShape stairE = VoxelHelper.rotateHorizontal(stairN, 90);
		VoxelShape stairS = VoxelHelper.rotateHorizontal(stairN, 180);
		VoxelShape stairW = VoxelHelper.rotateHorizontal(stairN, 270);
		
		SHAPES[0] = stairN;
		SHAPES[1] = stairS;
		SHAPES[2] = stairW;
		SHAPES[3] = stairE;
	}
	
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	
	public SlopeBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id) {
		super(soundGroup, color, group, id+"_slope");
	}
	
	public SlopeBlock(Block.Settings settings, String group, String id) {
		super(settings, group, id+"_slope");
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch(state.get(FACING)) {
			case NORTH -> SHAPES[0];
			case SOUTH -> SHAPES[1];
			case WEST  -> SHAPES[2];
			case EAST  -> SHAPES[3];
			default -> VoxelShapes.fullCube();
		};
	}
}
