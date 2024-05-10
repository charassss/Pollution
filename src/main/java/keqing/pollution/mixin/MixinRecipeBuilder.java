package keqing.pollution.mixin;

import gregtech.api.recipes.RecipeBuilder;
import gregtech.api.recipes.builders.FuelRecipeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeBuilder.class)
public class MixinRecipeBuilder <R extends RecipeBuilder<R>> {
    protected int duration;
    protected int parallel = 0;

    @Inject(method = "duration", at = @At("TAIL"))
    public void duration(int duration, CallbackInfoReturnable<R> cir) {
        this.duration = duration;
    }


}
