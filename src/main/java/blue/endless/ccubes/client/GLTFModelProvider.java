package blue.endless.ccubes.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import blue.endless.glow.model.Model;
import blue.endless.glow.model.gltf.GLTFLoader;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class GLTFModelProvider implements ModelResourceProvider {
	private final ResourceManager resourceManager;
	
	public GLTFModelProvider(ResourceManager rm) {
		resourceManager = rm;
	}

	@Override
	public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) throws ModelProviderException {
		if (!resourceId.getPath().endsWith(".json.gltf")) return null;
		
		System.out.println("### requested: "+resourceId+" ###");
		
		Optional<Resource> maybeResource = resourceManager.getResource(new Identifier(resourceId.getNamespace(), "models/"+resourceId.getPath()));
		if (maybeResource.isEmpty()) {
			System.out.println("Couldn't find "+resourceId);
			return null;
		}
		
		try (InputStream in = maybeResource.get().open()) {
			
			String resData = new String(in.readAllBytes(), StandardCharsets.UTF_8);
			System.out.println("Providing resource");
			return new GlowUnbakedModel(GLTFLoader.loadString(resData));
			
		} catch (IOException e) {
			System.out.println("FFFFFFFFFFFF - "+e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	public static @Nullable Model loadModel(Identifier resourceId, ResourceManager resourceManager) {
		Optional<Resource> maybeResource = resourceManager.getResource(new Identifier(resourceId.getNamespace(), "models/"+resourceId.getPath()));
		if (maybeResource.isEmpty()) {
			return null;
		}
		
		try (InputStream in = maybeResource.get().open()) {
			String resData = new String(in.readAllBytes(), StandardCharsets.UTF_8);
			return GLTFLoader.loadString(resData);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
