package net.burningtnt.earth2fix.mixin.transformer.impl;

import cpw.mods.modlauncher.api.ITransformer;
import net.burningtnt.earth2fix.mixin.transformer.IFeatureControlledClassNodeTransformer;
import net.burningtnt.earth2fix.switcher.ISwitcher;
import org.objectweb.asm.tree.ClassNode;

public class MixinWorldTransformer implements IFeatureControlledClassNodeTransformer {
    @Override
    public ITransformer.Target getTarget() {
        return ITransformer.Target.targetClass("corgitaco/blockswap/mixin/MixinWorld");
    }

    @Override
    public ISwitcher getSwitcher() {
        return ISwitcher.ENABLED;
    }

    @Override
    public void transform(ClassNode classNode) {
        classNode.methods.removeIf(methodNode -> methodNode.name.equals("isIncompatibleBlock") || methodNode.name.equals("func_241211_a_"));
    }
}
