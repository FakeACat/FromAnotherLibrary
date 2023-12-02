package mod.acats.fromanotherlibrary.content;

import com.mojang.brigadier.CommandDispatcher;
import mod.acats.fromanotherlibrary.content.commands.client.DevShaderCommand;
import mod.acats.fromanotherlibrary.content.client.shaders.FALShaders;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;

public class FromAnotherLibraryClientMod implements ClientMod {

    @Override
    public void registerClientCommands(CommandDispatcher<? extends SharedSuggestionProvider> dispatcher, CommandBuildContext buildContext) {
        new DevShaderCommand(dispatcher);
    }

    @Override
    public void registerShaders() {
        FALShaders.register();
    }
}
