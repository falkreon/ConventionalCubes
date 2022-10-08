package blue.endless.glow.model;

public record Vector3d(double x, double y, double z) {
	public Vector3d translate(double x, double y, double z) {
		return new Vector3d(this.x+x, this.y+y, this.z+z);
	}
}
