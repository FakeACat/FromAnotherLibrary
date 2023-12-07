package mod.acats.fromanotherlibrary.content.commands.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.client.screen.ConfigListScreen;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class FALConfigCommand {

    private static final SuggestionProvider<SharedSuggestionProvider> MODS =
            SuggestionProviders.register(new ResourceLocation(FromAnotherLibrary.MOD_ID, "mods_with_configs"),
                    (ctx, builder) -> SharedSuggestionProvider.suggest(CommonMod.ALL.values().stream().filter(mod -> mod.getConfigs().isPresent()).map(CommonMod::getID), builder));

    @SuppressWarnings("unchecked")
    private static <T extends SharedSuggestionProvider> SuggestionProvider<T> mods() {
        return (SuggestionProvider<T>) MODS;
    }

    public <T extends SharedSuggestionProvider> FALConfigCommand(CommandDispatcher<T> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<T>literal("fal_config")
                .then(RequiredArgumentBuilder.<T, String>argument("shaderName", StringArgumentType.greedyString())
                        .suggests(mods())
                        .executes(ctx -> openConfig(StringArgumentType.getString(ctx, "shaderName")))));
    }

    private int openConfig(String modID) throws CommandRuntimeException {
        CommonMod mod = CommonMod.ALL.get(modID);
        if (mod == null || mod.getConfigs().isEmpty()) {
            throw new CommandRuntimeException(Component.translatable("command.fromanotherlibrary.no_config", '"' + modID + '"'));
        }
        mod.getConfigs().ifPresent(configs -> Minecraft.getInstance().setScreen(new ConfigListScreen(modID, configs, null)));
        return 1;
    }
}
