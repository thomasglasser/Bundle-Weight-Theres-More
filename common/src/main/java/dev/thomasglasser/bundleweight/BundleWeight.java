package dev.thomasglasser.bundleweight;

import dev.thomasglasser.bundleweight.api.BundleWeightDataComponents;
import dev.thomasglasser.tommylib.api.platform.TommyLibServices;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleWeight {
    public static final String MOD_ID = "bundleweight";
    public static final String MOD_NAME = "Bundle Weight, There's More!";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void init() {
        LOGGER.info("Initializing {} for {} in a {} environment...", MOD_NAME, TommyLibServices.PLATFORM.getPlatformName(), TommyLibServices.PLATFORM.getEnvironmentName());

        BundleWeightDataComponents.init();
    }

    public static ResourceLocation modLoc(String s) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, s);
    }
}
