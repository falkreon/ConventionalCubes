package blue.endless.ccubes.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class LatticeBlock extends GroupedBlock {
	public static final BooleanProperty NORTH = Properties.NORTH;
	public static final BooleanProperty SOUTH = Properties.SOUTH;
	public static final BooleanProperty EAST = Properties.EAST;
	public static final BooleanProperty WEST = Properties.WEST;
	public static final BooleanProperty UP = Properties.UP;
	public static final BooleanProperty DOWN = Properties.DOWN;
	
	private static final double PX = 1d/16d;
	public static final VoxelShape SHAPE_BODY = VoxelShapes.cuboid(3*PX, 3*PX, 3*PX, 13*PX, 13*PX, 13*PX);
	public static final VoxelShape SHAPE_LEG_N = VoxelShapes.cuboid(3*PX,3*PX,0,13*PX,13*PX,3*PX);
	public static final VoxelShape SHAPE_LEG_S = VoxelShapes.cuboid(3*PX,3*PX,13*PX,13*PX,13*PX,16*PX);
	public static final VoxelShape SHAPE_LEG_W = VoxelShapes.cuboid(0*PX,3*PX,3*PX,3*PX,13*PX,13*PX);
	public static final VoxelShape SHAPE_LEG_E = VoxelShapes.cuboid(13*PX,3*PX,3*PX,16*PX,13*PX,13*PX);
	public static final VoxelShape SHAPE_LEG_D = VoxelShapes.cuboid(3*PX,0*PX,3*PX,13*PX,3*PX,13*PX);
	public static final VoxelShape SHAPE_LEG_U = VoxelShapes.cuboid(3*PX,13*PX,3*PX,13*PX,16*PX,13*PX);
	
	protected String sideTexture;
	protected String endTexture;
	protected String bodyTexture;
	
	public LatticeBlock(Material material, DyeColor color, String group, String id) {
		super(material, color, group, id);
	}
	
	public LatticeBlock(Block.Settings settings, String group, String id) {
		super(settings, group, id);
	}
	
	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
	}
	
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return getNewState(ctx.getWorld(), ctx.getBlockPos());
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return getNewState(world, pos);
	}
	
	public BlockState getNewState(WorldAccess world, BlockPos pos) {
		return this.getDefaultState()
				.with(NORTH, shouldConnect(world, pos, Direction.NORTH))
				.with(SOUTH, shouldConnect(world, pos, Direction.SOUTH))
				.with(EAST,  shouldConnect(world, pos, Direction.EAST))
				.with(WEST,  shouldConnect(world, pos, Direction.WEST))
				.with(UP,    shouldConnect(world, pos, Direction.UP))
				.with(DOWN,  shouldConnect(world, pos, Direction.DOWN))
				;
	}
	
	public boolean shouldConnect(WorldAccess world, BlockPos pos, Direction d) {
		//For now, by default, connect to anything solid.
		BlockPos neighbor = pos.offset(d);
		BlockState neighborState = world.getBlockState(neighbor);
		
		return neighborState.isSolidBlock(world, neighbor);
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		VoxelShape result = SHAPE_BODY;
		if (state.get(NORTH)) result = VoxelShapes.union(result, SHAPE_LEG_N);
		if (state.get(SOUTH)) result = VoxelShapes.union(result, SHAPE_LEG_S);
		if (state.get(EAST))  result = VoxelShapes.union(result, SHAPE_LEG_E);
		if (state.get(WEST))  result = VoxelShapes.union(result, SHAPE_LEG_W);
		if (state.get(UP))    result = VoxelShapes.union(result, SHAPE_LEG_U);
		if (state.get(DOWN))  result = VoxelShapes.union(result, SHAPE_LEG_D);
		return result;
	}
}
