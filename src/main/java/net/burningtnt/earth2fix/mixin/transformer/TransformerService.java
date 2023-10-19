package net.burningtnt.earth2fix.mixin.transformer;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.Features;
import net.burningtnt.earth2fix.mixin.transformer.impl.MixinWorldTransformer;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixins;

import java.util.List;
import java.util.Set;

public class TransformerService implements ITransformationService {
    private static final List<IFeatureControlledClassNodeTransformer> transformers = List.of(
            new MixinWorldTransformer()
    );

    private final Transformer transformer = new Transformer(transformers);

    @NotNull
    @Override
    public String name() {
        return "earth2fixes";
    }

    @Override
    public void initialize(@NotNull IEnvironment environment) {
        Earth2Fixes.getLogger().info("Earth2 Fixes Transformer Service is initialized.");
    }

    @Override
    public void beginScanning(@NotNull IEnvironment environment) {
    }

    @Override
    public void onLoad(@NotNull IEnvironment env, @NotNull Set<String> otherServices) {
        Earth2Fixes.getLogger().info("Earth2 Fixes Transformer Service is loaded..");

        for (Features options : Features.values()) {
            Earth2Fixes.getLogger().info(String.format("Earth2 Fix Option '%s' is %s.", options.name(), options.isEnabled() ? "enabled" : "disabled"));
        }

        Earth2Fixes.getLogger().info("Injecting earth2_fixes.mixins.json.");
        Mixins.addConfiguration("earth2_fixes.mixins.json");
    }

    @Override
    public @NotNull List<ITransformer> transformers() {
        return List.of(transformer);
    }
}
