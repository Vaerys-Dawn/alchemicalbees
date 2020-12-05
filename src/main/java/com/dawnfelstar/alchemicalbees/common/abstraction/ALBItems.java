package com.dawnfelstar.alchemicalbees.common.abstraction;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.items.RoyalJelly;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ALBItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AlchemicalBees.MODID);

    public static final RegistryObject<Item> ROYAL_JELLY = ITEMS.register("royal_jelly", () -> new RoyalJelly());
}