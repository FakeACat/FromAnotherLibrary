package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.*;
import mod.acats.fromanotherlibrary.spawning.ForgeSimpleSpawns;
import mod.acats.fromanotherlibrary.spawning.SimpleSpawns;
import mod.acats.fromanotherlibrary.spawning.SpawnEntry;
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
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ForgeSpecific implements ModLoaderSpecific {
    @Override
    public boolean isInDev() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isFabric() {
        return false;
    }

    @Override
    public boolean isForge() {
        return true;
    }

    @ApiStatus.Internal
    @Override
    public void registerAllCommonModContent(CommonMod mod) {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EntityRegistryForge.register(mod, eventBus);
        TabRegistryForge.register(mod, eventBus);
        BlockRegistryForge.register(mod, eventBus);
        ItemRegistryForge.register(mod, eventBus);
        BlockEntityRegistryForge.register(mod, eventBus);
        ParticleRegistryForge.register(mod, eventBus);
        SoundEventRegistryForge.register(mod, eventBus);
        MobEffectRegistryForge.register(mod, eventBus);

        eventBus.addListener((AddPackFindersEvent event) -> ResourcePackRegistryForge.register(mod, event));
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

    @Override
    public boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    @Override
    public SimpleSpawns getSimpleSpawns() {
        return ForgeSpecific::addSpawnEntry;
    }

    private static <T extends Mob> void addSpawnEntry(Supplier<EntityType<T>> entityTypeSupplier,
                                                      MobCategory category,
                                                      int weight,
                                                      int groupMin,
                                                      int groupMax,
                                                      SpawnPlacements.Type placementType,
                                                      Heightmap.Types heightmapType,
                                                      SpawnPlacements.SpawnPredicate<T> spawnPredicate,
                                                      TagKey<Biome> biomes) {
        ForgeSimpleSpawns.ENTRIES.add(new SpawnEntry<>(entityTypeSupplier, category, weight, groupMin, groupMax, placementType, heightmapType, spawnPredicate, biomes));
    }
}
