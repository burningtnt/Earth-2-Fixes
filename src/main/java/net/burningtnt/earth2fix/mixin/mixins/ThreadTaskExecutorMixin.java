package net.burningtnt.earth2fix.mixin.mixins;

import net.burningtnt.earth2fix.switcher.Features;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ThreadTaskExecutor.class)
public class ThreadTaskExecutorMixin {
    @Inject(
            method = "waitForTasks",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void earth2fixes$safeManagedBlock(CallbackInfo ci) {
        if (Features.CAS_PARKING.isEnabled()) {
            ci.cancel();
        }
    }
}
