package mod.acats.fromanotherlibrary.mixin;

import mod.acats.fromanotherlibrary.content.tags.FALEntityTypeTags;
import mod.acats.fromanotherlibrary.utilities.entity.SometimesTargetedByWardens;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Warden.class)
public class WardenMixin {
    @Inject(at = @At("HEAD"), method = "canTargetEntity", cancellable = true)
    private void fal$canWardenTargetEntity(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (target.getType().is(FALEntityTypeTags.IGNORED_BY_WARDENS) ||
                (target instanceof SometimesTargetedByWardens sometimesTargeted && !sometimesTargeted.canBeTargetedByWarden((Warden) (Object) this))) {
            cir.setReturnValue(false);
        }
    }
}
