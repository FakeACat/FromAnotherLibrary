package mod.acats.fromanotherlibrary.registry;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.resource.PathPackResources;

import java.nio.file.Path;

public class ResourcePackRegistryForge {
    public static void register(CommonMod mod, final AddPackFindersEvent event){
        mod.getResourcePacks().ifPresent(dataPackLoader -> dataPackLoader.resourcePacks.forEach(pack -> register(pack, event, mod)));
    }

    private static void register(ResourcePackLoader.FALResourcePack falResourcePack, final AddPackFindersEvent event, CommonMod mod) {
        if ((falResourcePack.data() && event.getPackType() == PackType.CLIENT_RESOURCES) || (!falResourcePack.data() && event.getPackType() == PackType.SERVER_DATA)) {
            return;
        }
        String realName = mod.getID() + '_' + falResourcePack.id();
        FromAnotherLibrary.LOGGER.info("Attempting to load resource pack " + '"' + realName + '"');
        Path resourcePath = ModList.get().getModFileById(mod.getID()).getFile().findResource("resourcepacks/" + realName);
        Pack pack = Pack.readMetaAndCreate(
                "builtin/" + realName,
                Component.translatable("falresourcepack." + mod.getID() + "." + falResourcePack.id()),
                false,
                (path) -> new PathPackResources(path, false, resourcePath),
                event.getPackType(),
                Pack.Position.TOP,
                PackSource.BUILT_IN);
        event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
    }
}
