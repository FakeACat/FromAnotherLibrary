package mod.acats.fromanotherlibrary.starteritems;

import com.google.common.collect.Lists;
import mod.acats.fromanotherlibrary.utilities.player.FALServerPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Supplier;

public class StarterItems {
    private static final List<StarterItemStack> STARTER_ITEM_STACKS = Lists.newArrayList();

    public static void add(Supplier<ItemStack> stackSupplier, boolean alsoGivenOnRespawn) {
        STARTER_ITEM_STACKS.add(new StarterItemStack(stackSupplier, alsoGivenOnRespawn));
    }

    @ApiStatus.Internal
    public static void giveInitial(ServerPlayer player) {
        FALServerPlayer p = (FALServerPlayer) player;
        if (!p.fal$hasGivenStarterItems()) {
            STARTER_ITEM_STACKS.forEach(stack -> player.addItem(stack.stackSupplier.get()));
            p.fal$setGivenStarterItems(true);
        }
    }

    @ApiStatus.Internal
    public static void giveOnRespawn(ServerPlayer player) {
        STARTER_ITEM_STACKS.forEach(stack -> {
            if (stack.alsoGivenOnRespawn()) {
                player.addItem(stack.stackSupplier.get());
            }
        });
    }

    private record StarterItemStack(Supplier<ItemStack> stackSupplier,
                                   boolean alsoGivenOnRespawn){
    }
}
