package mod.acats.fromanotherlibrary;

import mod.acats.fromanotherlibrary.content.recipes.DamageItems;
import mod.acats.fromanotherlibrary.starteritems.StarterItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class FromAnotherLibraryFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        FromAnotherLibrary.init();

        registerRecipeTypes();

        ServerPlayerEvents.AFTER_RESPAWN.register((oldP, newP, alive) -> {
            if (!alive) {
                StarterItems.giveOnRespawn(newP);
            }
        });
    }

    private void registerRecipeTypes() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation(FromAnotherLibrary.MOD_ID, DamageItems.Serializer.ID), DamageItems.Serializer.INSTANCE);
    }
}
