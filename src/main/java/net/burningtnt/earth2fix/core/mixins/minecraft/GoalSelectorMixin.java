package net.burningtnt.earth2fix.core.mixins.minecraft;

import net.burningtnt.earth2fix.utils.ConcurrentCollections;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.profiler.IProfiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;
import java.util.function.Supplier;

@Mixin(GoalSelector.class)
public class GoalSelectorMixin {
    @Shadow
    @Final
    @Mutable
    private Set<PrioritizedGoal> availableGoals;

    @Inject(
            method = "<init>",
            at = @At(
                    value = "RETURN"
            )
    )
    private void earth2fixes$availableGoalsCMEFix(Supplier<IProfiler> p_i231546_1_, CallbackInfo ci) {
        this.availableGoals = ConcurrentCollections.ofNullableArraySet();
    }
}
