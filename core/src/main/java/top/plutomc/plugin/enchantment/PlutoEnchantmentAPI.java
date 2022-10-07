package top.plutomc.plugin.enchantment;

import net.deechael.useless.function.parameters.DuParameter;
import net.deechael.useless.function.parameters.Parameter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import top.plutomc.plugin.enchantment.api.EnchantmentAPI;
import top.plutomc.plugin.enchantment.api.PlutoEnchantment;
import top.plutomc.plugin.enchantment.api.Trigger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PlutoEnchantmentAPI extends EnchantmentAPI {

    private final Map<String, PlutoEnchantment> enchantments = new HashMap<>();

    private final Map<PlutoEnchantment, EnchantmentTriggers> triggers = new HashMap<>();

    @Override
    public void register(Plugin plugin, PlutoEnchantment enchantment) {
        this.enchantments.put(enchantment.getName(), enchantment);
        EnchantmentTriggers triggers = new EnchantmentTriggers(enchantment);
        Bukkit.getPluginManager().registerEvents(triggers, plugin);
        this.triggers.put(enchantment, triggers);
    }

    @Override
    public PlutoEnchantment getEnchantment(String name) {
        return this.enchantments.get(name);
    }

    @Override
    public boolean hasEnchantment(String name) {
        return this.enchantments.containsKey(name);
    }

    @Override
    public void addTrigger(PlutoEnchantment enchantment, Trigger trigger, DuParameter<Event, List<ItemStack>> onTrigger) {
        if (!this.triggers.containsKey(enchantment))
            throw new RuntimeException("This enchantment hasn't been registered");
        if (trigger == Trigger.ATTACK) {
            this.triggers.get(enchantment).setAttackTrigger(onTrigger);
        } else if (trigger == Trigger.SHOOT) {
            this.triggers.get(enchantment).setShootTrigger(onTrigger);
        } else if (trigger == Trigger.DIG) {
            this.triggers.get(enchantment).setDigTrigger(onTrigger);
        } else if (trigger == Trigger.BE_ATTACKED) {
            this.triggers.get(enchantment).setBeAttackedTrigger(onTrigger);
        }
    }

}
