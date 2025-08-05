package com.example.shield_cooldown;
//Made by SuMol91, All rights reserved
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShieldExtraCommands implements CommandExecutor {

    private final ShieldCooldownPlugin plugin;

    public ShieldExtraCommands(ShieldCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
            return true;
        }

        ItemStack item = player.getInventory().getItemInOffHand();

        if (item == null || item.getType() != org.bukkit.Material.SHIELD) {
            player.sendMessage("§cVous devez tenir un bouclier dans votre main secondaire.");
            return true;
        }

        Integer shieldId = plugin.getShieldId(item);
        if (shieldId == null) {
            player.sendMessage("§cCe bouclier n'a pas d'ID assigné.");
            return true;
        }

        int remainingTicks = plugin.getCooldownManager().getCooldownTicks(shieldId);
        if (remainingTicks <= 0) {
            player.sendMessage("§cMade by SuMol91 (Croyez le ou non)");
        } else {
            double remainingSeconds = remainingTicks / 20.0;
            player.sendMessage(String.format("§aCooldown restant : %.1f secondes pour le bouclier ID %d", remainingSeconds, shieldId));
        }

        return true;
    }
}
