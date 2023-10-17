package net.burningtnt.earth2fix.mixin.mixins;

import corgitaco.blockswap.BlockSwap;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.Features;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
    @Shadow public abstract boolean setBlock(BlockPos p_241211_1_, BlockState p_241211_2_, int p_241211_3_, int p_241211_4_);

    @Inject(
            method = "setBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void isIncompatibleBlock(BlockPos pos, BlockState state, int i, int flags, CallbackInfoReturnable<Boolean> cir) {
        if (Features.BLOCK_SWAP_NPE_FIX.isEnabled()) {
            if (BlockSwap.blockToBlockMap != null) {
                Block block = state.getBlock();
                if (block != null) {
                    if (BlockSwap.blockToBlockMap.containsKey(state.getBlock())) {
                        cir.setReturnValue(this.setBlock(pos, BlockSwap.remapState(state), i, flags));
                    }
                } else {
                    Earth2Fixes.getLogger().warn("net.minecraft.block.AbstractBlock.AbstractBlockState.getBlock is null! Block Swapper is disabled in order not to crash Minecraft.");
                }
            } else {
                Earth2Fixes.getLogger().warn("corgitaco.blockswap.BlockSwap.blockToBlockMap is null! Block Swapper is disabled in order not to crash Minecraft.");
            }
        }
    }
}
