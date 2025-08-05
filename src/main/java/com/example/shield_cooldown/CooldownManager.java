package com.example.shield_cooldown;
//Made by SuMol91, All rights reserved
import java.util.HashMap;

public class CooldownManager {

    private final HashMap<Integer, Long> cooldowns = new HashMap<>();
    private final HashMap<Integer, Integer> definedDurations = new HashMap<>(); // durées définies (ticks)

    public void setCooldown(int shieldId, int ticksDuration) {
        long expireTime = System.currentTimeMillis() + ticksDuration * 50L;
        cooldowns.put(shieldId, expireTime);
    }

    public int getCooldownTicks(int shieldId) {
        if (!cooldowns.containsKey(shieldId)) return 0;
        long expire = cooldowns.get(shieldId);
        long remainingMs = expire - System.currentTimeMillis();
        if (remainingMs <= 0) {
            cooldowns.remove(shieldId);
            return 0;
        }
        return (int)(remainingMs / 50);
    }

    // Ajouté : définit la durée "de base" du cooldown (en ticks)
    public void defineDuration(int shieldId, int ticksDuration) {
        definedDurations.put(shieldId, ticksDuration);
    }

    // Ajouté : récupère la durée définie (en ticks) ou 0 si non défini
    public int getDefinedDuration(int shieldId) {
        return definedDurations.getOrDefault(shieldId, 0);
    }
}