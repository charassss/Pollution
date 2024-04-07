package keqing.pollution.api.block.impl;

import net.minecraft.util.IStringSerializable;

public class WrappedTired implements ITired {

    private final IStringSerializable inner;

    public WrappedTired(IStringSerializable inner) {
        this.inner = inner;
    }

    @Override
    public String getName() {
        return inner.getName();
    }
}