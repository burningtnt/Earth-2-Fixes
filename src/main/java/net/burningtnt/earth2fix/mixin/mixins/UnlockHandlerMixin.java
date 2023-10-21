package net.burningtnt.earth2fix.mixin.mixins;

import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.switcher.Features;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.BonemealEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import owmii.losttrinkets.handler.UnlockHandler;

@Mixin(UnlockHandler.class)
public class UnlockHandlerMixin {
    @Inject(
            method = "bonemeal",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private static void earth2fixes$fixNPE(BonemealEvent event, CallbackInfo ci) {
        if (!Features.UNLOCK_HANDLER_NPE_FIX.isEnabled()) {
            return;
        }

        PlayerEntity playerEntity = event.getPlayer();
        if (playerEntity == null || playerEntity.level == null) {
            Earth2Fixes.getLogger().info("LostTrinkets is prevented to unlock FARM_HARVEST on player");
            ci.cancel();
        }
    }
}
