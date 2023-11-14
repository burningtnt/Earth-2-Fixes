package net.burningtnt.earth2fix.core.mixins.mods.tac;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.tac.guns.client.BulletTrail;
import com.tac.guns.client.handler.BulletTrailRenderingHandler;
import net.burningtnt.earth2fix.Logging;
import net.burningtnt.earth2fix.controller.Features;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BulletTrailRenderingHandler.class)
public abstract class BulletTrailRenderingHandlerMixin {
    @Shadow(remap = false)
    protected abstract void renderBulletTrail(BulletTrail bulletTrail, MatrixStack matrixStack, float partialTicks);

    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tac/guns/client/handler/BulletTrailRenderingHandler;renderBulletTrail(Lcom/tac/guns/client/BulletTrail;Lcom/mojang/blaze3d/matrix/MatrixStack;F)V"
            ),
            remap = false
    )
    private void earth2fixes$safeRender(BulletTrailRenderingHandler instance, BulletTrail builder, MatrixStack posSize, float bulletType) {
        if (Features.BULLET_RENDER_FIX.isEnabled()) {
            try {
                ((BulletTrailRenderingHandlerMixin) (Object) instance).renderBulletTrail(builder, posSize, bulletType);
            } catch (Throwable t) {
                Logging.getLogger().warn("Cannot render bullets.", t);
            }
        } else {
            ((BulletTrailRenderingHandlerMixin) (Object) instance).renderBulletTrail(builder, posSize, bulletType);
        }
    }
}
