package dev.thomasglasser.bundleweight.impl.mixin.minecraft.world.item.component;

import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.Mutable.class)
public class BundleContentsMutableMixin {
    @Inject(method = "getMaxAmountToAdd", at = @At("HEAD"), cancellable = true)
    private void allowNoWeight(Fraction itemWeight, CallbackInfoReturnable<Integer> cir) {
        if (itemWeight.getNumerator() == 0) {
            cir.setReturnValue(Integer.MAX_VALUE);
        }
    }
}
