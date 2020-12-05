package com.dawnfelstar.alchemicalbees.common.abstraction;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.items.RoyalJelly;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.core.util.KeyValuePair;

import java.util.HashMap;
import java.util.Map;

public abstract class ALBItem {

    public boolean isBlockItem = false;

    public static void generateItems() {
        registerItem("royal_jelly", new RoyalJelly());
    }

    public static void addToTab() {
        registeredItems.forEach((k, v) -> {
            v.properties.group(AlchemicalBees.ITEM_GROUP);
        });
    }

    public abstract void addTranslations(Map<String, String> translations);

    public abstract void addProperties(Item.Properties properties);

    public void setID(String id) {
        this.itemID = id;
    }

    public String itemID;
    public Map<String, String> translations = new HashMap<>();
    public RegistryObject<Item> item;
    public Item.Properties properties = new Item.Properties();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AlchemicalBees.MODID);
    public static Map<String, ALBItem> registeredItems = new HashMap<>();

    public static void registerItem(String id, ALBItem item) {
        // set item id
        item.setID(id);
        // add item properties
        item.addProperties(item.properties);
        // add translations
        item.addTranslations(item.translations);
        // register item
        item.item = ITEMS.register(id, () -> new Item(item.properties));

        registeredItems.put(id, item);
    }
}