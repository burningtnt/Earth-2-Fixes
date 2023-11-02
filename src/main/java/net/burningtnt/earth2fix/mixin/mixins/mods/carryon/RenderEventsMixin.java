package net.burningtnt.earth2fix.mixin.mixins.mods.carryon;

import com.google.common.collect.ImmutableMap;
import net.burningtnt.earth2fix.controller.Features;
import net.burningtnt.earth2fix.utils.ConflictableImmutableMapBuilder;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tschipp.carryon.client.event.RenderEvents;

@Mixin(RenderEvents.class)
public final class RenderEventsMixin {
    @Redirect(
            method = "onRenderWorld",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"
            ),
            remap = false
    )
    private ImmutableMap<RenderType, BufferBuilder> earch2fixes$fixConflict(ImmutableMap.Builder<RenderType, BufferBuilder> instance) {
        if (Features.CARRY_ON_CONFLICT_FIX.isEnabled()) {
            return ConflictableImmutableMapBuilder.safeBuild(instance);
        } else {
            return instance.build();
        }
    }
}
