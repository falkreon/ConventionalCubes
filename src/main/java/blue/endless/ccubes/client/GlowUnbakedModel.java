package blue.endless.ccubes.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;

import blue.endless.glow.model.Mesh;
import blue.endless.glow.model.Model;
import blue.endless.glow.model.ShaderAttribute;
import blue.endless.glow.model.Vector2d;
import blue.endless.glow.model.Vector3d;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.impl.client.indigo.renderer.IndigoRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class GlowUnbakedModel implements UnbakedModel {
	private static final Identifier DEFAULT_BLOCK_MODEL = new Identifier("minecraft:block/block");
	
	protected final Model model;
	private List<SpriteIdentifier> textures = new ArrayList<>();
	private Map<Identifier, Sprite> sprites = new HashMap<>();
	
	
	public GlowUnbakedModel(Model glowModel) {
		this.model = glowModel;
		for(Mesh mesh : model) {
			String texId = mesh.getMaterial().get(ShaderAttribute.DIFFUSE_TEXTURE);
			if (texId!=null) textures.add(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, new Identifier(texId)));
		}
		//textures.add(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, TextureManager.MISSING_IDENTIFIER));
	}
	
	@Override
	public Collection<Identifier> getModelDependencies() {
		//Does not depend on other models
		
		return ImmutableList.of(DEFAULT_BLOCK_MODEL);
	}

	@Override
	public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
		//System.out.println("Unresolved Texture Dependencies: "+unresolvedTextureReferences);
		
		return ImmutableList.copyOf(textures);
	}

	@Override
	public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
		//System.out.println("Baking: "+modelId);
		
		sprites.clear();
		for(SpriteIdentifier id : textures) {
			sprites.put(id.getTextureId(), textureGetter.apply(id));
		}
		
		SpriteIdentifier missingnoId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, TextureManager.MISSING_IDENTIFIER);
		Sprite missingno = textureGetter.apply(missingnoId);
		
		//The following doesn't really make things run on Sodium without Indium, but it makes it stop crashing.
		Renderer renderer = (RendererAccess.INSTANCE.hasRenderer()) ? RendererAccess.INSTANCE.getRenderer() : IndigoRenderer.INSTANCE;
		MeshBuilder meshBuilder = renderer.meshBuilder();
		QuadEmitter emitter = meshBuilder.getEmitter();
		
		
		
		for(Mesh mesh : model) {
			for(Mesh.Face face : mesh.createTriangleList()) {
				Iterator<Mesh.Vertex> verts = face.iterator();
				Mesh.Vertex v1 = verts.next();
				Mesh.Vertex v2 = verts.next();
				Mesh.Vertex v3 = verts.next();
				Mesh.Vertex v4 = (verts.hasNext()) ? verts.next() : v3; //If somehow there are quads mixed in, emit them as-is instead of making a degenerate triangle
				
				emit(v1, emitter, 0);
				emit(v2, emitter, 1);
				emit(v3, emitter, 2);
				emit(v4, emitter, 3);
				
				Sprite sprite = missingno;
				String texId = mesh.getMaterial().get(ShaderAttribute.DIFFUSE_TEXTURE);
				if (texId!=null) {
					Sprite maybe = sprites.get(new Identifier(texId));
					if (maybe!=null) {
						sprite = maybe;
					}
				}
				
				emitter.spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED);
				emitter.emit();
			}
		}
		
		JsonUnbakedModel defaultBlockModel = (JsonUnbakedModel) loader.getOrLoadModel(DEFAULT_BLOCK_MODEL);
		
		return new BakedMeshModel(missingno, defaultBlockModel.getTransformations(), meshBuilder.build());
	}
	
	private static void emit(Mesh.Vertex v, QuadEmitter emitter, int index) {
		Vector3d pos = v.get(ShaderAttribute.POSITION);
		Vector2d tex = v.get(ShaderAttribute.TEXCOORD);
		Vector3d norm = v.get(ShaderAttribute.NORMAL);
		
		emitter.pos(index, (float) pos.x(), (float) pos.y(), (float) pos.z());
		emitter.sprite(index, 0, (float) tex.x(), (float) tex.y());
		emitter.spriteColor(0, -1, -1, -1, -1);
		emitter.normal(index, (float) norm.x(), (float) norm.y(), (float) norm.z());
	}

}
