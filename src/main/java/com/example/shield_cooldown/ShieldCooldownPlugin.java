package com.example.shield_cooldown;
//Made by SuMol91, All rights reserved
import org.bukkit.plugin.java.JavaPlugin;

public class ShieldCooldownPlugin extends JavaPlugin {

    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        this.cooldownManager = new CooldownManager();

        // Register event listener
        getServer().getPluginManager().registerEvents(new ShieldListener(this), this);

        // Register commands
        getCommand("shield_set_cd").setExecutor(new ShieldCooldownCommand(this));
        getCommand("shield_see_cd").setExecutor(new ShieldCooldownCommand(this));
        getCommand("shield_id").setExecutor(new ShieldExtraCommands(this));
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    /**
     * Retourne l’ID (CustomModelData) du bouclier si présent, sinon null.
     */
    public Integer getShieldId(org.bukkit.inventory.ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasCustomModelData()) {
            return null;
        }
        return item.getItemMeta().getCustomModelData();
    }
}
