package dev.thomasglasser.bundleweight.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.bundleweight.BundleWeight;
import dev.thomasglasser.tommylib.api.registration.DeferredHolder;
import dev.thomasglasser.tommylib.api.registration.DeferredRegister;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.apache.commons.lang3.math.Fraction;

public class BundleWeightDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, BundleWeight.MOD_NAMESPACE);

    /**
     * Codecs for the {@link Fraction} class.
     */
    private static final StreamCodec<ByteBuf, Fraction> FRACTION_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, Fraction::getNumerator,
            ByteBufCodecs.VAR_INT, Fraction::getDenominator,
            Fraction::getFraction);
    private static final Codec<Fraction> DIRECT_FRACTION_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("numerator").forGetter(Fraction::getNumerator),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("denominator").forGetter(Fraction::getDenominator)).apply(instance, Fraction::getFraction));
    private static final Codec<Fraction> VALIDATED_FRACTION_CODEC = DIRECT_FRACTION_CODEC.validate(fraction -> {
        double value = fraction.doubleValue();
        if (value < 0 || value > 1) {
            return DataResult.error(() -> "Fraction must be between 0 and 1 inclusive.");
        }
        return DataResult.success(fraction);
    });
    private static final Codec<Fraction> FRACTION_OR_DOUBLE_CODEC = Codec.withAlternative(
            VALIDATED_FRACTION_CODEC,
            Codec.doubleRange(0, 1).xmap(Fraction::getFraction, Fraction::doubleValue));

    /**
     * Used to determine the weight of an item in a bundle separately from the stack size.
     */
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Fraction>> BUNDLE_WEIGHT = DATA_COMPONENTS.register("bundle_weight", () -> DataComponentType.<Fraction>builder().persistent(FRACTION_OR_DOUBLE_CODEC).networkSynchronized(FRACTION_STREAM_CODEC).build());

    public static void init() {}
}
