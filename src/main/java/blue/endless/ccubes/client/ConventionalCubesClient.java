package blue.endless.ccubes.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.item.ItemStack;

public class ConventionalCubesClient implements ClientModInitializer {
	
	public static int debug(CommandContext<FabricClientCommandSource> context) throws CommandSyntaxException {
		ItemStack heldItem = context.getSource().getPlayer().getInventory().getMainHandStack();
		context.getSource().sendFeedback(heldItem.getName());
		
		return Command.SINGLE_SUCCESS;
	}
	
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			var root = dispatcher.getRoot();
			var concubes = LiteralArgumentBuilder.<FabricClientCommandSource>literal("concubes").build();
			var debug = LiteralArgumentBuilder.<FabricClientCommandSource>literal("debug")
					.executes(ConventionalCubesClient::debug)
					.build();
			
			root.addChild(concubes);
			concubes.addChild(debug);
		});
	}

}
