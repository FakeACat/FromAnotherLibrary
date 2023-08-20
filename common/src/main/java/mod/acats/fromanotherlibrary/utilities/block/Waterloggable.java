package mod.acats.fromanotherlibrary.utilities.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

/**
 * Interface to reduce boilerplate code for waterloggable blocks
 */
public interface Waterloggable {

    /**
     * Gets the BooleanProperty that controls whether the block is waterlogged
     * @return the BooleanProperty that controls whether the block is waterlogged
     */
    BooleanProperty getWaterloggedProperty();

    /**
     * Is the block waterlogged?
     * @param state The BlockState in question
     * @return True if the block is waterlogged, false if it is not
     */
    default boolean isWaterlogged(BlockState state) {
        return state.getValue(this.getWaterloggedProperty());
    }

    /**
     * Sets whether the block is waterlogged
     * @param state The BlockState in question
     * @param bl True to waterlog the block, false to remove water from the block
     * @return The updated BlockState
     */
    default BlockState setWaterlogged(BlockState state, boolean bl) {
        return state.setValue(this.getWaterloggedProperty(), bl);
    }

    /**
     * Call this in neighborChanged
     * @param blockState The BlockState provided in neighborChanged
     * @param level The level provided in neighborChanged
     * @param blockPos The BlockPos provided in neighborChanged
     */
    default void waterloggedNeighbourChanged(BlockState blockState, Level level, BlockPos blockPos) {
        if (!level.isClientSide() && this.isWaterlogged(blockState)) {
            level.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
    }

    /**
     * Return this in getStateForPlacement
     * @param blockState The BlockState provided in getStateForPlacement
     * @param blockPlaceContext The BlockPlaceContext provided in getStateForPlacement
     * @return The BlockState to return in getStateForPlacement
     */
    default BlockState waterloggedStateForPlacement(BlockState blockState, BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        return this.setWaterlogged(blockState, fluidState.getType() == Fluids.WATER);
    }

    /**
     * Return this in getFluidState
     * @param blockState The BlockState provided in getFluidState
     * @return The FluidState to return in getFluidState
     */
    default FluidState waterloggedFluidState(BlockState blockState) {
        return this.isWaterlogged(blockState) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    /**
     * Call this in updateShape
     * @param blockState The BlockState provided in updateShape
     * @param levelAccessor The LevelAccessor provided in updateShape
     * @param blockPos The BlockPos provided in updateShape
     */
    default void waterloggedUpdateShape(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos) {
        if (this.isWaterlogged(blockState)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
    }
}
