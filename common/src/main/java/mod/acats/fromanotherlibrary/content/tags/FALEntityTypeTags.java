package mod.acats.fromanotherlibrary.content.tags;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class FALEntityTypeTags {
    public static final TagKey<EntityType<?>> IGNORED_BY_WARDENS = entityTag("ignored_by_wardens");
    private static TagKey<EntityType<?>> entityTag(String id){
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(FromAnotherLibrary.MOD_ID, id));
    }
}
