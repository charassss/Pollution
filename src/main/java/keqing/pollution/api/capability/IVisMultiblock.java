package keqing.pollution.api.capability;

public interface IVisMultiblock {

    /**
     *
     * @return the current maximum amount of parallelization provided
     */
    default boolean isVis() {
        return false;
    }
}