package top.plutomc.plugin.enchantment.api;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class PlutoEnchantment extends Enchantment {

    private final String name;
    private final EnchantmentTarget target;

    public PlutoEnchantment(@NotNull String name, @NotNull EnchantmentTarget target) {
        super(new NamespacedKey("pluto", name));
        this.name = name;
        this.target = target;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return target;
    }

    public abstract int getMaxLevel();

    @Override
    public int getStartLevel() {
        return 1;
    }

    public abstract boolean isTreasure();

    public abstract boolean isCursed();

    public abstract boolean conflictsWith(@NotNull Enchantment other);

    public abstract boolean canEnchantItem(@NotNull ItemStack item);

    public abstract String display(int level);

}
