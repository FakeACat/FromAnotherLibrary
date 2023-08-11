package mod.acats.fromanotherlibrary.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class ItemRegistryFabric {
    public static void register(Register<Item> itemRegister) {
        itemRegister.registerAll((id, sup) -> Registry.register(BuiltInRegistries.ITEM, id, sup.get()));
    }
}
