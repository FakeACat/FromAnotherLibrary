package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistryForge {
    public static void register(CommonMod mod, IEventBus eventBus) {
        if (mod.getItemRegister() == null) {
            return;
        }

        final DeferredRegister<Item> register = DeferredRegister.create(ForgeRegistries.ITEMS, mod.getID());

        mod.getItemRegister().registerAll(register::register);

        register.register(eventBus);
    }
}
