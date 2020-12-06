package com.dawnfelstar.alchemicalbees.items;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import net.minecraft.item.*;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class RoyalJelly extends HoneyBottleItem {

    public RoyalJelly(Properties properties) {
        super(properties);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }
}
