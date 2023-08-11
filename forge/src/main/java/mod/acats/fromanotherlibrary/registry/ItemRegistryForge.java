package mod.acats.fromanotherlibrary.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

public class ItemRegistryForge {
    public static void register(Register<Item> itemRegister, IEventBus eventBus) {
        final HashMap<String, DeferredRegister<Item>> itemRegisters = new HashMap<>();
        itemRegister.registerAll((id, sup) -> {
            DeferredRegister<Item> register = itemRegisters.computeIfAbsent(id.getNamespace(), (iD) -> DeferredRegister.create(ForgeRegistries.ITEMS, iD));
            register.register(id.getPath(), sup);
        });

        itemRegisters.forEach((id, reg) -> reg.register(eventBus));
    }
}
