package mod.acats.fromanotherlibrary.spawning;

import com.mojang.serialization.Codec;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SpawningBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER =
            RegistryObject.create(new ResourceLocation(FromAnotherLibrary.MOD_ID, "spawns"),
                    ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FromAnotherLibrary.MOD_ID);

    @Override
    public void modify(Holder<Biome> arg, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        for (SpawnEntry<?> e : ForgeSimpleSpawns.ENTRIES) {
            if (arg.is(e.biomes())) {
                builder.getMobSpawnSettings().getSpawner(e.category()).add(new MobSpawnSettings.SpawnerData(e.entityTypeSupplier().get(), e.weight(), e.groupMin(), e.groupMax()));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<SpawningBiomeModifier> makeCodec() {
        return Codec.unit(SpawningBiomeModifier::new);
    }
}
