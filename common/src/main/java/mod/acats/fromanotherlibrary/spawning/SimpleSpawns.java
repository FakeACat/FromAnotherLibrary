package mod.acats.fromanotherlibrary.spawning;

import mod.acats.fromanotherlibrary.config.v2.SpawnValues;
import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

@FunctionalInterface
public interface SimpleSpawns {
    static SimpleSpawns get() {
        return ModLoaderSpecific.INSTANCE.getSimpleSpawns();
    }

    <T extends Mob> void add(Supplier<EntityType<T>> entityTypeSupplier,
                            MobCategory category,
                            int weight,
                            int groupMin,
                            int groupMax,
                            SpawnPlacements.Type placementType,
                            Heightmap.Types heightmapType,
                            SpawnPlacements.SpawnPredicate<T> spawnPredicate,
                            TagKey<Biome> biomes);

    default <T extends Monster> void addMonster(Supplier<EntityType<T>> entityTypeSupplier,
                                                      int weight,
                                                      int min,
                                                      int max,
                                                      SpawnPlacements.SpawnPredicate<T> spawnPredicate,
                                                      TagKey<Biome> biomes) {
        add(
                entityTypeSupplier,
                MobCategory.MONSTER,
                weight,
                min,
                max,
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                spawnPredicate,
                biomes
        );
    }

    default <T extends Monster> void addMonster(Supplier<EntityType<T>> entityTypeSupplier,
                                                      SpawnValues spawnValues,
                                                      SpawnPlacements.SpawnPredicate<T> spawnPredicate,
                                                      TagKey<Biome> biomes) {
        addMonster(
                entityTypeSupplier,
                spawnValues.weight(),
                spawnValues.groupMin(),
                spawnValues.groupMax(),
                spawnPredicate,
                biomes
        );
    }
}
