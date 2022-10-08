package blue.endless.ccubes.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;

public class VanillaPlusBlockState {
	Map<String, Variant> variants = new HashMap<>();
	
	
	
	
	@Override
	public String toString() {
		return new GsonBuilder().create().toJson(this);
	}
	
	public static class Variant {
		public String model = "";
		public int x = 0;
		public int y = 0;
		public int z = 0;
		
		@Override
		public String toString() {
			return new GsonBuilder().create().toJson(this);
		}
	}
	
	
}
