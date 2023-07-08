package blue.endless.ccubes.datagen;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.HashMap;
import java.util.stream.Collectors;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;

public class App {
	public static void main(String... args) {
		// Vibe check
		Path workingDir = Path.of("");
		if (!workingDir.toAbsolutePath().toString().endsWith("/datagen")) {
			if (workingDir.toAbsolutePath().toString().endsWith("/datagen/run/")) {
				workingDir = workingDir.toAbsolutePath().getParent();
			} else {
				System.err.println("Bad output directory. Please run this app in the datagen path.");
				return;
			}
		}

		//Continue the vibe check
		Path cCubesDir = workingDir.toAbsolutePath().getParent();
		if (!cCubesDir.toAbsolutePath().toString().endsWith("/ConventionalCubes")) {
			System.err.println("Datagen appears to have been separated from Conventional Cubes.");
			return;
		}


		Path assetsDir = cCubesDir.resolve("src/main/resources/assets/conventional_cubes/");
		Path blockstatesDir = assetsDir.resolve("blockstates");
		Path modelsDir = assetsDir.resolve("models");

		if (Files.exists(blockstatesDir, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createDirectories(blockstatesDir);
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}
		}
		
		Jankson jankson = Jankson.builder().build();
		Path dataFolder = workingDir.resolve("data");
		try {
			List<Path> paths = Files.list(dataFolder).collect(Collectors.toList());
			
			int blocksGenerated = 0;
			
			for(Path file : paths) {
				String fileString = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
				try {
					DatagenSpec datagenSpec = jankson.fromJson(fileString, DatagenSpec.class);
					System.out.println("Generating "+datagenSpec.groupName);
					
					//For each template
					if (datagenSpec.blocks == null) {
						System.out.println("Required: 'blocks' key. Skipping because there are no variants to generate.");
						continue;
					}
					for(Map.Entry<String, String[]> templateEntry : datagenSpec.blocks.entrySet()) {
						String templateName = templateEntry.getKey();
						String blockstateTemplate = BuiltinTemplates.BLOCKSTATE_TEMPLATES.getOrDefault(templateName, BuiltinTemplates.CUBE_ALL_BLOCKSTATE);
						String blockModelTemplate = BuiltinTemplates.MODEL_TEMPLATES.getOrDefault(templateName, BuiltinTemplates.CUBE_ALL_BLOCKMODEL);
						Function<String, String> namer = BuiltinTemplates.NAMERS.getOrDefault(templateName, Function.identity());
						
						for(String sub : templateEntry.getValue()) {
							String name = datagenSpec.groupName+"_"+sub;
							
							HashMap<String, String> env = new HashMap<>();
							env.put("name", name);
							env.put("appliedName", namer.apply(name));
							env.put("group", datagenSpec.groupName);
							env.put("variety", sub);
							
							applyTemplate(blockstateTemplate, env, blockstatesDir.resolve(namer.apply(name)+".json"));
							applyTemplate(blockModelTemplate, env, modelsDir.resolve("block").resolve(namer.apply(name)+".json"));
							applyTemplate(BuiltinTemplates.CUBE_ALL_ITEM,       env, modelsDir.resolve("item").resolve(namer.apply(name)+".json"));
							/*
							String blockstate = BuiltinTemplates.CUBE_ALL_BLOCKSTATE.replaceAll("\\{\\{name\\}\\}", name);
							Path p = blockstatesDir.resolve(name+".json");
							if (Files.exists(p)) Files.delete(p);
							Files.createFile(p);
							Files.writeString(p, blockstate, StandardOpenOption.WRITE);*/
							
							System.out.println("    "+namer.apply(name));
							blocksGenerated++;
						}
					}
				} catch (SyntaxError e) {
					e.printStackTrace();
				}
			}


			System.out.println("Complete. Data for "+blocksGenerated+" blocks generated.");



		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void applyTemplate(String source, Map<String, String> properties, Path destination) throws IOException {
		String result = source;
		for(Map.Entry<String, String> entry : properties.entrySet()) {
			result = result.replaceAll("\\{\\{"+entry.getKey()+"\\}\\}", entry.getValue());
		}
		
		if (Files.exists(destination)) Files.delete(destination);
		//Files.createFile(destination);
		Files.writeString(destination, result, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
	}
}
