package net.lldv.oreearn;

import cn.nukkit.block.BlockID;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;
import net.lldv.oreearn.components.data.Ore;
import net.lldv.oreearn.components.language.Language;
import net.lldv.oreearn.components.listener.EventListener;

import java.util.*;

/**
 * @author LlamaDevelopment
 * @project OreEarn
 * @website http://llamadevelopment.net/
 */
public class OreEarn extends PluginBase {

    @Getter
    private final Map<Integer, Ore> ores = new HashMap<>();
    @Getter
    private int notificationType;
    private Config config;
    @Getter
    private boolean drops, autoSmelt, ignoreSilktouch, checkWorld;
    @Getter
    private Set<String> worlds;

    @Override
    public void onEnable() {
        this.getLogger().info("§aStarting OreEarn...");
        try {
            Language.init(this);
            this.saveDefaultConfig();

            this.config = getConfig();

            this.notificationType = this.config.getInt("notificationType");
            this.drops = this.config.getBoolean("drops");
            this.autoSmelt = this.config.getBoolean("autoSmelt");
            this.ignoreSilktouch = this.config.getBoolean("ignoreSilktouch");
            this.worlds = new HashSet<>(this.config.getStringList("worlds"));

            this.checkWorld = this.worlds.size() > 0;

            this.loadOre("stone", BlockID.STONE, null);
            this.loadOre("quartz", BlockID.QUARTZ_ORE, null);
            this.loadOre("coal", BlockID.COAL_ORE, null);
            this.loadOre("iron", BlockID.IRON_ORE, Item.get(Item.IRON_INGOT));
            this.loadOre("redstone", BlockID.REDSTONE_ORE, null);
            this.loadOre("redstone", BlockID.GLOWING_REDSTONE_ORE, null);
            this.loadOre("lapis", BlockID.LAPIS_ORE, null);
            this.loadOre("gold", BlockID.GOLD_ORE, Item.get(Item.GOLD_INGOT));
            this.loadOre("diamond", BlockID.DIAMOND_ORE, null);
            this.loadOre("emerald", BlockID.EMERALD_ORE, null);

            this.getServer().getPluginManager().registerEvents(new EventListener(this), this);

            this.getLogger().info("§aDone.");
        } catch (Exception ex) {
            this.getLogger().error("§cCouldn't start OreEarn!", ex);
        }
    }

    private void loadOre(final String key, int id, Item smeltResult) {
        if (this.config.getBoolean(key + ".enabled"))
            this.ores.put(id, new Ore(Language.getNP(key), smeltResult, this.config.getDouble(key + ".money")));
    }

    public static class NotificationType {
        public static final int NONE = -1, POPUP = 0, ACTIONBAR = 1, CHAT = 2;
    }
}
