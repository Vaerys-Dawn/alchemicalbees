package com.dawnfelstar.alchemicalbees.data;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBBlock;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBItems;
import com.dawnfelstar.alchemicalbees.items.AThing;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ALBLanguageProvider extends LanguageProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public ALBLanguageProvider(DataGenerator gen, String locale) {
        super(gen, AlchemicalBees.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        String locale = this.getName().replace("Languages: ", "");
//        ALBItems.registeredItems.forEach((k, v) -> {
//            String translation = v.translations.get(locale);
//            if (translation != null) {
//                addItem(v.item, translation);
//            }
//        });
//        addItem(ALBItems.A_THING, "AAaaAa");
        ALBBlock.registeredBlocks.forEach((k, v) -> {
            String translation = v.translations.get(locale);
            if (translation != null) {
                addBlock(v.block, translation);
            }
        });
    }
}
