package dev.thomasglasser.bundleweight.impl.mixin.minecraft.world.item.component;

import com.mojang.serialization.DataResult;
import dev.thomasglasser.bundleweight.api.BundleWeightDataComponents;
import java.util.List;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.component.Bees;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BundleContents.class)
public class BundleContentsMixin {
    @Inject(method = "getWeight", at = @At("HEAD"), cancellable = true)
    private static void overrideWeight(ItemInstance item, CallbackInfoReturnable<DataResult<Fraction>> cir) {
        Fraction bundleWeight = item.get(BundleWeightDataComponents.BUNDLE_WEIGHT.get());
        if (bundleWeight != null) {
            BundleContents bundle = item.get(DataComponents.BUNDLE_CONTENTS);
            if (bundle != null) {
                cir.setReturnValue(bundle.weight().map((nestedWeight) -> nestedWeight.add(bundleWeight)));
            } else {
                List<BeehiveBlockEntity.Occupant> bees = item.getOrDefault(DataComponents.BEES, Bees.EMPTY).bees();
                if (bees.isEmpty()) {
                    cir.setReturnValue(DataResult.success(bundleWeight));
                }
            }
        } else {
            List<BeehiveBlockEntity.Occupant> bees = item.getOrDefault(DataComponents.BEES, Bees.EMPTY).bees();
            if (!bees.isEmpty()) {
                Fraction beehiveWeight = item.get(BundleWeightDataComponents.BEEHIVE_BUNDLE_WEIGHT.get());
                if (beehiveWeight != null) {
                    cir.setReturnValue(DataResult.success(beehiveWeight));
                }
            }
        }
    }
}
