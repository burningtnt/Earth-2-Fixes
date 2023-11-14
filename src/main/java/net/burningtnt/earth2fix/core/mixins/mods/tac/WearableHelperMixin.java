package net.burningtnt.earth2fix.core.mixins.mods.tac;

import com.tac.guns.util.WearableHelper;
import net.burningtnt.earth2fix.Logging;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WearableHelper.class)
public class WearableHelperMixin {
    @Inject(
            method = "isFullDurability",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private static void earth2fixes$redirectGetTag(ItemStack rig, CallbackInfoReturnable<Boolean> cir) {
        if (Features.DURABILITY_NPE_FIX.isEnabled() && rig.getTag() == null) {
            Logging.getLogger().info("ItemStack reg.getTag is null! True is returned in order not to cause NPE.");
            cir.setReturnValue(true);
        }
    }
}
