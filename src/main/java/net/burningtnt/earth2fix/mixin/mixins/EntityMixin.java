package net.burningtnt.earth2fix.mixin.mixins;

import com.google.common.base.MoreObjects;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.switcher.Features;
import net.minecraft.block.BlockState;
import net.minecraft.block.PortalInfo;
import net.minecraft.block.PortalSize;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public final class EntityMixin {
    @SuppressWarnings("target")
    @Redirect(
            method = "lambda$findDimensionEntryPoint$5(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/TeleportationRepositioner$Result;)Lnet/minecraft/block/PortalInfo;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/PortalSize;createPortalInfo(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/util/TeleportationRepositioner$Result;Lnet/minecraft/util/Direction$Axis;Lnet/minecraft/util/math/vector/Vector3d;Lnet/minecraft/entity/EntitySize;Lnet/minecraft/util/math/vector/Vector3d;FF)Lnet/minecraft/block/PortalInfo;"
            )
    )
    private PortalInfo earch2fixes$fixInvalidBlockState(ServerWorld serverWorld, TeleportationRepositioner.Result result, Direction.Axis axis, Vector3d vector3d1, EntitySize entitySize, Vector3d vector3d2, float f1, float f2) {
        if (Features.NETHER_PORTAL_FIX.isEnabled()) {
            BlockState blockState = serverWorld.getBlockState(result.minCorner);
            if (!blockState.hasProperty(BlockStateProperties.HORIZONTAL_AXIS)) {
                Earth2Fixes.getLogger().info(String.format(
                        "Portal at {%s} is invalid because it's a %s in level %s. A new portal is generated in order not to crash Minecrarft.",
                        MoreObjects.toStringHelper(result).add("minCorner", result.minCorner).add("", result.axis1Size).add("", result.axis2Size),
                        blockState,
                        serverWorld
                ));
                return null;
            }
        }

        return PortalSize.createPortalInfo(serverWorld, result, axis, vector3d1, entitySize, vector3d2, f1, f2);
    }
}
