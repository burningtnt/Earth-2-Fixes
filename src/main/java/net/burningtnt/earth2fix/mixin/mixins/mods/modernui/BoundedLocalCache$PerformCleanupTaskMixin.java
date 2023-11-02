package net.burningtnt.earth2fix.mixin.mixins.mods.modernui;

import net.burningtnt.earth2fix.controller.Features;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "icyllis/caffeine/cache/BoundedLocalCache$PerformCleanupTask")
public class BoundedLocalCache$PerformCleanupTaskMixin {
    @Inject(
            method = "run",
            at = @At(
                    value = "HEAD"
            ),
            remap = false
    )
    private void earth2fixes$fixInvalidClassLoader(CallbackInfo ci) {
        if (Features.MODERN_UI_INVALID_CLASS_LOADER_FIX.isEnabled()) {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
        }
    }
}
