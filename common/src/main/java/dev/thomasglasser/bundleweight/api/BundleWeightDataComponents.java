package dev.thomasglasser.bundleweight.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.bundleweight.BundleWeight;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.apache.commons.lang3.math.Fraction;

public class BundleWeightDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, BundleWeight.MOD_ID);

    /**
     * Codecs for the {@link Fraction} class.
     */
    private static final Codec<Fraction> FRACTION_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("numerator").forGetter(Fraction::getNumerator),
            Codec.INT.fieldOf("denominator").forGetter(Fraction::getDenominator)).apply(instance, Fraction::getFraction));
    private static final StreamCodec<ByteBuf, Fraction> FRACTION_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, Fraction::getNumerator,
            ByteBufCodecs.INT, Fraction::getDenominator,
            Fraction::getFraction);

    /**
     * Used to determine the weight of an item in a bundle separately from the stack size.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Fraction>> BUNDLE_WEIGHT = DATA_COMPONENTS.register("bundle_weight", () -> DataComponentType.<Fraction>builder().persistent(FRACTION_CODEC).networkSynchronized(FRACTION_STREAM_CODEC).build());

    public static void init() {}
}
