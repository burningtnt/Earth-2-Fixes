package net.burningtnt.earth2fix.mixin.mixins.minecraft;

import com.mojang.datafixers.DataFixer;
import net.burningtnt.earth2fix.controller.Features;
import net.burningtnt.earth2fix.utils.ConcurrentCollections;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.SaveFormat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(TemplateManager.class)
public final class TemplateManagerMixin {
    @Shadow
    @Final
    @Mutable
    private Map<ResourceLocation, Template> structureRepository;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "RETURN"
            )
    )
    private void earth2fixes$fixCME(IResourceManager p_i232119_1_, SaveFormat.LevelSave p_i232119_2_, DataFixer p_i232119_3_, CallbackInfo ci) {
        if (Features.TEMPLATE_MANAGER_CME_FIX.isEnabled()) {
            this.structureRepository = ConcurrentCollections.ofNullableMap();
        }
    }
}
