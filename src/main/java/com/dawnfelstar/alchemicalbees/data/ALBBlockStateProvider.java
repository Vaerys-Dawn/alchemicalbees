package com.dawnfelstar.alchemicalbees.data;

import com.dawnfelstar.alchemicalbees.common.AlchemicalBees;
import com.dawnfelstar.alchemicalbees.common.abstraction.PipeBlock;
import com.dawnfelstar.alchemicalbees.common.abstraction.ALBBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ALBBlockStateProvider extends BlockStateProvider {

    private static final Logger LOGGER = LogManager.getLogger();

    public ALBBlockStateProvider(DataGenerator gen, ExistingFileHelper efh) {
        super(gen, AlchemicalBees.MOD_ID, efh);
    }

    @Override
    public void registerStatesAndModels() {
        ALBBlock.registeredBlocks.forEach((k, v) -> {
            if (v instanceof PipeBlock) {
                generatePipeTemplate(v);
            } else if (v.getModelPath() == null) {
                basicBlock(v);
            } else {
                modelBlock(v);
            }
        });
    }

    private void modelBlock(ALBBlock block) {
        ResourceLocation location = block.block.get().getRegistryName();
        ModelFile file = this.models()
                .withExistingParent(location.getPath(), new ResourceLocation(location.getNamespace(), "templates/" + block.getModelPath()))
                .texture("0", new ResourceLocation(location.getNamespace(), ALBItemModelProvider.BLOCK_FOLDER + "/" + location.getPath()))
                .texture("particle", new ResourceLocation(location.getNamespace(), ALBItemModelProvider.BLOCK_FOLDER + "/" + location.getPath()));
        if (block.isDirectional()) {
            this.directionalBlock(block.block.get(), file);
        } else {
            this.simpleBlock(block.block.get(), file);
        }
        if (block.hasImageItem()) {
            blockItem(block);
        } else {
            this.simpleBlockItem(block.block.get(), file);
        }
    }

    public void basicBlock(ALBBlock block) {
        ModelFile file = cubeAll(block.block.get());
        this.simpleBlock(block.block.get(), file);
        if (block.hasImageItem()) {
            blockItem(block);
        } else {
            this.simpleBlockItem(block.block.get(), file);
        }
    }

    public void blockItem(ALBBlock block) {
        ResourceLocation location = block.block.get().getRegistryName();
        this.itemModels().getBuilder(location.getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", new ResourceLocation(location.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + location.getPath()));
    }


    public void generatePipeTemplate(ALBBlock block) {
        String basePath = "templates/pipeparts/";
        MultiPartBlockStateBuilder builder = this.getMultipartBuilder(block);
        ModelFile partMidAny = generateModelPart(block, basePath, "pipe_mid_any");
        ModelFile partUp = generateModelPart(block, basePath, "pipe_up");
        ModelFile partDown = generateModelPart(block, basePath, "pipe_down");
        ModelFile partNorth = generateModelPart(block, basePath, "pipe_north");
        ModelFile partSouth = generateModelPart(block, basePath, "pipe_south");
        ModelFile partEast = generateModelPart(block, basePath, "pipe_east");
        ModelFile partWest = generateModelPart(block, basePath, "pipe_west");

        builder.part().modelFile(partMidAny).addModel();
        builder.part().modelFile(partUp).addModel().condition(PipeBlock.UP, true);
        builder.part().modelFile(partDown).addModel().condition(PipeBlock.DOWN, true);
        builder.part().modelFile(partNorth).addModel().condition(PipeBlock.NORTH, true);
        builder.part().modelFile(partSouth).addModel().condition(PipeBlock.SOUTH, true);
        builder.part().modelFile(partEast).addModel().condition(PipeBlock.EAST, true);
        builder.part().modelFile(partWest).addModel().condition(PipeBlock.WEST, true);
        if (block.hasImageItem()) {
            blockItem(block);
        } else {
            ResourceLocation location = block.block.get().getRegistryName();
            ModelFile file = this.models()
                    .withExistingParent(ModelProvider.BLOCK_FOLDER + "/" + location.getPath(), new ResourceLocation(location.getNamespace(), "templates/pipe_base_model"))
                    .texture("0", new ResourceLocation(location.getNamespace(), ALBItemModelProvider.BLOCK_FOLDER + "/" + location.getPath()))
                    .texture("particle", new ResourceLocation(location.getNamespace(), ALBItemModelProvider.BLOCK_FOLDER + "/" + location.getPath()));
        }
    }

    public ModelFile generateModelPart(ALBBlock block, String modelPath, String part) {
        ResourceLocation location = block.block.get().getRegistryName();
        return this.models()
                .withExistingParent(ModelProvider.BLOCK_FOLDER + "/" + location.getPath() + "/" + part, new ResourceLocation(location.getNamespace(), modelPath + part))
                .texture("0", new ResourceLocation(location.getNamespace(), ALBItemModelProvider.BLOCK_FOLDER + "/" + location.getPath()))
                .texture("particle", new ResourceLocation(location.getNamespace(), ALBItemModelProvider.BLOCK_FOLDER + "/" + location.getPath()));
    }
}
