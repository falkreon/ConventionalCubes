package blue.endless.ccubes.block;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public interface SyntheticDataBlock {
	default String getBlockStateTemplate() {
		return "simple.json";
	}
	
	default Map<String, String> getBlockStateMap() {
		return ImmutableMap.of("model", "conventional_cubes:block/"+getId());
	}
	
	default String getBlockModelTemplate() {
		return "cube_all.json";
	}
	
	default Map<String, String> getBlockModelMap() {
		return ImmutableMap.of("all", "conventional_cubes:block/"+getGroup()+"/"+getId());
		
	}
	
	default String getItemModelTemplate() {
		return "block.json";
	}
	
	default Map<String, String> getItemModelMap() {
		return ImmutableMap.of("model", "conventional_cubes:block/"+getId());
	}
	
	String getId();
	String getGroup();
}
