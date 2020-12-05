package com.dawnfelstar.alchemicalbees.common.abstraction;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public abstract class ALBBlock extends Block {

    public static void generateBlocks() {

    }

    public ALBBlock(Material material) {
        super(AbstractBlock.Properties.create(material));
    }

    public ALBBlock(Material material, MaterialColor materialColor) {
        super(AbstractBlock.Properties.create(material, materialColor));
    }

    public ALBBlock(Material material, DyeColor dyeColor) {
        super(AbstractBlock.Properties.create(material, dyeColor));
    }

    public String getModelPath() {
        return null;
    }

    public boolean hasImageItem() {
        return false;
    }

    public abstract void addProperties(AbstractBlock.Properties properties);

    public abstract void addTranslations(Map<String, String> translations);

    public abstract void addItemProperties(Item.Properties properties);

    public void setID(String id) {
        this.itemID = id;
    }

    public String itemID;
    public RegistryObject<Block> block;
    public RegistryObject<BlockItem> item;
    public Map<String, String> translations = new HashMap<>();
    public Item.Properties itemProperties = new Item.Properties();
    public ALBItems swcItem;

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AlchemicalBees.MODID);
    public static Map<String, ALBBlock> registeredBlocks = new HashMap<>();

    public static void registerBlock(String id, ALBBlock block) {
        // set id
        block.setID(id);

        // add translations
        block.addTranslations(block.translations);

        // add properties
        block.addItemProperties(block.itemProperties);
        BlockItem blockItem = new BlockItem(block, block.itemProperties);

        block.addProperties(block.field_235684_aB_);
        block.block = BLOCKS.register(id, () -> block);
        block.item = ALBItems.ITEMS.register(id, () -> blockItem);
        // register in dictionaries
        registeredBlocks.put(id, block);
    }

    public boolean isDirectional() {
        return true;
    }
}
