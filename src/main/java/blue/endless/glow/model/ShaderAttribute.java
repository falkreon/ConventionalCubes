package blue.endless.glow.model;

public class ShaderAttribute<T> {
	protected final String name;
	
	public ShaderAttribute(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static final ShaderAttribute<String> DIFFUSE_TEXTURE = new ShaderAttribute<>("diffuse_texture");
	public static final ShaderAttribute<Vector3d> POSITION = new ShaderAttribute<>("position");
	public static final ShaderAttribute<Vector2d> TEXCOORD = new ShaderAttribute<>("uv");
	public static final ShaderAttribute<Vector3d> NORMAL   = new ShaderAttribute<>("normal");
	
	public static final ShaderAttribute<Double> ROUGHNESS = new ShaderAttribute<>("roughness");
	public static final ShaderAttribute<Double> METALNESS = new ShaderAttribute<>("metalness");
}
