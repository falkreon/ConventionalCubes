package blue.endless.ccubes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;

import blue.endless.ccubes.asset.JarData;
import blue.endless.ccubes.asset.SyntheticAssetPack;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePackProfile.InsertionPosition;
import net.minecraft.resource.pack.metadata.PackResourceMetadata;
import net.minecraft.resource.pack.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ConventionalCubesMod implements ModInitializer {
	public static final String MODID = "conventional_cubes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static final SyntheticAssetPack syntheticDataPack = new SyntheticAssetPack("conventional_cubes_data");
	
	public static Map<Identifier, String> clientAssets = new HashMap<>();
	
	@Override
	public void onInitialize(ModContainer mod) {
		
		//TODO: Register some cubes
		
		Block smoothDolomite = new GroupedBlock(Material.STONE, DyeColor.BROWN, "dolomite");
		Registry.register(Registry.BLOCK, new Identifier(MODID, "smooth_dolomite"), smoothDolomite);
		
		BlockItem smoothDolomiteItem = new BlockItem(smoothDolomite, new QuiltItemSettings().group(ItemGroup.DECORATIONS));
		Registry.register(Registry.ITEM, new Identifier(MODID, "smooth_dolomite"), smoothDolomiteItem);
		
		try {
			String dolomiteBlockState = JarData.applyTemplate(JarData.getString("assets/conventional_cubes/templates/blockstate/simple.json"), ImmutableMap.of("model", "conventional_cubes:block/smooth_dolomite"));
			clientAssets.put(new Identifier(MODID, "blockstates/smooth_dolomite.json"), dolomiteBlockState);
			
			String dolomiteModel = JarData.applyTemplate(JarData.getString("assets/conventional_cubes/templates/model/block/cube_all.json"), ImmutableMap.of("all", "conventional_cubes:block/smooth_dolomite"));
			clientAssets.put(new Identifier(MODID, "models/block/smooth_dolomite.json"), dolomiteModel);
			
			String dolomiteItemModel = JarData.applyTemplate(JarData.getString("assets/conventional_cubes/templates/model/item/block.json"), ImmutableMap.of("model", "conventional_cubes:block/smooth_dolomite"));
			clientAssets.put(new Identifier(MODID, "models/item/smooth_dolomite.json"), dolomiteItemModel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//TODO: Load up synthetic data
		
		ResourceLoader.get(ResourceType.SERVER_DATA).registerResourcePackProfileProvider((adder, factory)->{
			PackResourceMetadata meta;
			try {
				meta = syntheticDataPack.parseMetadata(PackResourceMetadata.READER);
				adder.accept(factory.create(MODID+"_data", Text.literal("Conventional Cubes Synthetic Data"), true, ()->syntheticDataPack, meta, InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
