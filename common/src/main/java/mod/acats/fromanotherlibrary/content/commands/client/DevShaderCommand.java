package mod.acats.fromanotherlibrary.content.commands.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.client.shader.MultiShaderController;
import mod.acats.fromanotherlibrary.mixin.client.GameRendererAccessor;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DevShaderCommand {

    public static final List<ResourceLocation> SHADER_LIST = new ArrayList<>(Arrays.asList(GameRendererAccessor.getEffects()));

    private static final SuggestionProvider<SharedSuggestionProvider> SELECTABLE_SHADERS =
            SuggestionProviders.register(new ResourceLocation(FromAnotherLibrary.MOD_ID, "selectable_shaders"),
            (ctx, builder) -> SharedSuggestionProvider.suggest(SHADER_LIST.stream().map(ResourceLocation::toString), builder));

    @SuppressWarnings("unchecked")
    private static <T extends SharedSuggestionProvider> SuggestionProvider<T> selectableShader() {
        return (SuggestionProvider<T>) SELECTABLE_SHADERS;
    }

    public <T extends SharedSuggestionProvider> DevShaderCommand(CommandDispatcher<T> dispatcher) {
        dispatcher.register(LiteralArgumentBuilder.<T>literal("fal_shader")
                .then(LiteralArgumentBuilder.<T>literal("toggle")
                        .then(RequiredArgumentBuilder.<T, String>argument("shaderName", StringArgumentType.greedyString())
                                .suggests(selectableShader())
                                .executes(ctx -> toggle(StringArgumentType.getString(ctx, "shaderName")))))
                .then(LiteralArgumentBuilder.<T>literal("clear")
                        .executes((ctx) -> clear())));
    }

    private int toggle(String input) throws CommandRuntimeException {
        ResourceLocation location = new ResourceLocation(input);
        if (!SHADER_LIST.contains(location)) {
            throw new CommandRuntimeException(Component.literal(input + " is not a supported shader!"));
        }
        if (MultiShaderController.isShaderLoaded(location)) {
            MultiShaderController.unloadShader(location);
        } else {
            MultiShaderController.loadShader(location);
        }
        return 1;
    }

    private int clear() {
        MultiShaderController.clearShaders();
        return 1;
    }
}
