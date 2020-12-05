package com.dawnfelstar.alchemicalbees.data;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class ALBItemModelProvider extends ItemModelProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public ALBItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, AlchemicalBees.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ALBItems.ROYAL_JELLY);
    }

    public void simpleItem(Supplier<? extends Item> itemSupplier) {
        ResourceLocation location = itemSupplier.get().getRegistryName();
        this.getBuilder(location.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(location.getNamespace(), ITEM_FOLDER + "/" + location.getPath()));
    }
}
