package dev.thomasglasser.bundleweight;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(BundleWeight.MOD_ID)
public class BundleWeightNeoForge {
    public BundleWeightNeoForge(IEventBus modBus) {
        BundleWeight.init();
    }
}
