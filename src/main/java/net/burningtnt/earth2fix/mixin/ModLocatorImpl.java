package net.burningtnt.earth2fix.mixin;

import net.burningtnt.earth2fix.Earth2Fixes;
import net.minecraftforge.fml.loading.moddiscovery.AbstractJarFileLocator;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.forgespi.locating.IModFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModLocatorImpl extends AbstractJarFileLocator {
    public List<IModFile> scanMods() {
        Earth2Fixes.getLogger().info("Earth 2 Fixes Mod Locator is invoked.");

        List<IModFile> list = new ArrayList<>();
        try {
            Path path = Path.of(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
            if (!Files.isDirectory(path)) {
                IModFile file = ModFile.newFMLInstance(path, this);
                this.modJars.compute(file, (mf, fs) -> this.createFileSystem(mf));
                list.add(file);
            }
        } catch (URISyntaxException e) {
            Earth2Fixes.getLogger().error("Cannot inject Earth 2 Fixes Mod to forge.", e);
        }
        return list;
    }

    public String name() {
        return "earth2_fixes";
    }

    public void initArguments(Map<String, ?> arguments) {
    }
}
