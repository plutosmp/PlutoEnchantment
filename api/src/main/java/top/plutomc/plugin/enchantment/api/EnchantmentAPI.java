package top.plutomc.plugin.enchantment.api;

import net.deechael.useless.function.parameters.DuParameter;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Javadoc is required
 */
public abstract class EnchantmentAPI {

    private static EnchantmentAPI api;

    public static void setApi(EnchantmentAPI api) {
        if (EnchantmentAPI.api != null)
            throw new RuntimeException("You are not allowed to set the api");
        EnchantmentAPI.api = api;
    }

    public static EnchantmentAPI getApi() {
        return api;
    }

    public abstract void register(Plugin plugin, PlutoEnchantment enchantment);

    public abstract PlutoEnchantment getEnchantment(String name);

    public abstract boolean hasEnchantment(String name);

    public abstract void addTrigger(PlutoEnchantment enchantment, Trigger trigger, DuParameter<Event, List<ItemStack>> onTrigger);

}
