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
import java.util.function.BooleanSupplier;

public class DataPackRegistryForge {
    public static void register(CommonMod mod, final AddPackFindersEvent event){
        if (event.getPackType() == PackType.SERVER_DATA){
            mod.getDataPacks().ifPresent(dataPackLoader ->
                    dataPackLoader.dataPacks.forEach((id, criteria) -> register(id, criteria, event, mod))
            );
        }
    }

    private static void register(String id, BooleanSupplier criteria, final AddPackFindersEvent event, CommonMod mod) {
        if (criteria.getAsBoolean()){
            String realName = mod.getID() + '_' + id;
            FromAnotherLibrary.LOGGER.info("Attempting to load data pack " + '"' + realName + '"');
            Path resourcePath = ModList.get().getModFileById(mod.getID()).getFile().findResource("resourcepacks/" + realName);
            Pack pack = Pack.readMetaAndCreate("builtin/" + realName, Component.literal(realName), false,
                    (path) -> new PathPackResources(path, false, resourcePath), PackType.SERVER_DATA, Pack.Position.BOTTOM, PackSource.BUILT_IN);
            event.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
        }
    }
}
