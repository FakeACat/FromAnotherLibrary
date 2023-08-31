package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.io.File;
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
    Optional<FALRegister<ParticleType<?>>> getParticleRegister();
    Optional<DataPackLoader> getDataPacks();

    void loadConfigs(File configFolder);

    default void preRegisterContent() {
    }

    default void postRegisterContent() {
    }

    /**
     * Registers all common content from in the CommonMod
     * ClientRegistryFabric.registerClient must be used in the ClientModInitializer to register client content on Fabric
     */
    default void init() {
        ALL.put(this.getID(), this);

        File configFolder = new File(ModLoaderSpecific.INSTANCE.getConfigDirectory(this).toFile(), this.getID() + "/");
        if (!configFolder.exists() && !configFolder.mkdirs()){
            FromAnotherLibrary.LOGGER.error("Unable to create config directory for " + '"' + this.getID() + '"');
        }
        else {
            this.loadConfigs(configFolder);
        }

        this.preRegisterContent();
        ModLoaderSpecific.INSTANCE.registerAllCommonModContent(this);
        this.postRegisterContent();
    }
}
