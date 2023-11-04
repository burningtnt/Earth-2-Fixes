package net.burningtnt.earth2fix.mixin.mixins.mods.losttrinkets;

import net.burningtnt.earth2fix.Logging;
import net.burningtnt.earth2fix.controller.Features;
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
            Logging.getLogger().info("LostTrinkets is prevented to unlock FARM_HARVEST on player");
            ci.cancel();
        }
    }
}
