package mod.acats.fromanotherlibrary.content.client.shaders;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.content.commands.client.DevShaderCommand;
import net.minecraft.resources.ResourceLocation;

public class FALShaders {
    public static final ResourceLocation IMMERSED_IN_DARKNESS = register("immersed_in_darkness");
    public static void register() {
    }

    private static ResourceLocation register(String name) {
        ResourceLocation location = new ResourceLocation(FromAnotherLibrary.MOD_ID, "shaders/post/" + name + ".json");
        DevShaderCommand.SHADER_LIST.add(location);
        return location;
    }
}
