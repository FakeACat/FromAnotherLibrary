package mod.acats.fromanotherlibrary.mixin;

import mod.acats.fromanotherlibrary.utilities.player.FALServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements FALServerPlayer {

    @Unique
    private boolean fal$givenStarterItems = false;
    @Override
    @Unique
    public void fal$setGivenStarterItems(boolean bl) {
        fal$givenStarterItems = bl;
    }

    @Override
    @Unique
    public boolean fal$hasGivenStarterItems() {
        return fal$givenStarterItems;
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void fal$readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if (compoundTag.contains("FALGivenStarterItems")) {
            fal$givenStarterItems = compoundTag.getBoolean("FALGivenStarterItems");
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void fal$addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        compoundTag.putBoolean("FALGivenStarterItems", fal$givenStarterItems);
    }
}
