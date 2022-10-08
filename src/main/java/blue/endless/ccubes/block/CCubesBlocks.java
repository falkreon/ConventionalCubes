package blue.endless.ccubes.block;

import java.util.ArrayList;

import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import blue.endless.ccubes.ConventionalCubesMod;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CCubesBlocks {
	public static ArrayList<GroupedBlock> syntheticBlocks = new ArrayList<>();
	
	public static Multimap<String, GroupedBlock> byGroup = HashMultimap.create();
	
	public static GroupedBlock SMOOTH_DOLOMITE;
	
	public static void init() {
		SMOOTH_DOLOMITE = register(new GroupedBlock(Material.STONE, DyeColor.BROWN, "dolomite", "smooth_dolomite"));
		
		registerGroup("dolomite", Material.STONE, DyeColor.BROWN,
				"dolomite_large_tile",
				"dolomite_tiles",
				"dolomite_small_tiles",
				"dolomite_brick",
				"dolomite_checker",
				"dolomite_small_checker"
				);
		
		registerGroup("oneup", Material.STONE, DyeColor.ORANGE,
				"oneup_orange_brick",
				"oneup_orange_block",
				"oneup_uneven_orange_brick",
				"oneup_depleted_question",
				"oneup_gold_brick"
				);
		
		registerGroup("tourian", Material.METAL, DyeColor.GRAY,
				"tourian_spawner",
				"tourian_bevel",
				"tourian_dented_bevel",
				"tourian_vent",
				"tourian_hollow_block"
				);
		
		registerGroup("aero", Material.METAL, DyeColor.PURPLE,
				"aero_purple",
				"aero_purple_ridged",
				"aero_gold"
				);
		
		register(new SlopeBlock(Material.STONE, DyeColor.BROWN, "dolomite", "smooth_dolomite_slope", "smooth_dolomite"));
		register(new SlopeBlock(Material.STONE, DyeColor.BROWN, "dolomite", "dolomite_checker_slope", "dolomite_checker"));
		
		register(new LatticeBlock(Material.METAL, DyeColor.GRAY, "tourian", "pipe"));
	}
	
	private static void registerGroup(String groupName, Material material, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			register(new GroupedBlock(material, color, groupName, blockName));
		}
	}
	
	private static <T extends GroupedBlock> T register(T block) {
		Registry.register(Registry.BLOCK, new Identifier(ConventionalCubesMod.MODID, block.getId()), block);
		
		BlockItem item = new BlockItem(block, new QuiltItemSettings().group(ConventionalCubesMod.ITEMGROUP)); //TODO: Lib39 Fractal
		Registry.register(Registry.ITEM, new Identifier(ConventionalCubesMod.MODID, block.getId()), item);
		
		syntheticBlocks.add(block);
		byGroup.put(block.getGroup(), block);
		
		return block;
	}
}
