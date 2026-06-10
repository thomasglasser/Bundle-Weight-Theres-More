package dev.thomasglasser.bundleweight.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.thomasglasser.tommylib.api.registration.ExtendedHolder;
import dev.thomasglasser.tommylib.api.registration.Registrar;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.apache.commons.lang3.math.Fraction;
import org.jetbrains.annotations.ApiStatus;

public class BundleWeightDataComponents {
    public static final Registrar.DataComponents DATA_COMPONENTS = Registrar.createDataComponents(Registries.DATA_COMPONENT_TYPE, BundleWeightConstants.MOD_ID);

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

    /// The weight of an item in a bundle.
    public static final ExtendedHolder<DataComponentType<?>, DataComponentType<Fraction>> BUNDLE_WEIGHT = DATA_COMPONENTS.registerSimple("bundle_weight", builder -> builder.persistent(FRACTION_OR_DOUBLE_CODEC).networkSynchronized(FRACTION_STREAM_CODEC));
    /// The weight of a non-empty beehive in a bundle.
    public static final ExtendedHolder<DataComponentType<?>, DataComponentType<Fraction>> BEEHIVE_BUNDLE_WEIGHT = DATA_COMPONENTS.registerSimple("beehive_bundle_weight", builder -> builder.persistent(FRACTION_OR_DOUBLE_CODEC).networkSynchronized(FRACTION_STREAM_CODEC));

    @ApiStatus.Internal
    public static void init() {}
}
