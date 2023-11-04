package net.burningtnt.earth2fix.mixin.transformer;

import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.Logging;
import net.burningtnt.earth2fix.controller.Features;
import net.burningtnt.earth2fix.mixin.transformer.impls.MixinWorldTransformer;
import net.minecraftforge.fml.loading.ModDirTransformerDiscoverer;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class TransformationServiceImpl implements ITransformationService {
    private final TransformerImpl transformer = new TransformerImpl(List.of(
            new MixinWorldTransformer()
    ));

    @NotNull
    @Override
    public String name() {
        return "earth2_fixes";
    }

    @Override
    public void initialize(@NotNull IEnvironment environment) {
        Logging.getLogger().info("Earth 2 Fixes Transformer Service is initialized.");

        for (Features options : Features.values()) {
            Logging.getLogger().info(String.format("Earth2 Fix Option '%s' is %s.", options.name(), options.isEnabled() ? "enabled" : "disabled"));
        }

        try {
            ModDirTransformerDiscoverer.getExtraLocators().add(Path.of(TransformationServiceImpl.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            Logging.getLogger().info("Successfully inject the custom ModLocator into Forge.");
        } catch (URISyntaxException e) {
            Logging.getLogger().error("An unexpected issue occurred while injecting the custom ModLocator into Forge.", e);
        }
    }

    @Override
    public void beginScanning(@NotNull IEnvironment environment) {
    }

    @Override
    public void onLoad(@NotNull IEnvironment env, @NotNull Set<String> otherServices) {
        Logging.getLogger().info("Earth 2 Fixes Transformer Service is loaded.");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public @NotNull List<ITransformer> transformers() {
        return List.of(transformer);
    }
}
