package mod.acats.fromanotherlibrary.mixin.client;

import mod.acats.fromanotherlibrary.event.client.shader.SetShaderUniforms;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostPass.class)
public class PostPassMixin {
    @Final
    @Shadow
    private EffectInstance effect;
    @Inject(method = "process", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EffectInstance;apply()V", shift = At.Shift.BEFORE))
    private void fal$process(float partialTick, CallbackInfo ci) {
        SetShaderUniforms.EVENT.execute(e -> e.setUniforms(effect, partialTick));
    }
}
