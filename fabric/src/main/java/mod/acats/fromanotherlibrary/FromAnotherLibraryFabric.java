package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.content.recipes.DamageItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class FromAnotherLibraryFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        FromAnotherLibrary.init();

        registerRecipeTypes();
    }

    private void registerRecipeTypes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(FromAnotherLibrary.MOD_ID, DamageItems.Serializer.ID), DamageItems.Serializer.INSTANCE);
    }
}
