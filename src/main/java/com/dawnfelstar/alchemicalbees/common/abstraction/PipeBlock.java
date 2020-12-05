package com.dawnfelstar.alchemicalbees.common.abstraction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/***
 *  Credit to Drakmyth for helping with the layout
 */

public abstract class PipeBlock extends ALBBlock {

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape VOXEL_SHAPE_CENTER = Block.makeCuboidShape(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);
    protected static final VoxelShape VOXEL_SHAPE_NORTH = Block.makeCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 7.0D);
    protected static final VoxelShape VOXEL_SHAPE_EAST = Block.makeCuboidShape(9.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D);
    protected static final VoxelShape VOXEL_SHAPE_SOUTH = Block.makeCuboidShape(5.0D, 5.0D, 9.0D, 11.0D, 11.0D, 16.0D);
    protected static final VoxelShape VOXEL_SHAPE_WEST = Block.makeCuboidShape(0.0D, 5.0D, 5.0D, 7.0D, 11.0D, 11.0D);
    protected static final VoxelShape VOXEL_SHAPE_UP = Block.makeCuboidShape(5.0D, 9.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    protected static final VoxelShape VOXEL_SHAPE_DOWN = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 5.0D, 11.0D);

    public PipeBlock(Material material) {
        super(material);

        BlockState defaultState = this.stateContainer.getBaseState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(UP, false)
                .with(DOWN, false)
                .with(WATERLOGGED, false);
        this.setDefaultState(defaultState);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos northPos = context.getPos().north();
        BlockPos eastPos = context.getPos().east();
        BlockPos southPos = context.getPos().south();
        BlockPos westPos = context.getPos().west();
        BlockPos upPos = context.getPos().up();
        BlockPos downPos = context.getPos().down();
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState()
                .with(NORTH, canConnect(world.getBlockState(northPos), northPos, world, Direction.SOUTH))
                .with(EAST, canConnect(world.getBlockState(eastPos), eastPos, world, Direction.WEST))
                .with(SOUTH, canConnect(world.getBlockState(southPos), southPos, world, Direction.NORTH))
                .with(WEST, canConnect(world.getBlockState(westPos), westPos, world, Direction.EAST))
                .with(UP, canConnect(world.getBlockState(upPos), upPos, world, Direction.DOWN))
                .with(DOWN, canConnect(world.getBlockState(downPos), downPos, world, Direction.UP))
                .with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    private boolean canConnect(BlockState state, BlockPos pos, IWorld world, Direction dir) {
        Block block = state.getBlock();
        if (!(block instanceof PipeBlock)) return false;
        return ((PipeBlock) block).canConnectToFace(state, pos, world, dir);
    }

    public boolean canConnectToFace(BlockState state, BlockPos pos, IWorld world, Direction dir) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, WATERLOGGED);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        List<VoxelShape> shapes = new ArrayList<>();
        if (state.get(NORTH)) {
            shapes.add(VOXEL_SHAPE_NORTH);
        }
        if (state.get(EAST)) {
            shapes.add(VOXEL_SHAPE_EAST);
        }
        if (state.get(SOUTH)) {
            shapes.add(VOXEL_SHAPE_SOUTH);
        }
        if (state.get(WEST)) {
            shapes.add(VOXEL_SHAPE_WEST);
        }
        if (state.get(UP)) {
            shapes.add(VOXEL_SHAPE_UP);
        }
        if (state.get(DOWN)) {
            shapes.add(VOXEL_SHAPE_DOWN);
        }
        return VoxelShapes.or(VOXEL_SHAPE_CENTER, shapes.toArray(new VoxelShape[0]));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        Direction oppositeFacing = facing.getOpposite();
        boolean canConnect = canConnect(facingState, facingPos, world, oppositeFacing);
        switch (facing) {
            case NORTH:
                return stateIn.with(NORTH, canConnect);
            case EAST:
                return stateIn.with(EAST, canConnect);
            case SOUTH:
                return stateIn.with(SOUTH, canConnect);
            case WEST:
                return stateIn.with(WEST, canConnect);
            case UP:
                return stateIn.with(UP, canConnect);
            case DOWN:
                return stateIn.with(DOWN, canConnect);
            default:
                return stateIn;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (world.isRemote()) return;
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (world.isRemote()) return;
    }
}
