package blue.endless.ccubes.client;

import java.util.ArrayList;
import java.util.HashMap;

public class VanillaPlusModel {
	public static final Rotation NO_ROTATION = new Rotation();
	public static final String VANILLA_PLUS_TYPE = "vanilla_plus";
	
	public String type = "unknown";
	public boolean ambientocclusion = true;
	public HashMap<String, String> textures = new HashMap<>();
	public ArrayList<Element> elements = new ArrayList<>();
	
	public static class Element {
		public double[] from = new double[] { 0, 0, 0 };
		public double[] to = new double[] { 16, 16, 16 };
		public Rotation rotation = NO_ROTATION;
		public boolean shade = true;
		
		public HashMap<String, Face> faces = new HashMap<>();
	}
	
	public static class Face {
		public String texture = "";
		public String cullface = "none";
		public double emissivity = 0.0; //0..1
	}
	
	//TODO: Change to allow arbitrary rotation
	public static class Rotation {
		public int[] origin = new int[] { 0, 0, 0 };
		public String axis = "y";
		public double angle = 0;
		public boolean rescale = false;
	}
}
