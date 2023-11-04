package net.burningtnt.earth2fix.mixin.mixins.mods.musictrigger;

import mods.thecomputerizer.musictriggers.client.EventsClient;
import mods.thecomputerizer.musictriggers.client.MusicPicker;
import net.burningtnt.earth2fix.Logging;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EventsClient.class)
public class EventsClientMixin {
    @Inject(
            method = "clientDisconnected",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private static void earth2fixes$fixNPE(PlayerEvent.PlayerLoggedOutEvent e, CallbackInfo ci) {
        if (Features.MUSIC_TRIGGER_CLIENT_DISCONNECTED_NPE_FIX.isEnabled() && MusicPicker.mc == null) {
            Logging.getLogger().warn("MusicTriggers is blocked to fire event 'clientDisconnected' because MusicPicker.mc is null.");
            ci.cancel();
        }
    }
}
