package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        mod.getItemRegister().ifPresent(itemRegister -> {

            final DeferredRegister<Item> register = DeferredRegister.create(ForgeRegistries.ITEMS, mod.getID());

            itemRegister.registerAll(register::register);

            register.register(eventBus);
        });
    }
}
