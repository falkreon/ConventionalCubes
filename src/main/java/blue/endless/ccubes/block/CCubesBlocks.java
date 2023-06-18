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
	public static ArrayList<GroupedBlock> syntheticBlocks = new ArrayList<>();
	
	public static Multimap<String, GroupedBlock> byGroup = HashMultimap.create();
	
	public static Block SMOOTH_DOLOMITE;
	
	public static void init() {
		//SMOOTH_DOLOMITE = register(new GroupedBlock(BlockSoundGroup.STONE, DyeColor.BROWN, "dolomite", "smooth_dolomite"));
		
		registerGroup("dolomite", BlockSoundGroup.STONE, DyeColor.BROWN,
				"smooth_dolomite",
				"dolomite_large_tile",
				"dolomite_tiles",
				"dolomite_small_tiles",
				"dolomite_brick",
				"dolomite_checker",
				"dolomite_small_checker"
				);
		SMOOTH_DOLOMITE = retrieve("dolomite", "smooth_dolomite");
		
		registerGroup("oneup", BlockSoundGroup.STONE, DyeColor.ORANGE,
				"oneup_orange_brick",
				"oneup_orange_block",
				"oneup_uneven_orange_brick",
				"oneup_depleted_question",
				"oneup_gold_brick"
				);
		
		registerGroup("tourian", BlockSoundGroup.METAL, DyeColor.GRAY,
				"tourian_spawner",
				"tourian_bevel",
				"tourian_dented_bevel",
				"tourian_vent",
				"tourian_hollow_block"
				);
		
		registerGroup("aero", BlockSoundGroup.METAL, DyeColor.PURPLE,
				"aero_purple",
				"aero_purple_ridged",
				"aero_gold"
				);
		
		register(new SlopeBlock(BlockSoundGroup.STONE, DyeColor.BROWN, "dolomite", "smooth_dolomite_slope", "smooth_dolomite"));
		register(new SlopeBlock(BlockSoundGroup.STONE, DyeColor.BROWN, "dolomite", "dolomite_checker_slope", "dolomite_checker"));
		
		register(new LatticeBlock(BlockSoundGroup.METAL, DyeColor.GRAY, "tourian", "pipe"));
	}
	
	private static void registerGroup(String groupName, BlockSoundGroup soundGroup, DyeColor color, String... blocks) {
		for(String blockName : blocks) {
			register(new GroupedBlock(soundGroup, color, groupName, blockName));
		}
	}
	
	private static <T extends GroupedBlock> T register(T block) {
		Registry.register(Registries.BLOCK, new Identifier(ConventionalCubesMod.MODID, block.getId()), block);
		
		BlockItem item = new BlockItem(block, new QuiltItemSettings());
		Registry.register(Registries.ITEM, new Identifier(ConventionalCubesMod.MODID, block.getId()), item);
		
		syntheticBlocks.add(block);
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
