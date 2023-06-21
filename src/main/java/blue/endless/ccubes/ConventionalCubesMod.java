package blue.endless.ccubes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blue.endless.ccubes.asset.SyntheticAssetPack;
import blue.endless.ccubes.block.CCubesBlocks;
import blue.endless.ccubes.block.GroupedBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePackProfile.InsertionPosition;
import net.minecraft.resource.pack.metadata.PackResourceMetadata;
import net.minecraft.resource.pack.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConventionalCubesMod implements ModInitializer {
	public static final String MODID = "conventional_cubes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	//public static final SyntheticAssetPack syntheticDataPack = new SyntheticAssetPack("conventional_cubes_data");
	public static ItemGroup ITEMGROUP = FabricItemGroup.builder()
			.name(Text.literal("Conventional Cubes"))
			.icon(()->new ItemStack(CCubesBlocks.SMOOTH_DOLOMITE))
			.entries((params, collector)-> {
				for(GroupedBlock b : CCubesBlocks.allBlocks) {
					collector.addItem(b);
				}
			})
			.build();
	
	@Override
	public void onInitialize(ModContainer mod) {
		Registry.register(Registries.ITEM_GROUP, new Identifier(MODID, "general"), ITEMGROUP);
		CCubesBlocks.init();
		/*
		ResourceLoader.get(ResourceType.SERVER_DATA).registerResourcePackProfileProvider((adder, factory)->{
			PackResourceMetadata meta;
			try {
				meta = syntheticDataPack.parseMetadata(PackResourceMetadata.READER);
				adder.accept(factory.create(MODID+"_data", Text.literal("Conventional Cubes Synthetic Data"), true, ()->syntheticDataPack, meta, InsertionPosition.BOTTOM, ResourcePackSource.PACK_SOURCE_BUILTIN));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});*/
	}

}
