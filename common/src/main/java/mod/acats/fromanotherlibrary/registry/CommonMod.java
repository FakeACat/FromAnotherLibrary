package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Optional;

public interface CommonMod {
    HashMap<String, CommonMod> ALL = new HashMap<>();

    String getID();

    Optional<FALRegister<Item>> getItemRegister();
    Optional<FALRegister<CreativeModeTab>> getTabRegister();
    Optional<TabPopulator> getTabPopulator();
    Optional<FALRegister<Block>> getBlockRegister();
    Optional<ClientMod> getClientMod();

    default void registerEverything() {
        ALL.put(this.getID(), this);
        ModLoaderSpecific.INSTANCE.registerAllCommonModContent(this);
    }
}
