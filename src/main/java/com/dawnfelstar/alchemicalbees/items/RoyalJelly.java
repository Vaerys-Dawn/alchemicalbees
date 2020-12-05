package com.dawnfelstar.alchemicalbees.items;

import com.dawnfelstar.alchemicalbees.common.abstraction.ALBItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class RoyalJelly extends ALBItem {

    @Override
    public void addTranslations(Map<String, String> translations) {
        translations.put("en_us", "Royal Jelly");
        translations.put("en_uk", "Royal Jelly");
    }

    @Override
    public void addProperties(Item.Properties properties) {
        properties.food(new Food.Builder().hunger(4).saturation(3).build());
    }
}
