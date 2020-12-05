package com.dawnfelstar.alchemicalbees.data;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBBlock;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ALBLanguageProvider extends LanguageProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public ALBLanguageProvider(DataGenerator gen, String locale) {
        super(gen, AlchemicalBees.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        LOGGER.debug("Running data gen #4");
        String locale = this.getName().replace("Languages: ", "");
        ALBItem.registeredItems.forEach((k, v) -> {
            String translation = v.translations.get(locale);
            if (translation != null) {
                addItem(v.item, translation);
            }
        });
        ALBBlock.registeredBlocks.forEach((k, v) -> {
            String translation = v.translations.get(locale);
            if (translation != null) {
                addBlock(v.block, translation);
            }
        });
    }
}
