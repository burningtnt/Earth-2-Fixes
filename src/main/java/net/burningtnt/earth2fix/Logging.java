package net.burningtnt.earth2fix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logging {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Logger getLogger() {
        return LOGGER;
    }
}
