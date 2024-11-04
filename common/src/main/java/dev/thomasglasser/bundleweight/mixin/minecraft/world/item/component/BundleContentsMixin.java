package dev.thomasglasser.bundleweight.mixin.minecraft.world.item.component;

import dev.thomasglasser.bundleweight.api.BundleWeightDataComponents;
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
        if (stack.has(BundleWeightDataComponents.BUNDLE_WEIGHT.get())) {
            cir.setReturnValue(stack.get(BundleWeightDataComponents.BUNDLE_WEIGHT.get()));
        }
    }
}
