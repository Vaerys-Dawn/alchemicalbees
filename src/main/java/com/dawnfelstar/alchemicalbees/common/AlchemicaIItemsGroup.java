package com.dawnfelstar.alchemicalbees.common;

import com.dawnfelstar.alchemicalbees.common.abstraction.ALBItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AlchemicaIItemsGroup extends ItemGroup {

    public AlchemicaIItemsGroup() {
        super("alchemical_bees");
    }

    @Override
    public ItemStack createIcon() {
        return ALBItems.ROYAL_JELLY.asStack();
    }
}
