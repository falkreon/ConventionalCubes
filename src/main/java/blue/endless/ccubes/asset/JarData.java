package blue.endless.ccubes.asset;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;

import blue.endless.ccubes.ConventionalCubesMod;

public class JarData {
	private static ModContainer container = null;
	
	private static void checkInit() {
		if (container == null) {
			container = QuiltLoader.getModContainer(ConventionalCubesMod.MODID).orElse(null);
		}
		
		if (container != null) return;
		
		// If the above works, this should not; still, make every effort to find ourselves.
		for(ModContainer mc : QuiltLoader.getAllMods()) {
			if (mc.metadata().id().equals(ConventionalCubesMod.MODID)) {
				container = mc;
				return;
			}
		}
			
		throw new IllegalStateException("QuiltLoader cannot find Conventional Cubes! Has the modid been changed in ConventionalCubesMod or in quilt.mod.json?");
	}
	
	public static String getString(String jarPath) throws FileNotFoundException, IOException {
		Path filePath = getMangledRootPath().resolve(jarPath);
		return Files.readString(filePath, StandardCharsets.UTF_8);
	}
	
	public static byte[] getData(String jarPath) throws FileNotFoundException, IOException {
		Path filePath = getMangledRootPath().resolve(jarPath);
		return Files.readAllBytes(filePath);
	}
	
	private static Path getMangledRootPath() {
		checkInit();
		
		if (QuiltLoader.isDevelopmentEnvironment()) {
			if (container.rootPath().toString().endsWith("/bin/main")) {
				//Eclipse starts this way. Walk back to the project directory and back forwards into the dev assets.
				Path devPath = container.rootPath().getParent().getParent();
				Path resourcesPath = devPath.resolve("src").resolve("main").resolve("resources");
				return resourcesPath;
			} else if (container.rootPath().toString().endsWith("/build/resources/main")) {
				//Proceed as normal; this is a gradle run and the assets directory is present relative to this path.
				return container.rootPath();
			} else {
				//Unknown dev environment! Try the standard way and cross our fingers
				return container.rootPath();
			}
		} else {
			//Prod
			return container.rootPath();
		}
	}
	
	public static String applyTemplate(String templateFile, Map<String, String> properties) {
		String result = templateFile;
		
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			result = result.replaceAll(Pattern.quote("{{"+entry.getKey()+"}}"), entry.getValue());
		}
		
		return result;
	}
}
