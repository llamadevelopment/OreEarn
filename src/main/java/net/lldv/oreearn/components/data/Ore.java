package net.lldv.oreearn.components.data;

import cn.nukkit.item.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author LlamaDevelopment
 * @project OreEarn
 * @website http://llamadevelopment.net/
 */
@Getter
@RequiredArgsConstructor
public class Ore {

    private final String name;
    private final Item smeltResult;
    private final double money;

}
