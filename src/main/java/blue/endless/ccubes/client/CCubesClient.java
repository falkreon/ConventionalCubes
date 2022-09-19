package blue.endless.ccubes.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import com.google.common.io.ByteSource;
import com.google.gson.JsonObject;

import blue.endless.ccubes.ConventionalCubesMod;
import blue.endless.ccubes.asset.JarData;
import blue.endless.ccubes.asset.SyntheticAssetPack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePackSource;
import net.minecraft.resource.pack.ResourcePackProfile.InsertionPosition;
import net.minecraft.resource.pack.metadata.PackResourceMetadata;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CCubesClient implements ClientModInitializer {
	
	public static final SyntheticAssetPack syntheticResPack = new SyntheticAssetPack("conventional_cubes_resources");
	
	@Override
	public void onInitializeClient(ModContainer mod) {
		
		try {
			ByteSource iconSource = ByteSource.wrap(JarData.getData("assets/conventional_cubes/icon.png"));
			syntheticResPack.provideRootFile("pack.png", iconSource);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(Map.Entry<Identifier, String> entry : ConventionalCubesMod.clientAssets.entrySet()) {
			syntheticResPack.provideResource(entry.getKey(), ByteSource.wrap(entry.getValue().getBytes(StandardCharsets.UTF_8)));
		}
		
		//Synthetic lang file: Felt cute, might delete later.
		JsonObject langObject = new JsonObject();
		langObject.addProperty("language.name", "English");
		langObject.addProperty("language.region", "United States");
		langObject.addProperty("language.code", "en_us");
		
		langObject.addProperty("block.conventional_cubes.smooth_dolomite", "Smooth Dolomite");
		langObject.addProperty("conventional_cubes.blockgroup.dolomite.tip", "Warm-toned stone for clean, strong structures.");
		syntheticResPack.provideResource(new Identifier(ConventionalCubesMod.MODID, "lang/en_us.json"), ByteSource.wrap(langObject.toString().getBytes(StandardCharsets.UTF_8)));
		
		
		ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerResourcePackProfileProvider((adder, factory)->{
			PackResourceMetadata meta;
			try {
				meta = syntheticResPack.parseMetadata(PackResourceMetadata.READER);
				adder.accept(factory.create(ConventionalCubesMod.MODID, Text.literal("Conventional Cubes Synthetic Resources"), true, ()->syntheticResPack, meta, InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
