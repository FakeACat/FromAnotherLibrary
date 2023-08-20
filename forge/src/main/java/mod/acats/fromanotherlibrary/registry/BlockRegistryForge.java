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

        mod.getBlockRegister().ifPresent(blockFALRegister -> {

            final DeferredRegister<Block> blockRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, mod.getID());

            blockFALRegister.registerAll((id, sup) -> {

                RegistryObject<Block> block = blockRegister.register(id, sup);

                mod.getItemRegister().ifPresent(itemRegister ->
                        itemRegister.register(id, () -> new BlockItem(block.get(), new Item.Properties()))
                );

            });

            blockRegister.register(bus);

        });
    }
}
