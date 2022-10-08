package blue.endless.ccubes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.item.group.api.QuiltItemGroup;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blue.endless.ccubes.asset.SyntheticAssetPack;
import blue.endless.ccubes.block.CCubesBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.pack.ResourcePackProfile.InsertionPosition;
import net.minecraft.resource.pack.metadata.PackResourceMetadata;
import net.minecraft.resource.pack.ResourcePackSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConventionalCubesMod implements ModInitializer {
	public static final String MODID = "conventional_cubes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static final SyntheticAssetPack syntheticDataPack = new SyntheticAssetPack("conventional_cubes_data");
	
	public static ItemGroup ITEMGROUP = QuiltItemGroup.builder(new Identifier(MODID, "general"))
			.icon(()->new ItemStack(CCubesBlocks.SMOOTH_DOLOMITE))
			.build();
	
	@Override
	public void onInitialize(ModContainer mod) {
		
		CCubesBlocks.init();
		
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
