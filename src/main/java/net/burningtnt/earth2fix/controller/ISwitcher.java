package net.burningtnt.earth2fix.controller;

public interface ISwitcher {
    ISwitcher ENABLED = () -> true;

    ISwitcher DISABLED = () -> false;

    boolean isEnabled();
}
