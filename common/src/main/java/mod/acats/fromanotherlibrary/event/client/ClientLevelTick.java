package mod.acats.fromanotherlibrary.event.client;

import mod.acats.fromanotherlibrary.event.FALEvent;
import net.minecraft.client.multiplayer.ClientLevel;

public interface ClientLevelTick {
    FALEvent<ClientLevelTick> EVENT = new FALEvent<>();
    void tick(ClientLevel level);
}
