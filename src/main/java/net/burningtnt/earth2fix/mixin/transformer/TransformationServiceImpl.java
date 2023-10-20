package net.burningtnt.earth2fix.mixin.transformer;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.Features;
import net.burningtnt.earth2fix.mixin.transformer.impl.MixinWorldTransformer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class TransformationServiceImpl implements ITransformationService {
    private static final List<IFeatureControlledClassNodeTransformer> transformers = List.of(
            new MixinWorldTransformer()
    );

    private final TransformerImpl transformer = new TransformerImpl(transformers);

    @NotNull
    @Override
    public String name() {
        return "earth2_fixes";
    }

    @Override
    public void initialize(@NotNull IEnvironment environment) {
        Earth2Fixes.getLogger().info("Earth 2 Fixes Transformer Service is initialized.");

        for (Features options : Features.values()) {
            Earth2Fixes.getLogger().info(String.format("Earth2 Fix Option '%s' is %s.", options.name(), options.isEnabled() ? "enabled" : "disabled"));
        }
    }

    @Override
    public void beginScanning(@NotNull IEnvironment environment) {
    }

    @Override
    public void onLoad(@NotNull IEnvironment env, @NotNull Set<String> otherServices) {
        Earth2Fixes.getLogger().info("Earth 2 Fixes Transformer Service is loaded.");
    }

    @Override
    public @NotNull List<ITransformer> transformers() {
        return List.of(transformer);
    }
}
