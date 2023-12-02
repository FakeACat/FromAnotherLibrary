package mod.acats.fromanotherlibrary.mixin.client;

import mod.acats.fromanotherlibrary.client.shader.MultiShaderController;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(at = @At("RETURN"), method = "reloadShaders")
    private void fal$reloadShaders(ResourceProvider $$0, CallbackInfo ci) {
        MultiShaderController.reload();
    }

    @Inject(at = @At("HEAD"), method = "resize")
    private void fal$resize(int $$0, int $$1, CallbackInfo ci) {
        MultiShaderController.resize($$0, $$1);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;doEntityOutline()V", shift = At.Shift.AFTER), method = "render")
    private void fal$render(float $$0, long $$1, boolean $$2, CallbackInfo ci) {
        MultiShaderController.render($$0);
    }
}
