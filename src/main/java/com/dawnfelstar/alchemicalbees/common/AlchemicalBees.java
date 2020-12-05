package com.dawnfelstar.alchemicalbees.common;

import com.dawnfelstar.alchemicalbees.client.proxy.ClientProxy;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBBlock;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBItems;
import com.dawnfelstar.alchemicalbees.common.proxy.IProxy;
import com.dawnfelstar.alchemicalbees.data.ALBBlockStateProvider;
import com.dawnfelstar.alchemicalbees.data.ALBItemModelProvider;
import com.dawnfelstar.alchemicalbees.data.ALBLanguageProvider;
import com.dawnfelstar.alchemicalbees.server.proxy.ServerProxy;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(AlchemicalBees.MODID)
public class AlchemicalBees {
    // Directly reference a log4j logger.

    public static final String MODID = "alchemicalbees";
    public static ItemGroup ITEM_GROUP;
    private static final Logger LOGGER = LogManager.getLogger();

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    private static final String[] LOCALES = new String[]{
            "en_us",
            "en_uk"
    };

    public AlchemicalBees() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forge = MinecraftForge.EVENT_BUS;

        PROXY.setup(mod, forge);
        addRegistries(mod);
        mod.addListener(this::commonSetup);
        mod.addListener(this::gatherData);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addRegistries(IEventBus mod) {
        ALBBlock.generateBlocks();
        ITEM_GROUP = new ItemGroup("alchemical_bees") {
            @Override
            @OnlyIn(Dist.CLIENT)
            public ItemStack createIcon() {
                return new ItemStack(ALBItems.ROYAL_JELLY.get());
            }
        };
        ALBItems.ITEMS.register(mod);
        ALBBlock.BLOCKS.register(mod);
    }

    private void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            ExistingFileHelper efh = event.getExistingFileHelper();

            gen.addProvider(new ALBItemModelProvider(gen, efh));
            gen.addProvider(new ALBBlockStateProvider(gen, efh));
            addLanguageProviders(gen);
        }
    }

    private void addLanguageProviders(final DataGenerator gen) {
        for (String locale : LOCALES) {
            gen.addProvider(new ALBLanguageProvider(gen, locale));
        }
    }
}
