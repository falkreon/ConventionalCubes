package blue.endless.ccubes.datagen;

import java.util.Map;

import blue.endless.jankson.annotation.SerializedName;

public class DatagenSpec {
	@SerializedName("group_name")
	public String groupName;
	
	public Map<String, String[]> blocks;
}
