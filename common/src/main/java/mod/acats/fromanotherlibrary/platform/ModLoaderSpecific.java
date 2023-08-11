package mod.acats.fromanotherlibrary.platform;

import mod.acats.fromanotherlibrary.registry.CommonMod;

public interface ModLoaderSpecific {
    boolean isInDev();
    void registerAllCommonModContent(CommonMod mod);
}
