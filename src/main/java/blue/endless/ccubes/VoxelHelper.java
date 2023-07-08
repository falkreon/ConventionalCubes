package blue.endless.ccubes;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Vec3d;

public class VoxelHelper {
	public static VoxelShape rotateHorizontal(VoxelShape v, int degrees) {
		List<VoxelShape> boxes = new ArrayList<>();
		
		v.forEachBox((x1,y1,z1,x2,y2,z2) -> {
			VoxelShape box = rotate(x1, y1, z1, x2, y2, z2, degrees);
			boxes.add(box);
		});
		
		if (boxes.size() == 0) {
			return VoxelShapes.fullCube();
		}
		
		VoxelShape result = VoxelShapes.empty();
		
		if (!boxes.isEmpty()) {
			for(VoxelShape box : boxes) {
				result = VoxelShapes.combine(result, box, BooleanBiFunction.OR);
			}
			
			result = result.simplify();
		}
		
		return result;
	}
	
	public static VoxelShape rotate(VoxelShape v, int x, int y, int z) {
		List<VoxelShape> boxes = new ArrayList<>();
		
		v.forEachBox((x1,y1,z1,x2,y2,z2) -> {
			VoxelShape box = rotate(x1, y1, z1, x2, y2, z2, x, y, z);
			boxes.add(box);
		});
		
		if (boxes.size() == 0) {
			return VoxelShapes.fullCube();
		}
		
		VoxelShape result = VoxelShapes.empty();
		
		if (!boxes.isEmpty()) {
			for(VoxelShape box : boxes) {
				result = VoxelShapes.combine(result, box, BooleanBiFunction.OR);
			}
			
			result = result.simplify();
		}
		
		return result;
	}
	
	public static final float TAU = (float) Math.PI*2;

	
	private static VoxelShape rotate(double x1, double y1, double z1, double x2, double y2, double z2, int degrees) {
		Vec3d a = new Vec3d(x1, y1, z1);
		a = a.subtract(0.5, 0.5, 0.5);
		Vec3d b = new Vec3d(x2, y2, z2);
		b = b.subtract(0.5, 0.5, 0.5);
		
		a = a.rotateY((float) (-TAU / 4) * (degrees / 90));
		b = b.rotateY((float) (-TAU / 4) * (degrees / 90));
		
		a = a.add(0.5, 0.5, 0.5);
		b = b.add(0.5, 0.5, 0.5);
		
		return VoxelShapes.cuboid(
			Math.min(a.x, b.x),
			Math.min(a.y, b.y),
			Math.min(a.z, b.z),
			
			Math.max(a.x, b.x),
			Math.max(a.y, b.y),
			Math.max(a.z, b.z)
			);
	}
	
	private static VoxelShape rotate(double x1, double y1, double z1, double x2, double y2, double z2, int x, int y, int z) {
		Vec3d a = new Vec3d(x1, y1, z1);
		a = a.subtract(0.5, 0.5, 0.5);
		Vec3d b = new Vec3d(x2, y2, z2);
		b = b.subtract(0.5, 0.5, 0.5);
		
		// Snap rotations to the nearest 90 degrees and convert them into radians
		float xRadians = (-TAU / 4) * (x / 90);
		float yRadians = (-TAU / 4) * (y / 90);
		float zRadians = (-TAU / 4) * (z / 90);
		
		a = a.rotateX(xRadians).rotateY(yRadians).rotateZ(zRadians);
		b = b.rotateX(xRadians).rotateY(yRadians).rotateZ(zRadians);
		
		a = a.add(0.5, 0.5, 0.5);
		b = b.add(0.5, 0.5, 0.5);
		
		return VoxelShapes.cuboid(
			Math.min(a.x, b.x),
			Math.min(a.y, b.y),
			Math.min(a.z, b.z),
			
			Math.max(a.x, b.x),
			Math.max(a.y, b.y),
			Math.max(a.z, b.z)
			);
	}
}
