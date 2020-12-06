package com.dawnfelstar.alchemicalbees.common.abstraction;

import com.dawnfelstar.alchemicalbees.common.AlchemicaIItemsGroup;
import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.items.AThing;
import com.dawnfelstar.alchemicalbees.items.FungalSpore;
import com.dawnfelstar.alchemicalbees.items.RoyalJelly;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.item.Foods;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ALBItems {

    public static final DeferredRegister<Item> SIMPLE_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AlchemicalBees.MOD_ID);

    public static final Registrate REGISTRATE = Registrate.create(AlchemicalBees.MOD_ID).itemGroup(AlchemicaIItemsGroup::new, "Alchemical Bees");

    public static final ItemEntry<Item> CRIMSON_SPORE = REGISTRATE.object("crimson_spore")
            .item(Item::new)
            .lang("Crimson Spore")
            .defaultModel()
            .register();
    public static final ItemEntry<Item> WARPED_SPORE = REGISTRATE.object("warped_spore")
            .item(Item::new)
            .lang("Warped Spore")
            .defaultModel()
            .register();
    public static final ItemEntry<RoyalJelly> ROYAL_JELLY = REGISTRATE.object("royal_jelly")
            .item(RoyalJelly::new)
            .properties(properties -> properties.containerItem(Items.GLASS_BOTTLE).food(Foods.HONEY).maxStackSize(16))
            .lang("Royal Jelly")
            .defaultModel()
            .register();
    public static final ItemEntry<AThing> A_THING = REGISTRATE.object("a_thing")
            .item(AThing::new)
            .properties(properties -> properties.maxStackSize(1))
            .lang("A Thing")
            .defaultModel()
            .register();
}