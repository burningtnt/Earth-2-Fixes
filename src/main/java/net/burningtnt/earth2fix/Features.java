package net.burningtnt.earth2fix;

public enum Features {
    BLOCK_SWAP_NPE_FIX,
    CARRY_ON_CONFLICT_FIX,
    CAS_PARKING,
    CHANNEL_MANAGER_CME_FIX,
    ILLUMINATIONS_FIX,
    MODERN_UI_INVALID_CLASS_LOADER_FIX,
    NETHER_PORTAL_FIX,
    NETWORK_MANAGER_LOG_FIX,
    OBFUSCATE_PLAYER_DATA_FIX,
    RENDER_TYPE_CME_FIX,
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
