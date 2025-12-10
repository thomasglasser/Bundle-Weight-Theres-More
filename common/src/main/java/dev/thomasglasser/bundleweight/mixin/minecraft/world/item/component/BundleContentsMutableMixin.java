package dev.thomasglasser.bundleweight.mixin.minecraft.world.item.component;

import dev.thomasglasser.bundleweight.api.BundleWeightDataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.Mutable.class)
public class BundleContentsMutableMixin {
    @Inject(method = "getMaxAmountToAdd", at = @At("HEAD"), cancellable = true)
    private void allowNoWeight(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        Fraction weight = stack.get(BundleWeightDataComponents.BUNDLE_WEIGHT.get());
        if (weight != null && weight.getNumerator() == 0) {
            cir.setReturnValue(Integer.MAX_VALUE);
        }
    }
}
