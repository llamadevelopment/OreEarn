package net.lldv.oreearn.components.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Sound;
import net.lldv.llamaeconomy.LlamaEconomy;
import net.lldv.oreearn.OreEarn;
import net.lldv.oreearn.components.data.Ore;
import net.lldv.oreearn.components.language.Language;

import java.util.concurrent.CompletableFuture;

/**
 * @author LlamaDevelopment
 * @project OreEarn
 * @website http://llamadevelopment.net/
 */
public class EventListener implements Listener {

    private final OreEarn plugin;

    public EventListener(final OreEarn plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(final BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (event.getPlayer().getGamemode() == 1) return;

        if (this.plugin.getOres().containsKey(event.getBlock().getId())) {

            final Ore ore = this.plugin.getOres().get(event.getBlock().getId());

            if (this.plugin.isCheckWorld())
                if (!this.plugin.getWorlds().contains(event.getPlayer().getLevel().getName()))
                    return;
            if (this.plugin.isIgnoreSilktouch())
                if (event.getItem().getEnchantment(Enchantment.ID_SILK_TOUCH) != null)
                    return;

            if (this.plugin.isAutoSmelt())
                if (ore.getSmeltResult() != null) {
                    event.setDrops(new Item[]{ore.getSmeltResult()});
                    event.getPlayer().getLevel().addSound(event.getBlock().getLocation(), Sound.RANDOM_FIZZ);
                }

            if (!this.plugin.isDrops()) {
                event.setDrops(null);
                event.setDropExp(0);
            }

            LlamaEconomy.getAPI().addMoney(event.getPlayer(), ore.getMoney());

            final String message = Language.getNP("notification",
                    ore.getName(),
                    LlamaEconomy.getAPI().getMonetaryUnit(),
                    LlamaEconomy.getAPI().getMoneyFormat().format(ore.getMoney())
            );

            switch (this.plugin.getNotificationType()) {
                case OreEarn.NotificationType.CHAT:
                    event.getPlayer().sendMessage(message);
                    break;
                case OreEarn.NotificationType.POPUP:
                    event.getPlayer().sendPopup(message);
                    break;
                case OreEarn.NotificationType.ACTIONBAR:
                    event.getPlayer().sendActionBar(message);
                    break;
                default:
                    break;
            }
        }
    }

}
