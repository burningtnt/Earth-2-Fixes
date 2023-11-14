package net.burningtnt.earth2fix.core.mixins.accessors;

import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(SoundEngine.class)
public interface SoundEngineAccessor {
    @Accessor
    Map<ISound, ChannelManager.Entry> getInstanceToChannel();
}
