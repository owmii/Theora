package xieao.theora.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import xieao.theora.Theora;

public class TheoraCommand {

    public TheoraCommand(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
                LiteralArgumentBuilder.literal(Theora.MOD_ID)
        );
    }
}
