package net.burningtnt.earth2fix.core.mixins.mods.obfuscate;

import com.mrcrayfish.obfuscate.common.data.SyncedDataKey;
import com.mrcrayfish.obfuscate.common.data.SyncedPlayerData;
import com.mrcrayfish.obfuscate.network.HandshakeMessages;
import net.burningtnt.earth2fix.Logging;
import net.burningtnt.earth2fix.controller.Features;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mixin(HandshakeMessages.S2CSyncedPlayerData.class)
public class HandshakeMessages$S2CSyncedPlayerDataMixin {
    @SuppressWarnings("unused")
    public HandshakeMessages$S2CSyncedPlayerDataMixin(Map<ResourceLocation, Integer> keyMap) {
    }

    @Inject(
            method = "decode",
            at = @At(
                    value = "HEAD"
            ),
            remap = false,
            cancellable = true
    )
    private static void earth2fixes$fixMissingData(PacketBuffer input, CallbackInfoReturnable<HandshakeMessages.S2CSyncedPlayerData> cir) {
        if (!Features.OBFUSCATE_PLAYER_DATA_FIX.isEnabled()) {
            return;
        }

        Map<ResourceLocation, Integer> keyMap = new HashMap<>();
        List<SyncedDataKey<?>> dataKeys = SyncedPlayerData.instance().getKeys();
        for (SyncedDataKey<?> dataKey : dataKeys) {
            ResourceLocation rl;
            try {
                rl = input.readResourceLocation();
            } catch (IndexOutOfBoundsException e) {
                earth2fixes$logMessage(dataKeys, dataKey, e, keyMap);
                cir.setReturnValue((HandshakeMessages.S2CSyncedPlayerData) (Object) new HandshakeMessages$S2CSyncedPlayerDataMixin(keyMap));
                return;
            }
            int varInt;
            try {
                varInt = input.readVarInt();
            } catch (IndexOutOfBoundsException e) {
                earth2fixes$logMessage(dataKeys, dataKey, e, keyMap);
                cir.setReturnValue((HandshakeMessages.S2CSyncedPlayerData) (Object) new HandshakeMessages$S2CSyncedPlayerDataMixin(keyMap));
                return;
            }
            keyMap.put(rl, varInt);
        }
        cir.setReturnValue((HandshakeMessages.S2CSyncedPlayerData) (Object) new HandshakeMessages$S2CSyncedPlayerDataMixin(keyMap));
    }

    @Unique
    private static void earth2fixes$logMessage(List<SyncedDataKey<?>> dataKeys, SyncedDataKey<?> currentDataKey, Throwable throwable, Map<ResourceLocation, Integer> currentKeyMap) {
        Logging.getLogger().warn(String.format(
                "Cannot read data key '%s'. Currently, the existed key is {%s}, however, {%s} is expected.",
                currentDataKey.getKey(),
                currentKeyMap.entrySet().stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(Collectors.joining(", ")),
                dataKeys.stream()
                        .map(key -> key.getKey() + "=" + key.getId())
                        .collect(Collectors.joining(", "))
        ), throwable);
    }
}
