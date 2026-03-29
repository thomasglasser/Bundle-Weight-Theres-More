package dev.thomasglasser.bundleweight.api;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleWeightConstants {
    public static final String MOD_ID = "bundleweight";
    public static final String MOD_NAME = "Bundle Weight, There's More!";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static Identifier modId(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
