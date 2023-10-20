package net.burningtnt.earth2fix.mixin;

import net.burningtnt.earth2fix.Earth2Fixes;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.forgespi.locating.IModFile;

import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModLocatorImpl extends AbstractJarFileLocator {
    private static final Path selfPath = Optional.ofNullable(ModLocatorImpl.class.getProtectionDomain().getCodeSource())
            .map(cs -> {
                try {
                    return Path.of(cs.getLocation().toURI()).toAbsolutePath();
                } catch (FileSystemNotFoundException | IllegalArgumentException | URISyntaxException e) {
                    Earth2Fixes.getLogger().warn("Cannot find the path to Earth 2 Fixes.", e);
                    return null;
                }
            })
            .filter(Files::isRegularFile)
            .orElse(null);

    static {
        Earth2Fixes.getLogger().info(String.format("Earth 2 Fixes path: %s.", selfPath));
    }

    public List<IModFile> scanMods() {
        if (selfPath == null) {
            Earth2Fixes.getLogger().info("Successfully added self to Forge.");
            return List.of();
        } else {
            Earth2Fixes.getLogger().info("Cannot added self to Forge.");
            return List.of(ModFile.newFMLInstance(selfPath, this));
        }
    }

    public String name() {
        return "earth2_fixes";
    }

    public void initArguments(Map<String, ?> arguments) {
    }
}
