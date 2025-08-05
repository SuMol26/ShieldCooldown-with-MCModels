package com.example.shield_cooldown;
//Made by SuMol91, All rights reserved
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ShieldListener implements Listener {

    private final ShieldCooldownPlugin plugin;

    public ShieldListener(ShieldCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerShieldBlock(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (!player.isBlocking() && !player.isHandRaised()) {
            return;
        }

        ItemStack shieldMain = player.getInventory().getItemInMainHand();
        ItemStack shieldOff = player.getInventory().getItemInOffHand();

        boolean usingOffHand = false;
        ItemStack shield;
        int slotToUse = -1;

        if (shieldMain.getType() == Material.SHIELD && player.isBlocking()) {
            shield = shieldMain;
            slotToUse = player.getInventory().getHeldItemSlot();
        } else if (shieldOff.getType() == Material.SHIELD && player.isBlocking()) {
            shield = shieldOff;
            usingOffHand = true;
        } else {
            return;
        }

        Integer id = plugin.getShieldId(shield);
        if (id == null) return;

        int cooldownTicksLeft = plugin.getCooldownManager().getCooldownTicks(id);
        if (cooldownTicksLeft > 0) {
            player.setCooldown(Material.SHIELD, cooldownTicksLeft);
            event.setCancelled(true);
            return;
        }

        // Récupère la durée définie (ou par défaut)
        int durationTicks = plugin.getCooldownManager().getDefinedDuration(id);

        // Applique le cooldown
        plugin.getCooldownManager().setCooldown(id, durationTicks);
        player.setCooldown(Material.SHIELD, durationTicks);

        if (usingOffHand) {
            final ItemStack shieldCopy = shield;
            player.getInventory().setItemInOffHand(null);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().setItemInOffHand(shieldCopy);
                }
            }.runTaskLater(plugin, 1L);
        } else {
            final int slotCopy = slotToUse;
            final ItemStack shieldCopy = shield;
            player.getInventory().setItem(slotCopy, null);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.getInventory().setItem(slotCopy, shieldCopy);
                }
            }.runTaskLater(plugin, 1L);
        }
    }
}
