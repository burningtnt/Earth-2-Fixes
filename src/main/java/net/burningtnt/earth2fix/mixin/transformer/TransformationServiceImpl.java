package net.burningtnt.earth2fix.mixin.transformer;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.ServiceLoaderStreamUtils;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.Features;
import net.burningtnt.earth2fix.mixin.transformer.impl.MixinWorldTransformer;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.loading.FMLServiceProvider;
import net.minecraftforge.fml.loading.ModDirTransformerDiscoverer;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;
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

        try {
            ModDirTransformerDiscoverer.getExtraLocators().add(Path.of(TransformationServiceImpl.class.getProtectionDomain().getCodeSource().getLocation().toURI()));
            Earth2Fixes.getLogger().info("Successfully inject the custom ModLocator into Forge.");
        } catch (URISyntaxException e) {
            Earth2Fixes.getLogger().error("An unexpected issue occurred while injecting the custom ModLocator into Forge.", e);
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
