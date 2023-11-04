package net.burningtnt.earth2fix.controller;

import net.minecraftforge.fml.common.ICrashCallable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CrashReportDetailImpl implements ICrashCallable {
    @Override
    public String getLabel() {
        return "Earth 2 Fixes Options";
    }

    @Override
    public String call() {
        return String.format(
                "Enabled Features: [%s].\nDisabledFeatures: [%s].",
                Arrays.stream(Features.values())
                        .filter(Features::isEnabled)
                        .map(Features::name)
                        .collect(Collectors.joining(", ")),
                Arrays.stream(Features.values())
                        .filter(features -> !features.isEnabled())
                        .map(Features::name)
                        .collect(Collectors.joining(", "))
        );
    }
}
