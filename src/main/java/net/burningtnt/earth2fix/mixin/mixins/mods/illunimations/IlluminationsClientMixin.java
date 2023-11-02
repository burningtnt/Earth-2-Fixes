package net.burningtnt.earth2fix.mixin.mixins.mods.illunimations;

import ladysnake.illuminations.client.IlluminationsClient;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IlluminationsClient.class)
public class IlluminationsClientMixin {
    @Shadow(remap = false)
    public static RegistryObject<BasicParticleType> EYES;

    @Inject(
            method = "lambda$static$3",
            at = @At(
                    value = "HEAD"
            ),
            remap = false,
            cancellable = true
    )
    private static void earth2fixes$fixIllunimationsClient(World world, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        if (Features.ILLUMINATIONS_FIX.isEnabled() && !EYES.isPresent()) {
            cir.setReturnValue(false);
        }
    }
}
