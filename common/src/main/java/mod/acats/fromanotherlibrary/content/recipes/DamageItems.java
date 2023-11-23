package mod.acats.fromanotherlibrary.content.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import mod.acats.fromanotherlibrary.utilities.item.ItemUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DamageItems implements CraftingRecipe {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> ingredients;
    private final CraftingBookCategory category;
    private final String group;

    public DamageItems(ResourceLocation id,
                             String group,
                             CraftingBookCategory category,
                             ItemStack output,
                             NonNullList<Ingredient> ingredients){
        this.id = id;
        this.output = output;
        this.ingredients = ingredients;
        this.category = category;
        this.group = group;
    }

    @Override
    public @NotNull CraftingBookCategory category() {
        return this.category;
    }

    @Override
    public boolean matches(CraftingContainer container, @NotNull Level level) {
        StackedContents stackedContents = new StackedContents();
        int i = 0;

        for(int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemStack = container.getItem(j);
            if (!itemStack.isEmpty()) {
                ++i;
                stackedContents.accountStack(itemStack, 1);
            }
        }

        return i == this.ingredients.size() && stackedContents.canCraft(this, null);
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return i * j >= this.ingredients.size();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return this.output;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull String getGroup() {
        return this.group;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull CraftingContainer container) {
        final NonNullList<ItemStack> nonNullList = CraftingRecipe.super.getRemainingItems(container);

        for(int i = 0; i < nonNullList.size(); ++i) {
            ItemStack stack = container.getItem(i);
            if (stack.isDamageableItem()){
                nonNullList.set(i, ItemUtils.damage(stack, 1).copy());
            }
        }
        return nonNullList;
    }

    public static class Serializer implements RecipeSerializer<DamageItems> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "damage_items";
        public Serializer() {
        }

        public @NotNull DamageItems fromJson(@NotNull ResourceLocation resourceLocation, @NotNull JsonObject jsonObject) {
            String string = GsonHelper.getAsString(jsonObject, "group", "");
            CraftingBookCategory craftingBookCategory = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(jsonObject, "category", null), CraftingBookCategory.MISC);
            NonNullList<Ingredient> nonNullList = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (nonNullList.isEmpty()) {
                throw new JsonParseException("No ingredients for damage item recipe");
            } else if (nonNullList.size() > 9) {
                throw new JsonParseException("Too many ingredients for damage item recipe");
            } else {
                ItemStack itemStack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
                return new DamageItems(resourceLocation, string, craftingBookCategory, itemStack, nonNullList);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArray) {
            NonNullList<Ingredient> nonNullList = NonNullList.create();

            for(int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i), false);
                if (!ingredient.isEmpty()) {
                    nonNullList.add(ingredient);
                }
            }

            return nonNullList;
        }

        public @NotNull DamageItems fromNetwork(@NotNull ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            String string = friendlyByteBuf.readUtf();
            CraftingBookCategory craftingBookCategory = friendlyByteBuf.readEnum(CraftingBookCategory.class);
            int i = friendlyByteBuf.readVarInt();
            NonNullList<Ingredient> nonNullList = NonNullList.withSize(i, Ingredient.EMPTY);

            nonNullList.replaceAll(ignored -> Ingredient.fromNetwork(friendlyByteBuf));

            ItemStack itemStack = friendlyByteBuf.readItem();
            return new DamageItems(resourceLocation, string, craftingBookCategory, itemStack, nonNullList);
        }

        public void toNetwork(FriendlyByteBuf friendlyByteBuf, DamageItems damageItems) {
            friendlyByteBuf.writeUtf(damageItems.group);
            friendlyByteBuf.writeEnum(damageItems.category);
            friendlyByteBuf.writeVarInt(damageItems.ingredients.size());

            for (Ingredient ingredient : damageItems.ingredients) {
                ingredient.toNetwork(friendlyByteBuf);
            }

            friendlyByteBuf.writeItem(damageItems.output);
        }
    }
}
