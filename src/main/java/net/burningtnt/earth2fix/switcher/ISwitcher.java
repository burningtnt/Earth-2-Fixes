package net.burningtnt.earth2fix.switcher;

public interface ISwitcher {
    static ISwitcher ofEnabled() {
        return Features.ENABLED;
    }

    static ISwitcher ofDisabled() {
        return Features.DISABLED;
    }

    static ISwitcher ofConstantStatus(boolean status) {
        return status ? Features.ENABLED : Features.DISABLED;
    }

    boolean isEnabled();
}
