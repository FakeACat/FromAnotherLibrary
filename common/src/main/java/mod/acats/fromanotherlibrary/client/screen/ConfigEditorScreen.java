package mod.acats.fromanotherlibrary.client.screen;

import mod.acats.fromanotherlibrary.config.v2.ConfigProperty;
import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.config.v2.properties.*;
import mod.acats.fromanotherlibrary.utilities.Maths;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.compress.utils.Lists;
import org.apache.logging.log4j.util.TriConsumer;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.HashMap;
import java.util.List;

@NonnullDefault
public class ConfigEditorScreen extends ListScreen<ConfigProperty<?>> {
    private final @Nullable Screen prev;
    private final ModConfig config;
    private final String modID;

    public static final HashMap<Class<? extends ConfigProperty<?>>, TriConsumer<List<AbstractWidget>, ConfigProperty<?>, ConfigEditorScreen>> WIDGET_REGISTER;

    protected ConfigEditorScreen(String modID, ModConfig config, @Nullable Screen prev) {
        super(Component.translatable("gui." + modID + ".config." + config.name()));
        this.config = config;
        this.prev = prev;
        this.modID = modID;
    }

    private @Nullable Button saveButton;
    private @Nullable Button resetButton;
    @Override
    protected void init() {
        super.init();
        GridLayout.RowHelper rowHelper = new GridLayout().columnSpacing(10).createRowHelper(4);
        this.saveButton = rowHelper.addChild(Button.builder(Component.translatable("gui.fromanotherlibrary.config.save"),
                button -> save()).size(100, 30).build());
        this.resetButton = rowHelper.addChild(Button.builder(Component.translatable("gui.fromanotherlibrary.config.reset"),
                button -> reset()).size(100, 30).build());
        rowHelper.addChild(Button.builder(Component.translatable("gui.fromanotherlibrary.config.open"),
                button -> Util.getPlatform().openUri(config.getFile().toPath().toUri())).size(100, 30).build());
        rowHelper.addChild(Button.builder(CommonComponents.GUI_BACK,
                button -> onClose()).size(100, 30).build());
        rowHelper.getGrid().visitWidgets(this::addRenderableWidget);
        rowHelper.getGrid().setPosition(this.width / 2 - 215, this.height - 31);
        rowHelper.getGrid().arrangeElements();
        updateButtons();
    }

    @Override
    protected int xOffset(int defaultX, AbstractWidget widget, ConfigProperty<?> value) {
        return defaultX - 45;
    }

    private void reset() {
        for (ConfigProperty<?> property:
                config.getProperties()) {
            resetPropertyValue(property);
        }

        config.genFile(!config.getFile().exists());

        onClose();
    }

    private <T> void resetPropertyValue(ConfigProperty<T> property) {
        property.changeValueInGame(property.defaultValue);
        property.updatedValue = property.defaultValue;
    }

    private void save() {
        for (ConfigProperty<?> property:
                config.getProperties()) {
            if (property.changedButUnsaved()) {
                updatePropertyValue(property);
            }
        }

        config.genFile(!config.getFile().exists());

        updateButtons();
    }

    private <T> void updatePropertyValue(ConfigProperty<T> property) {
        property.changeValueInGame(property.updatedValue);
    }

    private void updateButtons() {
        updateSaveButton();
        updateResetButton();
    }

    private void updateSaveButton() {
        assert saveButton != null;
        for (ConfigProperty<?> property:
             config.getProperties()) {
            if (property.changedButUnsaved()) {
                saveButton.active = true;
                return;
            }
        }
        saveButton.active = false;
    }

    private void updateResetButton() {
        assert resetButton != null;
        for (ConfigProperty<?> property:
                config.getProperties()) {
            if (!property.usingDefaultValue()) {
                resetButton.active = true;
                return;
            }
        }
        resetButton.active = false;
    }

    @Override
    protected ConfigProperty<?>[] defaultEntries() {
        return config.getProperties();
    }

    @Override
    public void render(List<AbstractWidget> children, ConfigProperty<?> value, GuiGraphics var1, int var2, int y, int var4, int var5, int var6, int mouseX, int mouseY, boolean var9, float var10) {
        super.render(children, value, var1, var2, y, var4, var5, var6, mouseX, mouseY, var9, var10);
        var1.drawString(Minecraft.getInstance().font, Component.translatable("gui." + modID + ".config." + config.name() + "." + value.getName()), var4, y + 5, 16777215, false);
    }

    @Override
    public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        super.render($$0, $$1, $$2, $$3);
        $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
        ScreenList list = getList();
        if (list != null) {
            ScreenListEntry entry = getList().getHovered();
            if (entry != null) {
                MutableComponent component = Component.translatableWithFallback("gui." + modID + ".config." + config.name() + "." + entry.value.getName() + ".description", entry.value.description);
                if (entry.value.requiresRestart) {
                    component.append("\n").append(Component.translatable("gui.fromanotherlibrary.config.requires_restart").withStyle(ChatFormatting.RED));
                }
                setTooltipForNextRenderPass(component);
            }
        }
    }

    @Override
    protected List<AbstractWidget> createChildrenFor(ScreenListEntry entry) {
        List<AbstractWidget> list = Lists.newArrayList();
        ConfigProperty<?> p = entry.value;
        WIDGET_REGISTER.get(p.getClass()).accept(list, p, this);
        return list;
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(prev);
    }

    private static void createCheckbox(List<AbstractWidget> list, ConfigProperty<?> p, ConfigEditorScreen screen) {
        BooleanProperty property = (BooleanProperty) p;
        list.add(CycleButton.booleanBuilder(Component.translatable("gui.fromanotherlibrary.config.true"), Component.translatable("gui.fromanotherlibrary.config.false"))
                .withInitialValue(property.menuValue())
                .displayOnlyValue()
                .create(10, 5, 44, 20, Component.literal("Boolean Option"), (button, bl) -> {
                    property.updatedValue = bl;
                    screen.updateButtons();
                }));
    }

    private static void createIntEditBox(List<AbstractWidget> list, ConfigProperty<?> p, ConfigEditorScreen screen) {
        IntegerProperty property = (IntegerProperty) p;
        EditBox input = new EditBox(Minecraft.getInstance().font, 10, 5, 44, 20, Component.literal("Integer Option"));
        input.setValue(Integer.toString(property.menuValue()));
        input.setResponder(str -> {
                    Maths.tryParseInt(str).ifPresentOrElse(
                            validInput -> property.updatedValue = validInput,
                            () -> property.updatedValue = property.get());
                    screen.updateButtons();
        });
        list.add(input);
    }

    private static void createFloatEditBox(List<AbstractWidget> list, ConfigProperty<?> p, ConfigEditorScreen screen) {
        FloatProperty property = (FloatProperty) p;
        EditBox input = new EditBox(Minecraft.getInstance().font, 10, 5, 44, 20, Component.literal("Float Option"));
        input.setValue(Float.toString(property.menuValue()));
        input.setResponder(str -> {
            Maths.tryParseFloat(str).ifPresentOrElse(
                    validInput -> property.updatedValue = validInput,
                    () -> property.updatedValue = property.get());
            screen.updateButtons();
        });
        list.add(input);
    }

    private static void createArrayScreenButton(List<AbstractWidget> list, ConfigProperty<?> p, ConfigEditorScreen screen) {
        ArrayProperty property = (ArrayProperty) p;
        list.add(Button.builder(Component.translatable("gui.fromanotherlibrary.config.edit"), button -> Minecraft.getInstance().setScreen(new ArrayEditorScreen(
                Component.translatable("gui." + screen.modID + ".config." + screen.config.name() + "." + property.getName()),
                screen,
                array -> {
                    property.updatedValue = array;
                    screen.updateButtons();
                },
                property::menuValue
        ))).size(44, 20).build());
    }

    private static void createSpawnValuesScreenButton(List<AbstractWidget> list, ConfigProperty<?> p, ConfigEditorScreen screen) {
        SpawnValuesProperty property = (SpawnValuesProperty) p;
        list.add(Button.builder(Component.translatable("gui.fromanotherlibrary.config.edit"), button -> Minecraft.getInstance().setScreen(new SpawnValuesEditorScreen(
                Component.translatable("gui." + screen.modID + ".config." + screen.config.name() + "." + property.getName()),
                screen,
                property
        ))).size(44, 20).build());
    }

    static {
        WIDGET_REGISTER = new HashMap<>();
        WIDGET_REGISTER.put(BooleanProperty.class, ConfigEditorScreen::createCheckbox);
        WIDGET_REGISTER.put(IntegerProperty.class, ConfigEditorScreen::createIntEditBox);
        WIDGET_REGISTER.put(FloatProperty.class, ConfigEditorScreen::createFloatEditBox);
        WIDGET_REGISTER.put(ArrayProperty.class, ConfigEditorScreen::createArrayScreenButton);
        WIDGET_REGISTER.put(SpawnValuesProperty.class, ConfigEditorScreen::createSpawnValuesScreenButton);
    }
}
