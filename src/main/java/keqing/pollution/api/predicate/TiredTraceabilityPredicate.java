package keqing.pollution.api.predicate;

import gregtech.api.block.VariantActiveBlock;
import gregtech.api.pattern.BlockWorldState;
import gregtech.api.pattern.PatternStringError;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.util.BlockInfo;
import gregtech.common.blocks.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import keqing.pollution.api.block.impl.ITired;
import keqing.pollution.api.block.impl.WrappedIntTired;
import keqing.pollution.common.block.PollutionMetaBlock.POCoilBlock;
import keqing.pollution.common.block.PollutionMetaBlock.POMagicBlock;
import keqing.pollution.common.block.PollutionMetaBlocks;
import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static keqing.pollution.common.block.PollutionMetaBlock.POCoilBlock.WireCoilType.*;
import static keqing.pollution.common.block.PollutionMetaBlock.POMBeamCore.MagicBlockType.*;
import static keqing.pollution.common.block.PollutionMetaBlocks.WIRE_COIL;

public class TiredTraceabilityPredicate extends TraceabilityPredicate {


    static {

        MAP_COIL_CASING = new Object2ObjectOpenHashMap<>();
        MAP_CP_BEAM = new Object2ObjectOpenHashMap<>();

        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_1),
                new WrappedIntTired(COIL_LEVEL_1, 1));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_2),
                new WrappedIntTired(COIL_LEVEL_2, 2));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_3),
                new WrappedIntTired(COIL_LEVEL_3, 3));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_4),
                new WrappedIntTired(COIL_LEVEL_4, 4));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_5),
                new WrappedIntTired(COIL_LEVEL_5, 5));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_6),
                new WrappedIntTired(COIL_LEVEL_6, 6));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_7),
                new WrappedIntTired(COIL_LEVEL_7, 7));
        TiredTraceabilityPredicate.MAP_COIL_CASING.put(WIRE_COIL.getState(COIL_LEVEL_8),
                new WrappedIntTired(COIL_LEVEL_8, 8));


        TiredTraceabilityPredicate.MAP_CP_BEAM.put(PollutionMetaBlocks.BEAM_CORE.getState(BEAM_CORE_0),
                new WrappedIntTired(BEAM_CORE_0,1));
        TiredTraceabilityPredicate.MAP_CP_BEAM.put(PollutionMetaBlocks.BEAM_CORE.getState(BEAM_CORE_1),
                new WrappedIntTired(BEAM_CORE_1,2));
        TiredTraceabilityPredicate.MAP_CP_BEAM.put(PollutionMetaBlocks.BEAM_CORE.getState(BEAM_CORE_2),
                new WrappedIntTired(BEAM_CORE_2,3));
        TiredTraceabilityPredicate.MAP_CP_BEAM.put(PollutionMetaBlocks.BEAM_CORE.getState(BEAM_CORE_3),
                new WrappedIntTired(BEAM_CORE_3,4));
        TiredTraceabilityPredicate.MAP_CP_BEAM.put(PollutionMetaBlocks.BEAM_CORE.getState(BEAM_CORE_4),
                new WrappedIntTired(BEAM_CORE_4,5));


    }

    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_COIL_CASING;
    public static final Object2ObjectOpenHashMap<IBlockState,ITired> MAP_CP_BEAM;

    public static TraceabilityPredicate CP_COIL_CASING = new TiredTraceabilityPredicate(MAP_COIL_CASING,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_COIL_CASING.get(s)).getIntTier()),"COIL",null);


    public static TraceabilityPredicate CP_BEAM_CORE = new TiredTraceabilityPredicate(MAP_CP_BEAM,
            Comparator.comparing((s) -> ((WrappedIntTired)MAP_CP_BEAM.get(s)).getIntTier()),"BEAM",null);


    private final Object2ObjectOpenHashMap<IBlockState, ITired> map;
    private final String name;

    private final String errorKey;

    private Supplier<BlockInfo[]> candidatesCache;

    private final Comparator<IBlockState> comparator;


    public TiredTraceabilityPredicate(Object2ObjectOpenHashMap<IBlockState, ITired> map, String name, @Nullable String errorKey){
        this(map,Comparator.comparing((s) -> map.get(s).getName()),name,errorKey);
    }

    public TiredTraceabilityPredicate(Object2ObjectOpenHashMap<IBlockState, ITired> map, Comparator<IBlockState> comparator, String name, @Nullable String errorKey){
        super();
        this.map = map;
        this.name = name;
        if(errorKey == null){
            this.errorKey = "gregtech.multiblock.pattern.error.casing";
        }
        else{
            this.errorKey = errorKey;
        }
        this.common.add(new SimplePredicate(predicate(), candidates()));
        this.comparator = comparator;

    }

    private Predicate<BlockWorldState> predicate(){
        return  (blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (map.containsKey(blockState)) {
                ITired stats = map.get(blockState);
                Object tier = stats.getTire();
                Object current = blockWorldState.getMatchContext().getOrPut(name, tier);
                if (!current.equals(tier)) {
                    blockWorldState.setError(new PatternStringError(errorKey));
                    return false;
                } else {
                    blockWorldState.getMatchContext().getOrPut(name+"TiredStats",stats);
                    if(blockState.getBlock() instanceof VariantActiveBlock){
                        (blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>())).add(blockWorldState.getPos());
                    }
                    return true;
                }
            } else {
                return false;
            }
        };
    }

    private Supplier<BlockInfo[]> candidates(){
        if(candidatesCache == null) {
            candidatesCache = () -> map.keySet().stream()
                    .sorted(comparator)
                    .map(type -> new BlockInfo(type, null,map.get(type).getInfo()))
                    .toArray(BlockInfo[]::new);
        }
        return candidatesCache;
    }
}