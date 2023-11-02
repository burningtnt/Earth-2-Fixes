package net.burningtnt.earth2fix.mixin.mixins.minecraft;

import com.google.common.collect.Multimap;
import net.burningtnt.earth2fix.controller.Features;
import net.burningtnt.earth2fix.utils.CMEFixes;
import net.minecraft.client.GameSettings;
import net.minecraft.client.audio.*;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(value = SoundEngine.class)
public abstract class SoundEngineMixin {
    @Mutable
    @Shadow
    @Final
    private Map<ISound, ChannelManager.Entry> instanceToChannel;

    @Mutable
    @Shadow
    @Final
    private Multimap<SoundCategory, ISound> instanceBySource;

    @Mutable
    @Shadow
    @Final
    private List<ITickableSound> tickingSounds;

    @Mutable
    @Shadow
    @Final
    private Map<ISound, Integer> queuedSounds;

    @Mutable
    @Shadow
    @Final
    private Map<ISound, Integer> soundDeleteTime;

    @Mutable
    @Shadow
    @Final
    private List<ISoundEventListener> listeners;

    @Mutable
    @Shadow
    @Final
    private List<ITickableSound> queuedTickableSounds;

    @Mutable
    @Shadow
    @Final
    private List<Sound> preloadQueue;

    @Mutable
    @Shadow
    @Final
    private static Set<ResourceLocation> ONLY_WARN_ONCE;

    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "TAIL"
            )
    )
    private static void earth2fixes$fixStaticCME(CallbackInfo ci) {
        ONLY_WARN_ONCE = CMEFixes.ofNullableSet();
    }

    @Inject(
            method = "<init>",
            at = @At(
                    value = "TAIL"
            )
    )
    private void earth2fixes$fixInstanceCME(SoundHandler p_i50892_1_, GameSettings p_i50892_2_, IResourceManager p_i50892_3_, CallbackInfo ci) {
        if (!Features.SOUND_ENGINE_CME_FIX.isEnabled()) {
            return;
        }

        this.instanceToChannel = CMEFixes.ofNullableMap();
        this.instanceBySource = CMEFixes.ofNullableHashMultiMap();
        this.tickingSounds = CMEFixes.ofList();
        this.queuedSounds = CMEFixes.ofNullableMap();
        this.soundDeleteTime = CMEFixes.ofNullableMap();
        this.listeners = CMEFixes.ofList();
        this.queuedTickableSounds = CMEFixes.ofList();
        this.preloadQueue = CMEFixes.ofList();
    }
}
