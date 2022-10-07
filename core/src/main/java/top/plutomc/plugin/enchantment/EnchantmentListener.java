package top.plutomc.plugin.enchantment;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;

public final class EnchantmentListener implements Listener {

    @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        event.getItem();
    }

}
