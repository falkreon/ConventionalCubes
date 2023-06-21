package blue.endless.ccubes.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import blue.endless.ccubes.ConventionalCubesMod;
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

public class SlopeBlock extends GroupedBlock {
	public static VoxelShape[] SHAPES = new VoxelShape[4];
	
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
		
		//VoxelShape stairE = VoxelHelper.cw(stairN);
		//VoxelShape stairS = VoxelHelper.cw(stairE);
		//VoxelShape stairW = VoxelHelper.cw(stairS);
		
		SHAPES[0] = stairN;
		SHAPES[1] = stairS;
		SHAPES[2] = stairW;
		SHAPES[3] = stairE;
	}
	
	public static DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	protected String baseTexture;
	
	public SlopeBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id, String baseTexture) {
		super(soundGroup, color, group, id);
		this.baseTexture = baseTexture;
	}
	
	public SlopeBlock(Block.Settings settings, String group, String id, String baseTexture) {
		super(settings, group, id);
		this.baseTexture = baseTexture;
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
	
	@Override
	public String getBlockStateTemplate() {
		return "horizontal_facing.json";
	}
	
	@Override
	public Map<String, String> getBlockStateMap() {
		return ImmutableMap.of("model", "conventional_cubes:block/"+getId()+".json.gltf");
	}
	
	@Override
	public String getBlockModelTemplate() {
		return "slope.json.gltf";
	}
	
	@Override
	public Map<String, String> getBlockModelMap() {
		return ImmutableMap.of(
				"side",   ConventionalCubesMod.MODID+":block/"+group+"/"+baseTexture,
				"top",    ConventionalCubesMod.MODID+":block/"+group+"/"+baseTexture);
	}
	
	@Override
	public String getItemModelTemplate() {
		return "slope.json.gltf";
	}
	
	@Override
	public Map<String, String> getItemModelMap() {
		return ImmutableMap.of(
				"side",   ConventionalCubesMod.MODID+":block/"+group+"/"+baseTexture,
				"top",    ConventionalCubesMod.MODID+":block/"+group+"/"+baseTexture);
	}
}
