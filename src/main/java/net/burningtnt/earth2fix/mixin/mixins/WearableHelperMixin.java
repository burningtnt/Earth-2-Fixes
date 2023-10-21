package net.burningtnt.earth2fix.mixin.mixins;

import com.tac.guns.util.WearableHelper;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.switcher.Features;
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
            Earth2Fixes.getLogger().info("ItemStack reg.getTag is null! True is returned in order not to cause NPE.");
            cir.setReturnValue(true);
        }
    }
}
