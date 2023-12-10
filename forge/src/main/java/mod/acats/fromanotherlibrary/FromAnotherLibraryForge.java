package mod.acats.fromanotherlibrary;

import com.mojang.serialization.Codec;
import mod.acats.fromanotherlibrary.content.recipes.DamageItems;
import mod.acats.fromanotherlibrary.spawning.SpawningBiomeModifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(FromAnotherLibrary.MOD_ID)
public class FromAnotherLibraryForge {
    
    public FromAnotherLibraryForge() {
        FromAnotherLibrary.init();

        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(eventBus);

        registerRecipeTypes(eventBus);


        final DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FromAnotherLibrary.MOD_ID);
        biomeModifiers.register(eventBus);
        biomeModifiers.register("spawns", SpawningBiomeModifier::makeCodec);
    }

    private void registerRecipeTypes(IEventBus eventBus) {

        final DeferredRegister<RecipeSerializer<?>> recipeSerializers = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FromAnotherLibrary.MOD_ID);

        recipeSerializers.register(DamageItems.Serializer.ID, () -> DamageItems.Serializer.INSTANCE);

        recipeSerializers.register(eventBus);
    }
}