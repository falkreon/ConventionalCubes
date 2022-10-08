package blue.endless.glow.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Model implements Iterable<Mesh> {
	protected List<Mesh> meshes = new ArrayList<>();
	
	public void transform(Matrix3d matrix) {
		for(Mesh mesh : meshes) mesh.transform(matrix);
	}
	
	public void transform(Matrix4d matrix) {
		for(Mesh mesh : meshes) mesh.transform(matrix);
	}
	
	public List<Mesh> getMeshes() {
		return meshes;
	}
	
	@Override
	public Iterator<Mesh> iterator() {
		return meshes.iterator();
	}
}
