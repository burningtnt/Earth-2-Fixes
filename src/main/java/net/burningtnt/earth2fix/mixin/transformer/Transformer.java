package net.burningtnt.earth2fix.mixin.transformer;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.tree.ClassNode;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Transformer implements ITransformer<ClassNode> {
    private final Map<String, IFeatureControlledClassNodeTransformer> transformers;

    private final Set<Target> targets;

    public Transformer(List<IFeatureControlledClassNodeTransformer> transformersList) {
        Map<String, IFeatureControlledClassNodeTransformer> transformers0 = new ConcurrentHashMap<>();
        Set<Target> targets0 = new HashSet<>();

        for (IFeatureControlledClassNodeTransformer transformer : transformersList) {
            if (transformer.getSwitcher().isEnabled()) {
                Target target = transformer.getTarget();
                transformers0.put(target.getClassName(), transformer);
                targets0.add(target);
            }
        }

        targets = Collections.unmodifiableSet(targets0);
        transformers = Collections.unmodifiableMap(transformers0);
    }

    @NotNull
    @Override
    public ClassNode transform(ClassNode input, @NotNull ITransformerVotingContext context) {
        IFeatureControlledClassNodeTransformer transformer = transformers.get(input.name);
        if (transformer != null) {
            transformer.transform(input);
        }
        return input;
    }

    @NotNull
    @Override
    public TransformerVoteResult castVote(@NotNull ITransformerVotingContext context) {
        return TransformerVoteResult.YES;
    }

    @NotNull
    @Override
    public Set<Target> targets() {
        return targets;
    }
}
