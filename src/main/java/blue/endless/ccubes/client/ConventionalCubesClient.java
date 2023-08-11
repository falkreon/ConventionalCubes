package blue.endless.ccubes.client;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.command.api.client.ClientCommandRegistrationCallback;
import org.quiltmc.qsl.command.api.client.QuiltClientCommandSource;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.ItemStack;

public class ConventionalCubesClient implements ClientModInitializer {
	
	public static int debug(CommandContext<QuiltClientCommandSource> context) throws CommandSyntaxException {
		ItemStack heldItem = context.getSource().getPlayer().getInventory().getMainHandStack();
		context.getSource().sendFeedback(heldItem.getName());
		
		return Command.SINGLE_SUCCESS;
	}
	
	@Override
	public void onInitializeClient(ModContainer mod) {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, buildContext, environment) -> {
			var root = dispatcher.getRoot();
			var concubes = LiteralArgumentBuilder.<QuiltClientCommandSource>literal("concubes").build();
			var debug = LiteralArgumentBuilder.<QuiltClientCommandSource>literal("debug")
					.executes(ConventionalCubesClient::debug)
					.build();
			
			root.addChild(concubes);
			concubes.addChild(debug);
		});
	}

}
