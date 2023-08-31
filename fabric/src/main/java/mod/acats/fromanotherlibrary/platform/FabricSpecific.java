package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.*;
import mod.acats.fromanotherlibrary.utilities.block.BlockUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

import java.nio.file.Path;
import java.util.function.Supplier;

public class FabricSpecific implements ModLoaderSpecific {
    @Override
    public boolean isInDev() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isFabric() {
        return true;
    }

    @Override
    public boolean isForge() {
        return false;
    }

    @Override
    public void registerAllCommonModContent(CommonMod mod) {
        EntityRegistryFabric.register(mod);
        TabRegistryFabric.register(mod);
        BlockRegistryFabric.register(mod);
        BlockUtils.setFlammableBlocks(mod);
        ItemRegistryFabric.register(mod);
        TabRegistryFabric.populate(mod);
        BlockEntityRegistryFabric.register(mod);
        ParticleRegistryFabric.register(mod);
        DataPackRegistryFabric.register(mod);
    }

    @Override
    public CreativeModeTab createTab(ResourceLocation id, Supplier<ItemStack> iconSupplier) {
        return FabricItemGroup.builder()
                .title(Component.translatable(id.getNamespace() + "." + id.getPath()))
                .icon(iconSupplier)
                .build();
    }

    @Override
    public SpawnEggItem createSpawnEgg(Supplier<EntityType<? extends Mob>> entityTypeSupplier, int primaryColour, int secondaryColour) {
        return new SpawnEggItem(entityTypeSupplier.get(), primaryColour, secondaryColour, new Item.Properties());
    }

    @Override
    public Path getConfigDirectory(CommonMod mod) {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
