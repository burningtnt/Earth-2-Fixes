package net.burningtnt.earth2fix.mixin.mixins;

import ladysnake.illuminations.client.IlluminationsClient;
import net.burningtnt.earth2fix.Features;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiPredicate;

@Mixin(IlluminationsClient.class)
public class IlluminationsClientMixin {
    @Mutable
    @Shadow(remap = false)
    @Final
    public static BiPredicate<World, BlockPos> EYES_LOCATION_PREDICATE;

    @Shadow(remap = false)
    public static RegistryObject<BasicParticleType> EYES;

    @Inject(
            method = "<clinit>",
            at = @At(
                    value = "TAIL"
            )
    )
    private static void earth2fixes$fixIllunimationsClient(CallbackInfo ci) {
        if (Features.ILLUMINATIONS_FIX.isEnabled()) {
            BiPredicate<World, BlockPos> raw = EYES_LOCATION_PREDICATE;
            EYES_LOCATION_PREDICATE = (world, blockPos) -> EYES.isPresent() && raw.test(world, blockPos);
        }
    }
}
