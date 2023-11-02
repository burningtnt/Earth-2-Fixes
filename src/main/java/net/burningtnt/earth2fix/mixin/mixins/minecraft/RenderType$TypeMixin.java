package net.burningtnt.earth2fix.mixin.mixins.minecraft;

import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderType.Type.class)
public class RenderType$TypeMixin {
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    @Redirect(
            method = "memoize",
            at = @At(
                    value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/objects/ObjectOpenCustomHashSet;addOrGet(Ljava/lang/Object;)Ljava/lang/Object;"
            ),
            remap = false
    )
    private static Object earth2fixes$synchronizedAddOrGet(ObjectOpenCustomHashSet<RenderType.Type> instance, Object key) {
        if (Features.RENDER_TYPE_CME_FIX.isEnabled()) {
            synchronized (instance) {
                return instance.addOrGet((RenderType.Type) key);
            }
        } else {
            return instance.addOrGet((RenderType.Type) key);
        }
    }
}
