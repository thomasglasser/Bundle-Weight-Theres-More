package dev.thomasglasser.bundleweight.impl;

import net.fabricmc.api.ModInitializer;

public class BundleWeightFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BundleWeight.init();
    }
}
