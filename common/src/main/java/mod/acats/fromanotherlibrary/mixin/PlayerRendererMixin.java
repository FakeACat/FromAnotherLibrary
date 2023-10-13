package mod.acats.fromanotherlibrary.mixin;

import mod.acats.fromanotherlibrary.utilities.item.Aimable;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Inject(method = "getArmPose", at = @At(value = "TAIL"), cancellable = true)
    private static void tryItemPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> ci) {
        Item item = player.getItemInHand(hand).getItem();
        if (item instanceof Aimable aimable && aimable.aiming(player, hand))
            ci.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
    }
}
