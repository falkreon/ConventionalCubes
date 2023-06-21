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
	public static ArrayList<GroupedBlock> allBlocks = new ArrayList<>();
	
	public static Multimap<String, GroupedBlock> byGroup = HashMultimap.create();
	
	public static Block SMOOTH_DOLOMITE;
	
	public static void init() {
		//SMOOTH_DOLOMITE = register(new GroupedBlock(BlockSoundGroup.STONE, DyeColor.BROWN, "dolomite", "smooth_dolomite"));
		
		registerCubes("dolomite", BlockSoundGroup.STONE, DyeColor.BROWN,
				"smooth",
				"large_tile",
				"tiles",
				"small_tiles",
				"brick",
				"checker",
				"small_checker"
				);
		SMOOTH_DOLOMITE = retrieve("dolomite", "dolomite_smooth");
		
		registerCubes("oneup", BlockSoundGroup.STONE, DyeColor.ORANGE,
				"orange_brick",
				"orange_block",
				"uneven_orange_brick",
				"depleted_question",
				"gold_brick"
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
		//TODO: Add one-offs for catapult_tech, tangled_pipes, steps, girder, scaffold, rusted_girder
		registerColumns("gestahl", BlockSoundGroup.METAL, DyeColor.BROWN,
				"girder",
				"rusted_girder",
				"scaffold"
				);
		
		registerCubes("tourian", BlockSoundGroup.METAL, DyeColor.GRAY,
				"spawner",
				"bevel",
				"dented_bevel",
				"vent",
				"hollow_block"
				);
		
		registerCubes("aero", BlockSoundGroup.METAL, DyeColor.PURPLE,
				"purple",
				"purple_ridged",
				"gold"
				);
		
		register(new SlopeBlock(BlockSoundGroup.STONE, DyeColor.BROWN, "dolomite", "dolomite_smooth_slope", "dolomite_smooth"));
		register(new SlopeBlock(BlockSoundGroup.STONE, DyeColor.BROWN, "dolomite", "dolomite_checker_slope", "dolomite_checker"));
		
		register(new LatticeBlock(BlockSoundGroup.METAL, DyeColor.GRAY, "tourian", "pipe"));
	}
	
	private static void registerCubes(String groupName, BlockSoundGroup soundGroup, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			String name = groupName+"_"+blockName;
			register(new GroupedBlock(soundGroup, color, groupName, name));
		}
	}
	
	private static void registerColumns(String groupName, BlockSoundGroup soundGroup, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			String name = groupName+"_"+blockName;
			register(new ColumnBlock(soundGroup, color, groupName, name));
		}
	}
	
	private static <T extends GroupedBlock> T register(T block) {
		Registry.register(Registries.BLOCK, new Identifier(ConventionalCubesMod.MODID, block.getId()), block);
		
		BlockItem item = new BlockItem(block, new QuiltItemSettings());
		Registry.register(Registries.ITEM, new Identifier(ConventionalCubesMod.MODID, block.getId()), item);
		
		allBlocks.add(block);
		byGroup.put(block.getGroup(), block);
		
		return block;
	}
	
	private static Block retrieve(String group, String id) {
		for(GroupedBlock block : byGroup.get(group)) {
			if (block.getId().equals(id)) return block;
		}
		return Blocks.AIR;
	}
}
