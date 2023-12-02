package mod.acats.fromanotherlibrary.client.shader;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiShaderController {
    private static final HashMap<ResourceLocation, PostChain> SHADERS = new HashMap<>();

    private static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static void loadShader(ResourceLocation location) {
        PostChain postEffect = SHADERS.get(location);
        if (postEffect != null) {
            postEffect.close();
        }
        try {
            postEffect = new PostChain(getMinecraft().getTextureManager(), getMinecraft().getResourceManager(), getMinecraft().getMainRenderTarget(), location);
            postEffect.resize(getMinecraft().getWindow().getWidth(), getMinecraft().getWindow().getHeight());
            SHADERS.put(location, postEffect);
        } catch (IOException var3) {
            FromAnotherLibrary.LOGGER.warn("Failed to load shader: {}", location, var3);
        } catch (JsonSyntaxException var4) {
            FromAnotherLibrary.LOGGER.warn("Failed to parse shader: {}", location, var4);
        }
    }

    public static void unloadShader(ResourceLocation location) {
        PostChain postEffect = SHADERS.get(location);
        if (postEffect != null) {
            postEffect.close();
        }
        SHADERS.remove(location);
    }

    public static boolean isShaderLoaded(ResourceLocation location) {
        return SHADERS.containsKey(location) && SHADERS.get(location) != null;
    }

    public static void clearShaders() {
        SHADERS.forEach((id, effect) -> effect.close());
        SHADERS.clear();
    }

    @ApiStatus.Internal
    public static void reload() {
        final List<ResourceLocation> currentShaders = new ArrayList<>();

        SHADERS.forEach((id, shader) -> {
            shader.close();
            currentShaders.add(id);
        });

        SHADERS.clear();

        if (!currentShaders.isEmpty()) {
            currentShaders.forEach(MultiShaderController::loadShader);
        }
    }

    @ApiStatus.Internal
    public static void resize(int width, int height) {
        SHADERS.forEach((id, shader) -> {
            if (shader != null) {
                shader.resize(width, height);
            }
        });
    }

    @ApiStatus.Internal
    public static void render(float partialTick) {
        if (!SHADERS.isEmpty()) {
            RenderSystem.disableBlend();
            RenderSystem.resetTextureMatrix();
            RenderSystem.disableDepthTest();
            for (PostChain shader:
                 SHADERS.values()) {
                if (shader != null) {
                    shader.process(partialTick);
                }
            }
        }
    }
}
