package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.*;
import mod.acats.fromanotherlibrary.spawning.SimpleSpawns;
import mod.acats.fromanotherlibrary.utilities.block.BlockUtils;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.ApiStatus;

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

    @ApiStatus.Internal
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
        ResourcePackRegistryFabric.register(mod);
        SoundEventRegistryFabric.register(mod);
        MobEffectRegistryFabric.register(mod);

        CommandRegistrationCallback.EVENT.register(mod::registerCommands);
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

    @Override
    public SimpleSpawns getSimpleSpawns() {
        return FabricSpecific::addSpawnEntry;
    }

    private static <T extends Mob> void addSpawnEntry(Supplier<EntityType<T>> entityTypeSupplier,
                                                      MobCategory category,
                                                      int weight,
                                                      int groupMin,
                                                      int groupMax,
                                                      SpawnPlacements.Type placementType,
                                                      Heightmap.Types heightmapType,
                                                      SpawnPlacements.SpawnPredicate<T> spawnPredicate,
                                                      TagKey<Biome> biomes){
        BiomeModifications.addSpawn(BiomeSelectors.tag(biomes), category, entityTypeSupplier.get(), weight, groupMin, groupMax);
        SpawnPlacements.register(entityTypeSupplier.get(), placementType, heightmapType, spawnPredicate);
    }
}
