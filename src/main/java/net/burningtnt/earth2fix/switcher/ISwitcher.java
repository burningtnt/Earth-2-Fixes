package net.burningtnt.earth2fix.switcher;

public interface ISwitcher {
    ISwitcher ENABLED = () -> true;

    ISwitcher DISABLED = () -> false;

    boolean isEnabled();
}
