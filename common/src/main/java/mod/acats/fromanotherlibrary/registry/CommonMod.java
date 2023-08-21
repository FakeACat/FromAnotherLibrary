package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public interface CommonMod {
    /**
     * HashMap containing all CommonMod instances registered so far
     * Key - the mod's ID
     * Value - the CommonMod instance
     */
    HashMap<String, CommonMod> ALL = new HashMap<>();

    String getID();

    Optional<FALRegister<Item>> getItemRegister();
    Optional<FALRegister<CreativeModeTab>> getTabRegister();
    Optional<TabPopulator> getTabPopulator();
    Optional<FALRegister<Block>> getBlockRegister();
    Optional<FALRegister<BlockEntityType<?>>> getBlockEntityRegister();
    Optional<FALRegister<EntityType<?>>> getEntityRegister();
    Optional<HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>>> getEntityAttributeRegister();
    Optional<ClientMod> getClientMod();

    /**
     * Registers all common content from in the CommonMod
     * ClientRegistryFabric.registerClient must be used in the ClientModInitializer to register client content on Fabric
     */
    default void registerEverything() {
        ALL.put(this.getID(), this);
        ModLoaderSpecific.INSTANCE.registerAllCommonModContent(this);
    }
}
