package net.burningtnt.earth2fix.mixin.mixins.accessors;

import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.SoundSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChannelManager.Entry.class)
public interface ChannelManager$EntryAccessor {
    @Accessor
    SoundSource getChannel();
}
