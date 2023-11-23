package mod.acats.fromanotherlibrary.utilities.entity;

import net.minecraft.world.entity.monster.warden.Warden;

public interface SometimesTargetedByWardens {
    boolean canBeTargetedByWarden(Warden warden);
}
