package com.example.shield_cooldown;
//Made by SuMol91, All rights reserved
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShieldCooldownCommand implements CommandExecutor {

    private final ShieldCooldownPlugin plugin;

    public ShieldCooldownCommand(ShieldCooldownPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Cette commande ne peut être utilisée que par un joueur.");
            return true;
        }

        String cmdName = command.getName().toLowerCase();

        if (cmdName.equals("shield_set_cd")) {
            // /shield_set_cd <id|inhand> <secondes>
            if (args.length != 2) {
                player.sendMessage("§cUsage : /shield_set_cd <id|inhand> <secondes>");
                return true;
            }
            return handleSetCooldown(player, args[0], args[1]);

        } else if (cmdName.equals("shield_see_cd")) {
            // /shield_see_cd <id|inhand>
            if (args.length != 1) {
                player.sendMessage("§cUsage : /shield_see_cd <id|inhand>");
                return true;
            }
            return handleSeeCooldown(player, args[0]);

        } else if (cmdName.equals("shield_id")) {
            // /shield_id : affiche l'ID du bouclier en main principale
            if (args.length != 0) {
                player.sendMessage("§cMade by SuMol91 (Croyez le ou non)");
                return true;
            }
            return handleShowShieldId(player);
        }

        return false;
    }

    private boolean handleSetCooldown(Player player, String target, String secondsStr) {
        int seconds;
        try {
            seconds = Integer.parseInt(secondsStr);
            if (seconds < 0) {
                player.sendMessage("§cLe nombre de secondes doit être positif.");
                return true;
            }
        } catch (NumberFormatException e) {
            player.sendMessage("§cLe deuxième argument doit être un nombre entier (secondes).");
            return true;
        }

        Integer shieldId = getShieldIdFromTarget(player, target);
        if (shieldId == null) return true;

        plugin.getCooldownManager().defineDuration(shieldId, seconds * 20);
        player.sendMessage("§aDurée de cooldown définie à " + seconds + " secondes pour le bouclier ID : " + shieldId);
        return true;
    }

    private boolean handleSeeCooldown(Player player, String target) {
        Integer shieldId = getShieldIdFromTarget(player, target);
        if (shieldId == null) return true;

        int remainingTicks = plugin.getCooldownManager().getCooldownTicks(shieldId);
        int definedDurationTicks = plugin.getCooldownManager().getDefinedDuration(shieldId);

        player.sendMessage("§eBouclier ID: " + shieldId);
        player.sendMessage("§eDurée définie du cooldown: " + (definedDurationTicks / 20.0) + " secondes");
        return true;
    }

    private boolean handleShowShieldId(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() != Material.SHIELD) {
            player.sendMessage("§cVous devez tenir un bouclier dans votre main principale.");
            return true;
        }

        Integer id = plugin.getShieldId(item);
        if (id == null) {
            player.sendMessage("§cCe bouclier n'a pas d'ID assigné.");
            return true;
        }

        player.sendMessage("§aID du bouclier : " + id);
        return true;
    }

    private Integer getShieldIdFromTarget(Player player, String target) {
        if (target.equalsIgnoreCase("inhand")) {
            ItemStack item = player.getInventory().getItemInMainHand(); // main hand
            if (item == null || item.getType() != Material.SHIELD) {
                player.sendMessage("§cVous devez tenir un bouclier dans votre main principale.");
                return null;
            }
            Integer id = plugin.getShieldId(item);
            if (id == null) {
                player.sendMessage("§cCe bouclier n'a pas d'ID assigné.");
                return null;
            }
            return id;
        } else {
            try {
                return Integer.parseInt(target);
            } catch (NumberFormatException e) {
                player.sendMessage("§cL’ID doit être un nombre entier ou 'inhand'.");
                return null;
            }
        }
    }
}
