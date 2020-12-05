package com.dawnfelstar.alchemicalbees.items;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import net.minecraft.item.*;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class RoyalJelly extends HoneyBottleItem {

    public RoyalJelly() {
        super(new Item.Properties().containerItem(Items.GLASS_BOTTLE).food(Foods.HONEY).group(AlchemicalBees.ITEM_GROUP).maxStackSize(16));
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
