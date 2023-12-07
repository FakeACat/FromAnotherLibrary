package mod.acats.fromanotherlibrary.registry;

import com.mojang.brigadier.CommandDispatcher;
import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
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

    default Optional<ClientMod> getClientMod() {
        return Optional.empty();
    }

    default Optional<FALRegister<Item>> getItemRegister() {
        return Optional.empty();
    }
    default Optional<FALRegister<CreativeModeTab>> getTabRegister() {
        return Optional.empty();
    }
    default Optional<TabPopulator> getTabPopulator() {
        return Optional.empty();
    }

    default Optional<FALRegister<Block>> getBlockRegister() {
        return Optional.empty();
    }
    default Optional<FALRegister<BlockEntityType<?>>> getBlockEntityRegister() {
        return Optional.empty();
    }

    default Optional<FALRegister<EntityType<?>>> getEntityRegister() {
        return Optional.empty();
    }
    default Optional<HashMap<
                    EntityType<? extends LivingEntity>,
                    Supplier<AttributeSupplier.Builder>
                    >> getEntityAttributeRegister() {
        return Optional.empty();
    }

    default Optional<FALRegister<ParticleType<?>>> getParticleRegister()  {
        return Optional.empty();
    }

    default Optional<FALRegister<SoundEvent>> getSoundEventRegister() {
        return Optional.empty();
    }

    default Optional<FALRegister<MobEffect>> getMobEffectRegister() {
        return Optional.empty();
    }

    default Optional<ResourcePackLoader> getResourcePacks() {
        return Optional.empty();
    }

    default void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext buildContext, Commands.CommandSelection selection){
    }

    default void loadConfigs(File configFolder){
        getConfigs().ifPresent(configs -> {
            for (ModConfig config:
                 configs) {
                config.load(configFolder);
            }
        });
    }

    default Optional<ModConfig[]> getConfigs() {
        return Optional.empty();
    }

    default void preRegisterContent() {
    }

    default void postRegisterContent() {
    }

    /**
     * Registers all common content in the CommonMod
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
