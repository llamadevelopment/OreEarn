package net.lldv.oreearn.components.language;

import cn.nukkit.utils.Config;
import net.lldv.oreearn.OreEarn;

import java.util.HashMap;
import java.util.Map;

public class Language {

    public final static Map<String, String> messages = new HashMap<>();

    public static void init(final OreEarn plugin) {
        messages.clear();
        plugin.saveResource("messages.yml");
        Config m = new Config(plugin.getDataFolder() + "/messages.yml");
        m.getAll().forEach((key, value) -> {
            if (value instanceof String) {
                String val = (String) value;
                messages.put(key, val);
            }
        });
    }

    public static String getNP(String key, Object... replacements) {
        String message = messages.getOrDefault(key, "null").replace("&", "§");

        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }

        return message;
    }

}
