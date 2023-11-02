package net.burningtnt.earth2fix.mixin.mixins.mods.stevemodel;

import com.elfmcys.yesstevemodel.O00OooooooOOo0OoO0Oo0O0O;
import com.tac.guns.common.Gun;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(O00OooooooOOo0OoO0Oo0O0O.class)
public class SteveModelMixin0 {
    @Unique
    private static final boolean earth2fixes$weaponTypeClassStatus;

    @Unique
    private static final boolean earth2fixes$weaponTypeMethodStatus;

    static {
        boolean classStatus = true, methodStatus = true;
        try {
            Class.forName("com.tac.guns.common.WeaponType");
        } catch (ClassNotFoundException e) {
            classStatus = false;
            Earth2Fixes.getLogger().error("Class WeaponType cannot be found. Yes Steve Mode will be disabled in order not to crash Minecraft.", e);
        }
        try {
            Gun.Display.class.getMethod("getWeaponType");
        } catch (NoSuchMethodException e) {
            methodStatus = false;
            Earth2Fixes.getLogger().error("Method Gun.Display::getWeaponType cannot be found. Yes Steve Mode will be disabled in order not to crash Minecraft.", e);
        }
        earth2fixes$weaponTypeClassStatus = classStatus;
        earth2fixes$weaponTypeMethodStatus = methodStatus;
    }

    @Inject(
            method = "OooOo0oOoOO0o00OO000oO00",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true,
            remap = false
    )
    private static void earth2fixes$weaponTypeFixes(RenderPlayerEvent.Pre par1, CallbackInfo ci) {
        if (Features.WEAPON_TYPE_FIX.isEnabled()) {
            if (!earth2fixes$weaponTypeClassStatus) {
                if (!earth2fixes$weaponTypeMethodStatus) {
                    Earth2Fixes.getLogger().warn("Yes Steve Model is disabled because TAC is unavailable as Class WeaponType and Method Gun.Display::getWeaponType cannot be found.");
                } else {
                    Earth2Fixes.getLogger().warn("Yes Steve Model is disabled because TAC is unavailable as Class WeaponType cannot be found.");
                }
                ci.cancel();
            } else if (!earth2fixes$weaponTypeMethodStatus) {
                Earth2Fixes.getLogger().warn("Yes Steve Model is disabled because TAC is unavailable as Method Gun.Display::getWeaponType cannot be found.");
                ci.cancel();
            }
        }
    }
}
