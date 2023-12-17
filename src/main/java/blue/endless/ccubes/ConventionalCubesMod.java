package blue.endless.ccubes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import blue.endless.ccubes.block.CCubesBlocks;
import blue.endless.ccubes.block.AbstractGroupedVariant;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ConventionalCubesMod implements ModInitializer {
	public static final String MODID = "conventional_cubes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
	
	public static ItemGroup ITEMGROUP = FabricItemGroup.builder()
			.displayName(Text.literal("Conventional Cubes"))
			.icon(()->new ItemStack(CCubesBlocks.SMOOTH_DOLOMITE))
			.entries((params, collector)-> {
				for(AbstractGroupedVariant b : CCubesBlocks.allBlocks) {
					collector.add(b);
				}
			})
			.build();
	
	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, new Identifier(MODID, "general"), ITEMGROUP);
		CCubesBlocks.init();
	}

}
