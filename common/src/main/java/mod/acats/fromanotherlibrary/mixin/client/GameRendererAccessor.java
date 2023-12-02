package mod.acats.fromanotherlibrary.mixin.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@ApiStatus.Internal
@Mixin(GameRenderer.class)
public interface GameRendererAccessor {

    @Accessor("EFFECTS")
    static ResourceLocation[] getEffects() {
        throw new AssertionError();
    }
}
