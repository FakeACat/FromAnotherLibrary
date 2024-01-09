package mod.acats.fromanotherlibrary.client.screen;

import com.google.common.collect.ImmutableList;
import mod.acats.fromanotherlibrary.config.v2.properties.SpawnValuesProperty;
import mod.acats.fromanotherlibrary.utilities.Maths;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class SpawnValuesEditorScreen extends Screen {
    private final @Nullable Screen prev;
    private final SpawnValuesProperty property;
    public SpawnValuesEditorScreen(Component component, @Nullable Screen prev, SpawnValuesProperty property) {
        super(component);
        this.prev = prev;
        this.property = property;
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(new SpawnValuesList());
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .size(300, 30)
                .pos(width / 2 - 150, height - 31)
                .build()
        );
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(prev);
    }

    private static abstract class SpawnValueEntry extends ContainerObjectSelectionList.Entry<SpawnValueEntry> {
    }

    public static class TitleEntry extends SpawnValueEntry {
        final Component label;

        public TitleEntry(Component $$1) {
            this.label = $$1;
        }

        @Override
        public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
            $$0.drawCenteredString(Minecraft.getInstance().font, this.label, $$3 + $$4 / 2, $$2 + 5, 16777215);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return ImmutableList.of();
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return ImmutableList.of(new NarratableEntry() {
                @Override
                public NarratableEntry.NarrationPriority narrationPriority() {
                    return NarratableEntry.NarrationPriority.HOVERED;
                }

                @Override
                public void updateNarration(NarrationElementOutput $$0) {
                    $$0.add(NarratedElementType.TITLE, TitleEntry.this.label);
                }
            });
        }
    }

    public class EnabledEntry extends SpawnValueEntry {

        private final CycleButton<Boolean> button;

        public EnabledEntry() {
            button = CycleButton.booleanBuilder(Component.translatable("gui.fromanotherlibrary.config.true"), Component.translatable("gui.fromanotherlibrary.config.false"))
                    .withInitialValue(property.menuValue().enabled())
                    .displayOnlyValue()
                    .create(10, 5, 60, 20, Component.literal("Boolean Option"), (button, bl) -> property.updatedValue = property.menuValue().setEnabled(bl));
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(button);
        }

        @Override
        public void render(GuiGraphics var1, int var2, int y, int var4, int var5, int var6, int mouseX, int mouseY, boolean var9, float var10) {
            button.setX(width / 2 - 30);
            button.setY(y);
            button.render(var1, mouseX, mouseY, var10);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(button);
        }
    }

    public abstract class IntEntry extends SpawnValueEntry {

        private final EditBox editBox;

        public IntEntry() {
            editBox = new EditBox(Minecraft.getInstance().font, 10, 5, 60, 20, Component.literal("Integer Option"));
            editBox.setValue(Integer.toString(initialValue()));
            editBox.setResponder(str -> Maths.tryParseInt(str).ifPresentOrElse(
                    this::setValue,
                    () -> setValue(currentValue())));
        }

        protected abstract int initialValue();
        protected abstract int currentValue();
        protected abstract void setValue(int value);

        @Override
        public List<? extends NarratableEntry> narratables() {
            return List.of(editBox);
        }

        @Override
        public void render(GuiGraphics var1, int var2, int y, int var4, int var5, int var6, int mouseX, int mouseY, boolean var9, float var10) {
            editBox.setX(width / 2 - 30);
            editBox.setY(y);
            editBox.render(var1, mouseX, mouseY, var10);
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return List.of(editBox);
        }
    }

    public class SpawnValuesList extends ContainerObjectSelectionList<SpawnValueEntry> {
        public SpawnValuesList() {
            super(
                    Minecraft.getInstance(),
                    SpawnValuesEditorScreen.this.width,
                    SpawnValuesEditorScreen.this.height,
                    43,
                    SpawnValuesEditorScreen.this.height - 32,
                    24
            );

            addEntry(new TitleEntry(Component.translatable("gui.fromanotherlibrary.config.spawn_values.enabled")));
            addEntry(new EnabledEntry());
            addEntry(new TitleEntry(Component.translatable("gui.fromanotherlibrary.config.spawn_values.weight")));
            addEntry(new IntEntry() {
                @Override
                protected int initialValue() {
                    return property.menuValue().weight();
                }

                @Override
                protected int currentValue() {
                    return property.get().weight();
                }

                @Override
                protected void setValue(int value) {
                    property.updatedValue = property.menuValue().setWeight(value);
                }
            });
            addEntry(new TitleEntry(Component.translatable("gui.fromanotherlibrary.config.spawn_values.min")));
            addEntry(new IntEntry() {
                @Override
                protected int initialValue() {
                    return property.menuValue().groupMin();
                }

                @Override
                protected int currentValue() {
                    return property.get().groupMin();
                }

                @Override
                protected void setValue(int value) {
                    property.updatedValue = property.menuValue().setMin(value);
                }
            });
            addEntry(new TitleEntry(Component.translatable("gui.fromanotherlibrary.config.spawn_values.max")));
            addEntry(new IntEntry() {
                @Override
                protected int initialValue() {
                    return property.menuValue().groupMax();
                }

                @Override
                protected int currentValue() {
                    return property.get().groupMax();
                }

                @Override
                protected void setValue(int value) {
                    property.updatedValue = property.menuValue().setMax(value);
                }
            });
        }
    }
}
