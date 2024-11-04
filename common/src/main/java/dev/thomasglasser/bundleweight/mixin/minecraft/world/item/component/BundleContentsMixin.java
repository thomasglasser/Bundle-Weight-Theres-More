package dev.thomasglasser.bundleweight.mixin.minecraft.world.item.component;

import dev.thomasglasser.bundleweight.api.BundleWeightDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.class)
public class BundleContentsMixin {
    @Inject(method = "getWeight", at = @At("HEAD"), cancellable = true)
    private static void getWeight(ItemStack stack, CallbackInfoReturnable<Fraction> cir) {
        Fraction bundleWeight = stack.get(BundleWeightDataComponents.BUNDLE_WEIGHT.get());
        if (bundleWeight != null) {
            BundleContents bundleContents = stack.get(DataComponents.BUNDLE_CONTENTS);
            if (bundleContents != null) {
                cir.setReturnValue(bundleWeight.add(bundleContents.weight()));
            } else {
                cir.setReturnValue(bundleWeight);
            }
        }
    }
}
