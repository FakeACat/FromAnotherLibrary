package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.CommonMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.util.ServiceLoader;
import java.util.function.Supplier;

public interface ModLoaderSpecific {
    ModLoaderSpecific INSTANCE = ServiceLoader.load(ModLoaderSpecific.class)
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Failed to load service for " + ModLoaderSpecific.class.getName()));
    boolean isInDev();
    boolean isFabric();
    boolean isForge();
    void registerAllCommonModContent(CommonMod mod);
    CreativeModeTab createTab(ResourceLocation id, Supplier<ItemStack> iconSupplier);
    SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends Mob>> entityTypeSupplier, int primaryColour, int secondaryColour);
}
