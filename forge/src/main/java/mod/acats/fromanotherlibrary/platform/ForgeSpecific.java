package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ForgeSpecific implements ModLoaderSpecific {
    @Override
    public boolean isInDev() {
        return FMLLoader.isProduction();
    }

    @Override
    public boolean isFabric() {
        return false;
    }

    @Override
    public boolean isForge() {
        return true;
    }

    @Override
    public void registerAllCommonModContent(CommonMod mod) {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EntityRegistryForge.register(mod, eventBus);
        TabRegistryForge.register(mod, eventBus);
        BlockRegistryForge.register(mod, eventBus);
        ItemRegistryForge.register(mod, eventBus);
        BlockEntityRegistryForge.register(mod, eventBus);
    }

    @Override
    public CreativeModeTab createTab(ResourceLocation id, Supplier<ItemStack> iconSupplier) {
        return CreativeModeTab.builder()
                .title(Component.translatable(id.getNamespace() + "." + id.getPath()))
                .icon(iconSupplier)
                .build();
    }

    @Override
    public SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends Mob>> entityTypeSupplier, int primaryColour, int secondaryColour) {
        return new ForgeSpawnEggItem(entityTypeSupplier, primaryColour, secondaryColour, new Item.Properties());
    }

    @Override
    public Path getConfigDirectory(CommonMod mod) {
        return FMLPaths.CONFIGDIR.get();
    }
}
