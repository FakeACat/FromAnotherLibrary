package mod.acats.fromanotherlibrary.utilities.block;

import mod.acats.fromanotherlibrary.mixin.FireBlockInvoker;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.ApiStatus;

public class BlockUtils {
    @ApiStatus.Internal
    public static void setFlammableBlocks(CommonMod mod) {
        mod.getBlockRegister().ifPresent(blockRegister -> {

            FireBlockInvoker invoker = (FireBlockInvoker) Blocks.FIRE;

            blockRegister.forEach((id2, sup) -> {
                Block block = sup.get();
                if (block instanceof Flammable f) {
                    invoker.fal$invokeSetFlammable(block, f.fireSpread(), f.flammability());
                }
            });

        });
    }
}
