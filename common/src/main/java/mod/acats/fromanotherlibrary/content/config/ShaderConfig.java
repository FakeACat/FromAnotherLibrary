package mod.acats.fromanotherlibrary.content.config;

import mod.acats.fromanotherlibrary.client.shader.MultiShaderController;
import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.config.v2.properties.BooleanProperty;

public class ShaderConfig extends ModConfig {
    public static final ShaderConfig INSTANCE = new ShaderConfig();
    @Override
    public String name() {
        return "shaders";
    }

    @Override
    protected int version() {
        return 0;
    }

    public final BooleanProperty commandEnabled = addProperty(new BooleanProperty(
            "command_enabled",
            "Should the shader command be enabled?",
            true,
            true
    ));

    public final BooleanProperty shadersEnabled = addProperty(new BooleanProperty(
            "shaders_enabled",
            "Should shaders that are loaded by FromAnotherLibrary be enabled?",
            true,
            false
    )).addOnChangeBehaviour((oldValue, newValue) -> {
        if (!newValue) {
            MultiShaderController.clearShaders();
        }
    });
}
