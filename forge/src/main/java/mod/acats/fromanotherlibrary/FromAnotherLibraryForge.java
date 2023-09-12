package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.recipes.DamageItems;
import net.minecraft.world.item.crafting.RecipeSerializer;
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

        registerRecipeTypes(eventBus);
    }

    private void registerRecipeTypes(IEventBus eventBus) {

        final DeferredRegister<RecipeSerializer<?>> recipeSerializers = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FromAnotherLibrary.MOD_ID);

        recipeSerializers.register(DamageItems.Serializer.ID, () -> DamageItems.Serializer.INSTANCE);

        recipeSerializers.register(eventBus);
    }
}