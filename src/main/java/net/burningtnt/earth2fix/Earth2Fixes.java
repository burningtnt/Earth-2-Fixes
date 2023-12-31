package net.burningtnt.earth2fix;

import com.tac.guns.common.WeaponType;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraftforge.fml.CrashReportExtender;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mod("earth2_fixes")
public class Earth2Fixes {
    public Earth2Fixes() {
        Logging.getLogger().info("Earth 2 Fixes is loaded.");

        try {
            Class.forName("com.tac.guns.common.WeaponType");
            Logging.getLogger().info(String.format("WeaponType is found with values [%s].", Arrays.stream(WeaponType.values()).map(Enum::name).collect(Collectors.joining(", "))));
        } catch (ClassNotFoundException e) {
            Logging.getLogger().warn("WeaponType isn't found.", e);
        }

        CrashReportExtender.registerCrashCallable("Earth 2 Fixes Options", () -> String.format(
                "Enabled Features: [%s]. DisabledFeatures: [%s].",
                Arrays.stream(Features.values())
                        .filter(Features::isEnabled)
                        .map(Features::name)
                        .collect(Collectors.joining(", ")),
                Arrays.stream(Features.values())
                        .filter(features -> !features.isEnabled())
                        .map(Features::name)
                        .collect(Collectors.joining(", "))
        ));
    }
}
