package blue.endless.ccubes.block;

import blue.endless.ccubes.VoxelHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PillarBlock extends ColumnBlock {
	
	private static final VoxelShape Y_SHAPE = makeOctoPillar();
	private static final VoxelShape Z_SHAPE = VoxelHelper.rotate(Y_SHAPE, 90, 0, 0);
	private static final VoxelShape X_SHAPE = VoxelHelper.rotate(Y_SHAPE, 90, 90, 0);

	public PillarBlock(BlockSoundGroup soundGroup, DyeColor color, String group, String id) {
		super(soundGroup, color, group, id+"_pillar");
	}
	
	public PillarBlock(Block.Settings settings, String group, String id) {
		super(settings, group, id+"_pillar");
	}
	
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return switch(state.get(ColumnBlock.AXIS)) {
		case X->X_SHAPE;
		case Y->Y_SHAPE;
		case Z->Z_SHAPE;
		};
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
