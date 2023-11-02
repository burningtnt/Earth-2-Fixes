package net.burningtnt.earth2fix.mixin.mixins.mods.musictrigger;

import mods.thecomputerizer.musictriggers.client.MusicPlayer;
import mods.thecomputerizer.musictriggers.util.audio.SetVolumeSound;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.mixin.mixins.accessors.ChannelManager$EntryAccessor;
import net.burningtnt.earth2fix.mixin.mixins.accessors.SoundEngineAccessor;
import net.burningtnt.earth2fix.mixin.mixins.accessors.SoundHandlerAccessor;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundEngine;
import net.minecraft.client.audio.SoundHandler;
import net.minecraftforge.event.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(MusicPlayer.class)
public class MusicPlayerMixin {
    @Shadow(remap = false)
    public static SoundHandler sh;

    @Shadow(remap = false)
    public static SetVolumeSound curMusic;

    @Inject(
            method = "onTick",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private static void earth2fixes$fixNPE(TickEvent.ClientTickEvent event, CallbackInfo ci) {
        if (!Features.MUSIC_PLAYER_NPE_FIX.isEnabled()) {
            return;
        }

        if (sh == null) {
            Earth2Fixes.getLogger().warn("MusicPlayer is disabled because MusicPlayer.sh is null.");
            ci.cancel();
            return;
        }
        SoundEngine soundEngine = ((SoundHandlerAccessor) sh).getSoundEngine();
        if (soundEngine == null) {
            Earth2Fixes.getLogger().warn("MusicPlayer is disabled because MusicPlayer.sh.soundEngine is null.");
            ci.cancel();
            return;
        }
        Map<ISound, ChannelManager.Entry> map = ((SoundEngineAccessor) soundEngine).getInstanceToChannel();
        if (map == null) {
            Earth2Fixes.getLogger().warn("MusicPlayer is disabled because MusicPlayer.sh.soundEngine.instanceToChannel is null.");
            ci.cancel();
            return;
        }
        ChannelManager.Entry entry = map.get(curMusic);
        if (entry == null) {
            Earth2Fixes.getLogger().warn("MusicPlayer is disabled because MusicPlayer.sh.soundEngine.instanceToChannel.get(curMusic) is null.");
            ci.cancel();
            return;
        }
        if (((ChannelManager$EntryAccessor) entry).getChannel() == null) {
            Earth2Fixes.getLogger().warn("MusicPlayer is disabled because MusicPlayer.sh.soundEngine.instanceToChannel.get(curMusic).channel is null.");
            ci.cancel();
        }
    }
}
