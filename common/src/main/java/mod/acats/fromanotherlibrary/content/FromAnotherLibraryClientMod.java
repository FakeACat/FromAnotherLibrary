package mod.acats.fromanotherlibrary.content;

import com.mojang.brigadier.CommandDispatcher;
import mod.acats.fromanotherlibrary.content.commands.client.DevShaderCommand;
import mod.acats.fromanotherlibrary.content.client.shaders.FALShaders;
import mod.acats.fromanotherlibrary.content.config.ShaderConfig;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;

public class FromAnotherLibraryClientMod implements ClientMod {

    @Override
    public void registerClientCommands(CommandDispatcher<? extends SharedSuggestionProvider> dispatcher, CommandBuildContext buildContext) {
        if (ShaderConfig.INSTANCE.commandEnabled.get()) {
            new DevShaderCommand(dispatcher);
        }
    }

    @Override
    public void setupClient() {
        FALShaders.register();
    }
}
