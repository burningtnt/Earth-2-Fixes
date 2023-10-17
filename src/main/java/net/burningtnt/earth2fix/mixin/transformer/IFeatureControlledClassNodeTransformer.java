package net.burningtnt.earth2fix.mixin.transformer;

import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.Features;
import org.objectweb.asm.tree.ClassNode;

public interface IFeatureControlledClassNodeTransformer {
    ITransformer.Target getTarget();

    Features getSwitcher();

    void transform(ClassNode classNode);
}
