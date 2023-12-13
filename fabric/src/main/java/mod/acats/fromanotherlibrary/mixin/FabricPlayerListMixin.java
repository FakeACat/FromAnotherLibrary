package mod.acats.fromanotherlibrary.mixin;

import mod.acats.fromanotherlibrary.starteritems.StarterItems;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public abstract class FabricPlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    private void fal$placeNewPlayer(Connection connection, ServerPlayer serverPlayer, CallbackInfo ci) {
        StarterItems.giveInitial(serverPlayer);
    }
}
