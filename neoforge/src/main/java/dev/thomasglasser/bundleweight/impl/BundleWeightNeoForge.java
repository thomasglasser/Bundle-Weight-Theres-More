package dev.thomasglasser.bundleweight.impl;

import dev.thomasglasser.bundleweight.api.BundleWeightConstants;
import net.neoforged.fml.common.Mod;

@Mod(BundleWeightConstants.MOD_ID)
public class BundleWeightNeoForge {
    public BundleWeightNeoForge() {
        BundleWeight.init();
    }
}
