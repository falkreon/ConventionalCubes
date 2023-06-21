package blue.endless.ccubes.datagen;

import java.util.Map;
import java.util.function.Function;

public class BuiltinTemplates {
	public static final String CUBE_ALL_BLOCKSTATE =
		"""
		{
			"variants": {
				"": {
					"model": "conventional_cubes:block/{{name}}"
				}
			}
		}
		""";
	
	public static final String AXIS_BLOCKSTATE =
		"""
		{
			"variants": {
				"axis=y":	{ "model": "conventional_cubes:block/{{name}}" },
				"axis=z":	{ "model": "conventional_cubes:block/{{name}}", "x": 90 },
				"axis=x":	{ "model": "conventional_cubes:block/{{name}}", "x": 90, "y": 90 }
			}
		}
		""";
	
	public static final String HORIZONTAL_BLOCKSTATE =
		"""
			{
			"variants": {
				"facing=north": { "model": "conventional_cubes:block/{{appliedName}}", "y":   0 },
				"facing=east":  { "model": "conventional_cubes:block/{{appliedName}}", "y":  90 },
				"facing=south": { "model": "conventional_cubes:block/{{appliedName}}", "y": 180 },
				"facing=west":  { "model": "conventional_cubes:block/{{appliedName}}", "y": 270 }
			}
		}
		""";
	
	public static final String CUBE_ALL_BLOCKMODEL =
		"""
		{
			"parent": "minecraft:block/cube_all",
			"textures": {
				"all": "conventional_cubes:block/{{group}}/{{variety}}"
			}
		}
		""";
	
	public static final String SIDE_END_MODEL =
			"""
			{
				"parent": "block/cube_column",
				"textures": {
					"side": "conventional_cubes:block/{{group}}/{{variety}}",
					"end" : "conventional_cubes:block/{{group}}/{{variety}}_end",
					"particle": "conventional_cubes:block/{{group}}/{{variety}}"
				}
			}
			""";
	
	public static final String SLOPE_MODEL =
			"""
			{
				"parent": "conventional_cubes:block/slope.gltf",
				"loader": "suspicious_shapes:gltf",
				"textures": {
					"side": "conventional_cubes:block/{{group}}/{{variety}}",
					"top":  "conventional_cubes:block/{{group}}/{{variety}}",
					"particle": "conventional_cubes:block/{{group}}/{{variety}}"
				}
			}
			""";
	
	public static final String CUBE_ALL_ITEM =
		"""
		{
			"parent": "conventional_cubes:block/{{appliedName}}"
		}
		""";
	

	
	public static final Map<String, String> BLOCKSTATE_TEMPLATES = Map.of(
			"cube", CUBE_ALL_BLOCKSTATE,
			"column", AXIS_BLOCKSTATE,
			"side_end", CUBE_ALL_BLOCKSTATE,
			"slope", HORIZONTAL_BLOCKSTATE
			);
	
	public static final Map<String, String> MODEL_TEMPLATES = Map.of(
			"cube", CUBE_ALL_BLOCKMODEL,
			"column", SIDE_END_MODEL,
			"side_end", SIDE_END_MODEL,
			"slope", SLOPE_MODEL
			);
	
	public static final Map<String, Function<String, String>> NAMERS = Map.of(
			"cube", Function.identity(),
			"column", Function.identity(),
			"side_end", Function.identity(),
			"slope", (it)->it+"_slope"
			);
}
