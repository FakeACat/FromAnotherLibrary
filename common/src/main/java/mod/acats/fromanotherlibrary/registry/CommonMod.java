package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public interface CommonMod {
    String getID();

    @Nullable Register<Item> getItemRegister();

    default void registerEverything() {
        FromAnotherLibrary.ML_SPECIFIC.registerAllCommonModContent(this);
    }
}
