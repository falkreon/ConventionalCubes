package blue.endless.ccubes.block;

import blue.endless.ccubes.VoxelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class SlopeBlock extends AbstractGroupedVariant {
	//public static final VoxelShape[] SHAPES = new VoxelShape[8];
	
	private VoxelShape stairN = createStairShape();
	private VoxelShape stairE = VoxelHelper.rotateHorizontal(stairN, 90);
	private VoxelShape stairS = VoxelHelper.rotateHorizontal(stairN, 180);
	private VoxelShape stairW = VoxelHelper.rotateHorizontal(stairN, 270);
	
	private VoxelShape upN = VoxelHelper.rotate(stairN, 180, 180, 0);
	private VoxelShape upE = VoxelHelper.rotate(stairN, 180, 270, 0);
	private VoxelShape upS = VoxelHelper.rotate(stairN, 180,   0, 0);
	private VoxelShape upW = VoxelHelper.rotate(stairN, 180,  90, 0);
	
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	
	public SlopeBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id) {
		super(soundGroup, color, group, id+"_slope");
		setDefaultState(stateManager.getDefaultState().with(WATERLOGGED, false));
	}
	
	public SlopeBlock(Block.Settings settings, String group, String id) {
		super(settings, group, id+"_slope");
		setDefaultState(stateManager.getDefaultState().with(WATERLOGGED, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(FACING, HALF, WATERLOGGED);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockHalf half = switch(ctx.getSide()) {
			case UP -> BlockHalf.BOTTOM; //We hit the top surface of a block
			case DOWN -> BlockHalf.TOP;  //We hit the underside of a block
			default -> {
				//If we hit higher than halfway up the side of the block, be the top half
				double sideHitHeight = ctx.getHitPos().getY() - ctx.getBlockPos().getY();
				if (sideHitHeight > 0.5) {
					yield BlockHalf.TOP;
				} else {
					yield BlockHalf.BOTTOM;
				}
			}
		};
		
		return this.getDefaultState()
				.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(HALF, half)
				.with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : Fluids.EMPTY.getDefaultState();
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		
		return state;
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (state.get(HALF) == BlockHalf.BOTTOM) {
			return switch(state.get(FACING)) {
				case NORTH -> stairN;
				case SOUTH -> stairS;
				case WEST  -> stairW;
				case EAST  -> stairE;
				default -> VoxelShapes.fullCube();
			};
		} else {
			return switch(state.get(FACING)) {
			case NORTH -> upN;
			case SOUTH -> upS;
			case WEST  -> upW;
			case EAST  -> upE;
			default -> VoxelShapes.fullCube();
		};
		}
	}
	
	private static VoxelShape createStairShape() {
		double px = 1/16.0;
		VoxelShape result = VoxelShapes.empty();
		for(int i=0; i<16; i++) {
			double stairWidth = (15-i)*px;
			double stairStart = (i+1)*px;
			VoxelShape cur = VoxelShapes.cuboid(0, i*px, stairStart, 1, (i+1)*px, stairStart+stairWidth);
			result = VoxelShapes.union(result, cur);
		}
		
		return result;
	}
}
