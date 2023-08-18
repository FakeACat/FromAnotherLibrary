package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistryForge {
    public static void register(CommonMod mod, IEventBus bus) {
        if (mod.getBlockRegister() == null) {
            return;
        }

        final DeferredRegister<Block> blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, mod.getID());
        mod.getBlockRegister().registerAll((id, sup) -> {
            RegistryObject<Block> block = blockRegister.register(id, sup);
            if (mod.getItemRegister() != null) {
                mod.getItemRegister().register(id, () -> new BlockItem(block.get(), new Item.Properties()));
            }
        });
        blockRegister.register(bus);
    }
}
