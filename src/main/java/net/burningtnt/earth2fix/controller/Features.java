package net.burningtnt.earth2fix.controller;

public enum Features implements ISwitcher {
    BLOCK_SWAP_NPE_FIX,
    BULLET_RENDER_FIX,
    CARRY_ON_CONFLICT_FIX,
    CHANNEL_MANAGER_CME_FIX,
    DURABILITY_NPE_FIX,
    ILLUMINATIONS_FIX,
    MODERN_UI_INVALID_CLASS_LOADER_FIX,
    MUSIC_PLAYER_NPE_FIX,
    MUSIC_TRIGGER_CLIENT_DISCONNECTED_NPE_FIX,
    NETHER_PORTAL_FIX,
    NETWORK_MANAGER_LOG_FIX,
    OBFUSCATE_PLAYER_DATA_FIX,
    RENDER_TYPE_CME_FIX,
    SOUND_ENGINE_CME_FIX,
    TEMPLATE_MANAGER_CME_FIX,
    UNLOCK_HANDLER_NPE_FIX,
    WEAPON_TYPE_FIX;

    private static final String PREFIX = "earth2.fixes";

    private final boolean enabled;

    Features() {
        String name = this.name();
        StringBuilder optionKey = new StringBuilder(PREFIX.length() + 1 + name.length()).append(PREFIX).append('.');
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                optionKey.append((char) (c + 32));
            } else if (c == '_' || (c >= '0' && c <= '9')) {
                optionKey.append(c);
            } else {
                throw new IllegalStateException(String.format("Fixes '%s' is invalid.", name));
            }
        }
        enabled = "enable".equals(System.getProperty(optionKey.toString()));
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
