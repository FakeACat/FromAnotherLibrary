package mod.acats.fromanotherlibrary.utilities.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;

public class ItemUtils {
    public static ItemStack damage(ItemStack stack, int damage){
        if (stack.isDamageableItem() && stack.hurt(damage, new XoroshiroRandomSource(System.currentTimeMillis()), null)){
            stack.shrink(1);
            stack.setDamageValue(0);
        }
        return stack;
    }
}
