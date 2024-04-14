package keqing.pollution.api.block.impl;

import net.minecraft.util.IStringSerializable;

public interface ITired extends IStringSerializable {

    default Object getInfo(){
        return null;
    }

    default Object getTire(){
        return this.getName();
    }
}