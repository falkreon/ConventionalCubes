package blue.endless.ccubes.block;

import java.util.ArrayList;

import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import blue.endless.ccubes.ConventionalCubesMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public class CCubesBlocks {
	public static ArrayList<AbstractGroupedVariant> allBlocks = new ArrayList<>();
	
	public static Multimap<String, AbstractGroupedVariant> byGroup = HashMultimap.create();
	
	public static Block SMOOTH_DOLOMITE;
	
	public static void init() {
		registerCubes("aero", BlockSoundGroup.METAL, DyeColor.PURPLE,
				"purple",
				"purple_ridged",
				"purple_round",
				"gold"
				);
		
		registerCubes("carmine", BlockSoundGroup.STONE, DyeColor.RED,
				"brick",
				"large_tile",
				"small_tiles",
				"smooth",
				"tiles"
				);
		
		registerCubes("celestite", BlockSoundGroup.STONE, DyeColor.LIGHT_BLUE,
				"brick",
				"checker",
				"hexagons",
				"large_tile",
				"prism",
				"small_checker",
				"small_tiles",
				"smooth",
				"tiles",
				
				"dark_brick",
				"dark_hexagons",
				"dark_large_tile",
				"dark_prism",
				"dark_small_tiles",
				"dark_smooth",
				"dark_tiles"
				);
		
		registerSlopes("celestite", BlockSoundGroup.STONE, DyeColor.LIGHT_BLUE,
				"brick",
				"smooth",
				"checker"
				);
		
		registerCubes("dolomite", BlockSoundGroup.STONE, DyeColor.BROWN,
				"brick",
				"checker",
				"hexagons",
				"large_tile",
				"prism",
				"small_checker",
				"small_tiles",
				"smooth",
				"tiles",
				
				"dark_brick",
				"dark_hexagons",
				"dark_large_tile",
				"dark_prism",
				"dark_small_tiles",
				"dark_smooth",
				"dark_tiles"
				);
		SMOOTH_DOLOMITE = retrieve("dolomite", "dolomite_smooth");
		
		registerColumns("dolomite", BlockSoundGroup.STONE, DyeColor.BROWN,
				"column"
				);
		
		registerSlopes("dolomite", BlockSoundGroup.STONE, DyeColor.BROWN,
				"brick",
				"smooth",
				"checker"
				);
		
		registerCubes("erechtheion", BlockSoundGroup.STONE, DyeColor.WHITE,
				"brick",
				"large_tile",
				"small_tiles",
				"smooth",
				"tiles"
				);
		
		registerCubes("fortyfive", BlockSoundGroup.METAL, DyeColor.YELLOW,
				"brick",
				"dots",
				"embossed",
				"grate",
				"large_tile",
				"panel",
				"t",
				"tiles"
				);
		
		registerCubes("gestahl", BlockSoundGroup.METAL, DyeColor.BROWN,
				"treads",
				"domino",
				"light_panel",
				"medium_panel",
				"dark_panel",
				"grate",
				"platform",
				"shadowed",
				"smooth_tech",
				"rumpled_tech",
				"surface",
				"smooth_surface",
				"gray_treads",
				"steps"
				);
		//TODO: Add one-offs for catapult_tech, tangled_pipes ? Different blockgroup?
		registerColumns("gestahl", BlockSoundGroup.METAL, DyeColor.BROWN,
				"girder",
				"rusted_girder",
				"scaffold"
				);
		
		registerCubes("oneup", BlockSoundGroup.STONE, DyeColor.ORANGE,
				"orange_brick",
				"orange_block",
				"uneven_orange_brick",
				"depleted_question",
				"gold_brick",
				"bevel",
				"lime_seastone"
				);
		
		registerCubes("peridot", BlockSoundGroup.STONE, DyeColor.LIME,
				"brick",
				"checker",
				"hexagons",
				"large_tile",
				"prism",
				"rock",
				"small_checker",
				"small_tiles",
				"tiles",
				
				"dark_brick",
				"dark_hexagons",
				"dark_large_tile",
				"dark_prism",
				"dark_rock",
				"dark_small_tiles",
				"dark_tiles"
				);
		
		registerSlopes("peridot", BlockSoundGroup.STONE, DyeColor.LIME,
				"brick",
				"rock",
				"checker"
				);
		
		registerCubes("sanic", BlockSoundGroup.STONE, DyeColor.LIGHT_BLUE,
				"white_checker",
				"white_large_tile",
				"white_tiles",
				"bright_checker",
				"bright_large_tile",
				"bright_tiles",
				"medium_checker",
				"medium_large_tile",
				"medium_tiles",
				"dark_checker",
				"dark_large_tile",
				"dark_tiles",
				"darkest_large_tile",
				"darkest_tiles",
				
				"gold_oil_spike",
				"gold_oil_spikes",
				"oil_checker",
				"purple_oil_spike",
				"purple_oil_spikes"
				);
		
		registerSlopes("sanic", BlockSoundGroup.STONE, DyeColor.LIGHT_BLUE,
				"white_tiles",
				"bright_tiles",
				"medium_tiles",
				"dark_tiles",
				"darkest_tiles",
				"oil_checker"
				);
		
		registerCubes("tourian", BlockSoundGroup.METAL, DyeColor.GRAY,
				"spawner",
				"spawner_on",
				"bevel",
				"block",
				"cracked_block",
				"dented_bevel",
				"hollow_block",
				"interface",
				"pedestal",
				"ruined_block",
				"small_blocks",
				"vent"
				);
		
		register(new LatticeBlock(BlockSoundGroup.METAL, DyeColor.GRAY, "tourian", "pipe"));
		
		registerCubes("verdigris", BlockSoundGroup.METAL, DyeColor.GREEN,
				"grate",
				"spout",
				"surface",
				"tiles",
				"triangles",
				"yoku_block"
				);
		
		registerColumns("verdigris", BlockSoundGroup.METAL, DyeColor.GREEN,
				"border",
				"girder",
				"pipe"
				);
		
		registerCubes("wingfortress", BlockSoundGroup.METAL, DyeColor.LIGHT_BLUE,
				"brace",
				"caution",
				"controller",
				"coolant",
				"corroded",
				"digital",
				"fluids",
				"gauges",
				"lift",
				"orb",
				"panel",
				"panel_light",
				"piston",
				"ports",
				"scaffold",
				"vents"
				);
		
		registerCubes("warpzone", BlockSoundGroup.STONE, DyeColor.BLUE,
				"blue_block",
				"blue_brick",
				"cyan_seastone",
				"minty_rivets",
				"reinforced"
				);
		
		registerCubes("ghosthouse", BlockSoundGroup.STONE, DyeColor.BROWN,
				"brick",
				"wood",
				"gray_dents",
				"uneven_gray_brick"
				);
		
		registerCubes("figaro", BlockSoundGroup.STONE, DyeColor.BROWN,
				"bright_canal",
				"regular_canal",
				"dark_canal",
				"black_canal",
				
				"acidic_gravel",
				"roof_slate",
				"zozo",
				
				"bricks",
				"cobble",
				"edging",
				"path",
				"shingles",
				
				"thamasa_bricks",
				"thamasa",
				
				"vector_bricks",
				"vector"
				);
		
		registerCubes("wattle", BlockSoundGroup.BAMBOO, DyeColor.WHITE,
				"vertical",
				"squares",
				"horizontal",
				
				"eave",
				"brace",
				"star_brace",
				
				"mini_window",
				"port"
				);
	}
	
	private static void registerCubes(String groupName, BlockSoundGroup soundGroup, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			register(new AbstractGroupedVariant(soundGroup, color, groupName, blockName));
		}
	}
	
	private static void registerColumns(String groupName, BlockSoundGroup soundGroup, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			register(new ColumnBlock(soundGroup, color, groupName, blockName));
		}
	}
	
	private static void registerSlopes(String groupName, BlockSoundGroup soundGroup, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			register(new SlopeBlock(soundGroup, color, groupName, blockName));
		}
	}
	
	private static <T extends AbstractGroupedVariant> T register(T block) {
		Registry.register(Registries.BLOCK, new Identifier(ConventionalCubesMod.MODID, block.getIdPath()), block);
		
		BlockItem item = new BlockItem(block, new QuiltItemSettings());
		Registry.register(Registries.ITEM, new Identifier(ConventionalCubesMod.MODID, block.getIdPath()), item);
		
		allBlocks.add(block);
		byGroup.put(block.getGroupName(), block);
		
		return block;
	}
	
	private static Block retrieve(String group, String id) {
		for(AbstractGroupedVariant block : byGroup.get(group)) {
			if (block.getIdPath().equals(id)) return block;
		}
		return Blocks.AIR;
	}
}
