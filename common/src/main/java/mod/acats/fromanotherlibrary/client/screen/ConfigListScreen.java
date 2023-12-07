package mod.acats.fromanotherlibrary.client.screen;

import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class ConfigListScreen extends ListScreen<ModConfig> {
    private final @Nullable Screen prev;
    private final String modID;
    private final ModConfig[] configs;
    public ConfigListScreen(String modID, ModConfig[] configs, @Nullable Screen prev) {
        super(Component.translatable("gui." + modID + ".config.name"));
        this.prev = prev;
        this.modID = modID;
        this.configs = configs;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .size(300, 30)
                .pos(width / 2 - 150, height - 31)
                .build()
        );
    }

    @Override
    protected ModConfig[] defaultEntries() {
        return configs;
    }

    @Override
    protected int xOffset(int defaultX, AbstractWidget widget, ModConfig value) {
        return width / 2 - 100;
    }

    @Override
    protected List<AbstractWidget> createChildrenFor(ScreenListEntry entry) {
        ModConfig config = entry.value;
        List<AbstractWidget> list = Lists.newArrayList();
        list.add(Button.builder(Component.translatable("gui." + modID + ".config." + config.name()), button -> openConfig(config))
                .size(200, 20)
                .build());
        return list;
    }

    private void openConfig(ModConfig config) {
        Minecraft.getInstance().setScreen(new ConfigEditorScreen(modID, config, this));
    }

    @Override
    public void render(@NotNull GuiGraphics $$0, int $$1, int $$2, float $$3) {
        renderBackground($$0);
        super.render($$0, $$1, $$2, $$3);
        $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(prev);
    }
}
