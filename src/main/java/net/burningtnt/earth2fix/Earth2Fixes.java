package net.burningtnt.earth2fix;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

@Mod("earth2_fixes")
public class Earth2Fixes {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Logger getLogger() {
        return LOGGER;
    }

    public Earth2Fixes() {
        Earth2Fixes.getLogger().info("Earth 2 Fixes is loaded.");
    }
}
