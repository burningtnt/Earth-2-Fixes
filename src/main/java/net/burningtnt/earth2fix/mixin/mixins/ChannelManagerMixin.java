package net.burningtnt.earth2fix.mixin.mixins;

import net.burningtnt.earth2fix.Features;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@Mixin(ChannelManager.class)
public abstract class ChannelManagerMixin {
    @Shadow
    @Final
    @Mutable
    private Set<ChannelManager.Entry> channels;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "TAIL"
            )
    )
    private void earth2fixes$wrapChannelsAsSynchronizedSet(SoundSystem p_i50894_1_, Executor p_i50894_2_, CallbackInfo ci) {
        if (Features.CHANNEL_MANAGER_CME_FIX.isEnabled()) {
            this.channels = Collections.newSetFromMap(new ConcurrentHashMap<>());
        }
    }

    @Inject(
            method = "clear",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void earth2fixes$wrapClearAsSynchronized(CallbackInfo ci) {
        if (Features.CHANNEL_MANAGER_CME_FIX.isEnabled()) {
            for (ChannelManager.Entry entry : this.channels) {
                if (this.channels.remove(entry)) {
                    entry.release();
                }
            }

            ci.cancel();
        }
    }
}
