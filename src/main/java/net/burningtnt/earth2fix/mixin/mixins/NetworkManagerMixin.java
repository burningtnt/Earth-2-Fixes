package net.burningtnt.earth2fix.mixin.mixins;

import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import net.burningtnt.earth2fix.Features;
import net.minecraft.network.NetworkManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.stream.Collectors;

@Mixin(NetworkManager.class)
public final class NetworkManagerMixin {
    @Redirect(
            method = "exceptionCaught",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/apache/logging/log4j/Logger;debug(Ljava/lang/String;Ljava/lang/Throwable;)V"
            ),
            remap = false
    )
    private void earth2fixes$forceLog(Logger instance, String message, Throwable throwable) {
        if (Features.NETWORK_MANAGER_LOG_FIX.isEnabled()) {
            instance.warn(message, new IOException("Minecraft cannot connect to the specific server. ", throwable));
            instance.warn(String.format(
                    "Currently, the com.mrcrayfish.obfuscate.common.data.SyncedPlayerData.getKeys() is [%s].",
                    SyncedPlayerData.instance().getKeys().stream()
                            .map(syncedDataKey -> String.format("SyncedDatKey{key=%s}", syncedDataKey.getKey()))
                            .collect(Collectors.joining(", "))
            ));
        } else {
            instance.debug(message, throwable);
        }
    }
}
