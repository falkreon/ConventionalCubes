package blue.endless.ccubes.block;

import blue.endless.ccubes.VoxelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
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

public class PillarBlock extends ColumnBlock {
	
	private static final VoxelShape Y_SHAPE = makeOctoPillar();
	private static final VoxelShape Z_SHAPE = VoxelHelper.rotate(Y_SHAPE, 90, 0, 0);
	private static final VoxelShape X_SHAPE = VoxelHelper.rotate(Y_SHAPE, 90, 90, 0);
	
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	public PillarBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id) {
		super(soundGroup, color, group, id+"_pillar");
		setDefaultState(stateManager.getDefaultState().with(WATERLOGGED, false));
	}
	
	public PillarBlock(Block.Settings settings, String group, String id) {
		super(settings, group, id+"_pillar");
		setDefaultState(stateManager.getDefaultState().with(WATERLOGGED, false));
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch(state.get(ColumnBlock.AXIS)) {
		case X->X_SHAPE;
		case Y->Y_SHAPE;
		case Z->Z_SHAPE;
		};
	}
	
	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
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
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx)
				.with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
	}
	
	private static VoxelShape makeOctoPillar() {
		double px = 1/16.0;
		VoxelShape base = //VoxelShapes.cuboid(4*px, 0, 4*px, 1-(4*px), 1, 1-(4*px));
				VoxelShapes.cuboid(4*px, 0, 0, 1-(4*px), 1, 1);
		
		VoxelShape cross2 = VoxelShapes.cuboid(0,0,4*px,1,1,1-(4*px));
		
		VoxelShape result = VoxelShapes.union(base, cross2);
		//TODO: Enrich
		
		return result;
	}
}
