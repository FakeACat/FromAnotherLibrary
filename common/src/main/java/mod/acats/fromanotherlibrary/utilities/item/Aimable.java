package mod.acats.fromanotherlibrary.utilities.item;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;

public interface Aimable {
    boolean aiming(AbstractClientPlayer player, InteractionHand hand);
}
