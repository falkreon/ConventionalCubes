package blue.endless.ccubes.client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import blue.endless.ccubes.ConventionalCubesMod;
import blue.endless.ccubes.asset.JarData;
import blue.endless.ccubes.asset.SyntheticAssetPack;
import blue.endless.ccubes.block.CCubesBlocks;
import blue.endless.ccubes.block.AbstractGroupedVariant;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
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
		/*
		try {
			ByteSource iconSource = ByteSource.wrap(JarData.getData("assets/conventional_cubes/icon.png"));
			syntheticResPack.provideRootFile("pack.png", iconSource);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		GroupedBlock[] syntheticBlocks = CCubesBlocks.syntheticBlocks.toArray(new GroupedBlock[CCubesBlocks.syntheticBlocks.size()]);
		
		for (GroupedBlock block : syntheticBlocks) {
			try {
				//TODO: Query block's propertymanager for blockstate type, and block for model type
				String blockStateTemplateName = block.getBlockStateTemplate();
				String blockStateTemplateExtension = extension(blockStateTemplateName);
				String blockState = JarData.applyTemplate(
						JarData.getString("assets/conventional_cubes/templates/blockstate/"+blockStateTemplateName),
						block.getBlockStateMap());
				syntheticResPack.provideResource(
						new Identifier(ConventionalCubesMod.MODID, "blockstates/"+block.getId()+blockStateTemplateExtension),
						byteSource(blockState));
				
				String modelTemplateName = block.getBlockModelTemplate();
				String modelTemplateExtension = extension(modelTemplateName);
				String model = JarData.applyTemplate(
						JarData.getString("assets/conventional_cubes/templates/model/block/"+modelTemplateName),
						block.getBlockModelMap());
				syntheticResPack.provideResource(
						new Identifier(ConventionalCubesMod.MODID, "models/block/"+block.getId()+modelTemplateExtension),
						byteSource(model));
				
				String itemModelTemplateName = block.getItemModelTemplate();
				String itemModelTemplateExtension = extension(itemModelTemplateName);
				String itemModel = JarData.applyTemplate(
						JarData.getString("assets/conventional_cubes/templates/model/item/"+itemModelTemplateName),
						block.getItemModelMap());
				syntheticResPack.provideResource(
						new Identifier(ConventionalCubesMod.MODID, "models/item/"+block.getId()+itemModelTemplateExtension),
						byteSource(itemModel));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//Find blocks in same group
			ArrayList<GroupedBlock> sameGroup = new ArrayList<>();
			for(int i=0; i<syntheticBlocks.length; i++) {
				GroupedBlock cur = syntheticBlocks[i];
				if (!Objects.equals(block, cur) && Objects.equals(cur.getGroup(), block.getGroup())) {
					sameGroup.add(cur);
				}
			}
			
			
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
		
		
		ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm->new GLTFModelProvider(rm));
		
		ModelLoadingRegistry.INSTANCE.registerVariantProvider(rm->variantProvider.attachResourceManager(rm));
		*/
	}
	
	private static ByteSource byteSource(String s) {
		return ByteSource.wrap(s.getBytes(StandardCharsets.UTF_8));
	}
	
	static String extension(String s) {
		int loc = s.indexOf('.');
		if (loc==-1) return "";
		return s.substring(loc);
	}
}
