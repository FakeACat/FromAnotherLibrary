package mod.acats.fromanotherlibrary.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class TabPopulator {
    final HashMap<ResourceKey<CreativeModeTab>, List<Supplier<Item>>> entries = new HashMap<>();

    @SafeVarargs
    public final void setTabs(Supplier<Item> item, ResourceKey<CreativeModeTab>... tabs) {
        for (ResourceKey<CreativeModeTab> tab:
             tabs) {
            List<Supplier<Item>> list = entries.computeIfAbsent(tab, t -> new ArrayList<>());
            list.add(item);
        }
    }

    public void forEach(BiConsumer<? super ResourceKey<CreativeModeTab>, ? super List<Supplier<Item>>> function) {
        this.entries.forEach(function);
    }
}
