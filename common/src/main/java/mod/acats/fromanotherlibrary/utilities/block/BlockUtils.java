package mod.acats.fromanotherlibrary.utilities.block;

import mod.acats.fromanotherlibrary.mixin.FireBlockInvoker;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockUtils {
    public static void setFlammableBlocks(CommonMod mod) {
        if (mod.getBlockRegister() != null) {
            FireBlockInvoker invoker = (FireBlockInvoker) Blocks.FIRE;

            mod.getBlockRegister().forEach((id2, sup) -> {
                Block block = sup.get();
                if (block instanceof Flammable f) {
                    invoker.invokeSetFlammable(block, f.fireSpread(), f.flammability());
                }
            });
        }
    }
}
