package top.plutomc.plugin.enchantment;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTListCompound;
import net.deechael.useless.function.parameters.DuParameter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import top.plutomc.plugin.enchantment.api.PlutoEnchantment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class EnchantmentTriggers implements Listener {

    private final PlutoEnchantment enchantment;

    private DuParameter<Event, List<ItemStack>> attackTrigger = (event, items) -> {};
    private DuParameter<Event, List<ItemStack>> shootTrigger = (event, items) -> {};
    private DuParameter<Event, List<ItemStack>> digTrigger = (event, items) -> {};
    private DuParameter<Event, List<ItemStack>> beAttackedTrigger = (event, items) -> {};

    public EnchantmentTriggers(PlutoEnchantment enchantment) {
        this.enchantment = enchantment;
    }

    public PlutoEnchantment getEnchantment() {
        return enchantment;
    }

    public void setAttackTrigger(DuParameter<Event, List<ItemStack>> attackTrigger) {
        this.attackTrigger = attackTrigger;
    }

    public void setShootTrigger(DuParameter<Event, List<ItemStack>> shootTrigger) {
        this.shootTrigger = shootTrigger;
    }

    public void setDigTrigger(DuParameter<Event, List<ItemStack>> digTrigger) {
        this.digTrigger = digTrigger;
    }

    public void setBeAttackedTrigger(DuParameter<Event, List<ItemStack>> beAttackedTrigger) {
        this.beAttackedTrigger = beAttackedTrigger;
    }

    @EventHandler
    public void onAttackAndShoot(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            trigger(event, player, attackTrigger);
        } else if (event.getDamager() instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player player) {
                trigger(event, player, shootTrigger);
            }
        }
        if (event.getEntity() instanceof Player player) {
            trigger(event, player, beAttackedTrigger);
        }
    }

    @EventHandler
    public void onDig(BlockBreakEvent event) {
        trigger(event, event.getPlayer(), digTrigger);
    }

    private void trigger(Event event, Player player, DuParameter<Event, List<ItemStack>> trigger) {
        PlayerInventory inventory = player.getInventory();
        List<ItemStack> items = new ArrayList<>();
        ItemStack onMainHand = inventory.getItemInMainHand();
        ItemStack onOffHand = inventory.getItemInOffHand();
        switch (enchantment.getItemTarget()) {
            case ARMOR:
            case WEARABLE:
                for (ItemStack itemStack : inventory.getArmorContents()) {
                    if (hasEnchantment(itemStack, this.enchantment))
                        items.add(itemStack);
                }
                break;
            case ARMOR_FEET:
                ItemStack onFeet = inventory.getBoots();
                if (hasEnchantment(onFeet, this.enchantment))
                    items.add(onFeet);
                break;
            case ARMOR_LEGS:
                ItemStack onLegs = inventory.getLeggings();
                if (hasEnchantment(onLegs, this.enchantment))
                    items.add(onLegs);
                break;
            case ARMOR_TORSO:
                ItemStack onTorso = inventory.getChestplate();
                if (hasEnchantment(onTorso, this.enchantment))
                    items.add(onTorso);
                break;
            case ARMOR_HEAD:
                ItemStack onHead = inventory.getHelmet();
                if (hasEnchantment(onHead, this.enchantment))
                    items.add(onHead);
                break;
            case WEAPON:
            case TOOL:
            case BREAKABLE:
                if (hasEnchantment(onMainHand, this.enchantment))
                    items.add(onMainHand);
                break;
            case BOW:
            case FISHING_ROD:
            case TRIDENT:
            case CROSSBOW:
                if (hasEnchantment(onMainHand, this.enchantment))
                    items.add(onMainHand);
                if (hasEnchantment(onOffHand, this.enchantment))
                    items.add(onOffHand);
                break;
            case VANISHABLE:
                for (ItemStack itemStack : inventory) {
                    if (hasEnchantment(itemStack, this.enchantment))
                        items.add(itemStack);
                }
                break;
        }
        if (items.isEmpty())
            return;
        trigger.apply(event, items);
    }

    private boolean hasEnchantment(ItemStack itemStack, PlutoEnchantment enchantment) {
        if (itemStack == null)
            return false;
        if (!enchantment.getItemTarget().includes(itemStack.getType()))
            return false;
        NBTItem nbtItem = new NBTItem(itemStack);
        boolean findEnchantment = false;
        for (NBTListCompound nbtListCompound : nbtItem.getCompoundList("pluto-enchantments")) {
            if (nbtListCompound.getCompound() instanceof NBTCompound compound) {
                if (Objects.equals(compound.getString("id"), enchantment.getName())) {
                    findEnchantment = true;
                    break;
                }
            }
        }
        return findEnchantment;
    }

}
