package net.burningtnt.earth2fix;

import com.tac.guns.common.WeaponType;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mod("earth2_fixes")
public class Earth2Fixes {
    private static final Logger LOGGER = LogManager.getLogger();

    public Earth2Fixes() {
        Earth2Fixes.getLogger().info("Earth 2 Fixes is loaded.");

        try {
            Class.forName("com.tac.guns.common.WeaponType");
            Earth2Fixes.getLogger().info(String.format("WeaponType is found with value [%s].", Arrays.stream(WeaponType.values()).map(Enum::name).collect(Collectors.joining(", "))));
        } catch (ClassNotFoundException e) {
            Earth2Fixes.getLogger().warn("WeaponType isn't found.", e);
        }
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
