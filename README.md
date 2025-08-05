# Shield Cooldown Plugin

A simple Minecraft plugin that allows custom cooldowns for shields using `CustomModelData`.

## 🔧 Features

- Assign custom cooldown durations (in seconds) to shields.
- Support for shields using `CustomModelData` (e.g., from resource packs like McModels).
- Three commands to manage cooldowns:
    - `/shield_set_cd <id|inhand> <seconds>` – Set cooldown for a specific CustomModelData or the shield in your main hand.
    - `/shield_see_cd <id|inhand>` – View the cooldown set for a specific CustomModelData or held shield.
    - `/shield_id` – Shows the creator of the plugin (ignore)

## 🧱 How it works

- When a shield blocks damage, a cooldown is applied based on its `CustomModelData`.
- If no cooldown was set for the ID, a default of 5 seconds is applied.
- Uses Minecraft's built-in cooldown system and replaces the shield temporarily to simulate unequip.

## 📦 Requirements

- Minecraft 1.16+ (uses `CustomModelData`)
- A compatible resource pack for custom shield models (e.g., McModels)

## 💡 Example

- /shield_set_cd inhand 3 or /shield_set_cd 1234
- /shield_see_cd inhand or /shield_see_cd 1234

##  🚩 Suggestions
Feel free to make any suggestion and report thing you would like me to change/upgrade

### PS: This plugin was entirely made with AI. I am not a dev, just a prompt seeker.