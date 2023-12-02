package mod.acats.fromanotherlibrary.event.client.shader;

import mod.acats.fromanotherlibrary.event.FALEvent;
import net.minecraft.client.renderer.EffectInstance;

@FunctionalInterface
public interface SetShaderUniforms {
    FALEvent<SetShaderUniforms> EVENT = new FALEvent<>();
    void setUniforms(EffectInstance effect, float partialTick);
}
