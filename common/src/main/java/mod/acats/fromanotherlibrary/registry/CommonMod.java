package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public interface CommonMod {
    HashMap<String, CommonMod> ALL = new HashMap<>();

    String getID();

    @Nullable Register<Item> getItemRegister();
    @Nullable Register<CreativeModeTab> getTabRegister();
    @Nullable TabPopulator getTabPopulator();
    @Nullable Register<Block> getBlockRegister();
    @Nullable ClientMod getClientMod();

    default void registerEverything() {
        ALL.put(this.getID(), this);
        ModLoaderSpecific.INSTANCE.registerAllCommonModContent(this);
    }
}
