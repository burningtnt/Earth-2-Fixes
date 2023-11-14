package net.burningtnt.earth2fix.core.transformer;

import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.controller.ISwitcher;
import org.objectweb.asm.tree.ClassNode;

public interface IFeatureControlledClassNodeTransformer {
    ITransformer.Target getTarget();

    ISwitcher getSwitcher();

    void transform(ClassNode classNode);
}
