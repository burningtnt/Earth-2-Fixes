package net.burningtnt.earth2fix.mixin.mixins.stevemodel;

import com.elfmcys.yesstevemodel.oooO000o00OoOOoO000o000O;
import com.tac.guns.common.Gun;
import com.tac.guns.common.WeaponType;
import net.burningtnt.earth2fix.Earth2Fixes;
import net.burningtnt.earth2fix.switcher.Features;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(oooO000o00OoOOoO000o000O.class)
public class SteveModelMixin0 {
    @Unique
    private static final WeaponType earth2fixes$defaultWeaponType;

    static {
        WeaponType defaultWeaponType;
        try {
            Gun.Display.class.getMethod("getWeaponType");
            defaultWeaponType = null;
        } catch (NoSuchMethodException e) {
            Earth2Fixes.getLogger().warn("Cannot get the type of the Weapon from com.tac.guns.common.Gun.Display because method com.tac.guns.common.Gun.Display.getWeaponType doesn't exist! WeaponType.AR will be returned!. The weapon may be rendered abnormally.");
            defaultWeaponType = WeaponType.AR;
        }
        earth2fixes$defaultWeaponType = defaultWeaponType;
    }

    @Redirect(
            method = {
                    "OooOo0oOoOO0o00OO000oO00(Lcom/elfmcys/yesstevemodel/o0ooooOo0oo00o0O0oO0OO;Lnet/minecraft/item/ItemStack;)Lcom/elfmcys/yesstevemodel/O0ooo00o00000OOo0oo0oO00;",
                    "OooOo0oOoOO0o00OO000oO00(Lnet/minecraft/item/ItemStack;Lcom/elfmcys/yesstevemodel/O00o00Ooo000oo0O00ooo0Oo;Lnet/minecraft/entity/LivingEntity;Lcom/mojang/blaze3d/matrix/MatrixStack;IF)V"
            },
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/tac/guns/common/Gun$Display;getWeaponType()Lcom/tac/guns/common/WeaponType;"
            ),
            remap = false
    )
    private static WeaponType earth2fixes$getWeaponTypeRedirector(Gun.Display instance) {
        if (Features.WEAPON_TYPE_FIX.isEnabled() && earth2fixes$defaultWeaponType != null) {
            return earth2fixes$defaultWeaponType;
        }
        return instance.getWeaponType();
    }
}
