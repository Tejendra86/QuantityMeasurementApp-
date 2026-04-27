import java.util.Objects;

enum LengthUnitUC9 {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnitUC9(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

enum WeightUnitUC9 {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    WeightUnitUC9(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}

final class QuantityLengthUC9 {
    private final double value;
    private final LengthUnitUC9 unit;

    public QuantityLengthUC9(double value, LengthUnitUC9 unit) {
        if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public QuantityLengthUC9 convertTo(LengthUnitUC9 target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.toBase(value);
        return new QuantityLengthUC9(target.fromBase(base), target);
    }

    public QuantityLengthUC9 add(QuantityLengthUC9 other) {
        return add(other, this.unit);
    }

    public QuantityLengthUC9 add(QuantityLengthUC9 other, LengthUnitUC9 target) {
        if (other == null || target == null) throw new IllegalArgumentException();
        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        return new QuantityLengthUC9(target.fromBase(sum), target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLengthUC9 other = (QuantityLengthUC9) obj;
        double a = unit.toBase(value);
        double b = other.unit.toBase(other.value);
        return Math.abs(a - b) < 1e-6;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.toBase(value));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

final class QuantityWeightUC9 {
    private final double value;
    private final WeightUnitUC9 unit;

    public QuantityWeightUC9(double value, WeightUnitUC9 unit) {
        if (unit == null || !Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public QuantityWeightUC9 convertTo(WeightUnitUC9 target) {
        if (target == null) throw new IllegalArgumentException();
        double base = unit.toBase(value);
        return new QuantityWeightUC9(target.fromBase(base), target);
    }

    public QuantityWeightUC9 add(QuantityWeightUC9 other) {
        return add(other, this.unit);
    }

    public QuantityWeightUC9 add(QuantityWeightUC9 other, WeightUnitUC9 target) {
        if (other == null || target == null) throw new IllegalArgumentException();
        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        return new QuantityWeightUC9(target.fromBase(sum), target);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityWeightUC9 other = (QuantityWeightUC9) obj;
        double a = unit.toBase(value);
        double b = other.unit.toBase(other.value);
        return Math.abs(a - b) < 1e-6;
    }

    @Override
    public int hashCode() {
        return Objects.hash(unit.toBase(value));
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

public class QuantityMeasurementAppuc9 {
    public static void main(String[] args) {

        QuantityLengthUC9 l1 = new QuantityLengthUC9(1, LengthUnitUC9.FEET);
        QuantityLengthUC9 l2 = new QuantityLengthUC9(12, LengthUnitUC9.INCHES);

        System.out.println(l1.equals(l2));
        System.out.println(l1.add(l2));
        System.out.println(l1.add(l2, LengthUnitUC9.YARDS));
        System.out.println(l1.convertTo(LengthUnitUC9.INCHES));

        QuantityWeightUC9 w1 = new QuantityWeightUC9(1, WeightUnitUC9.KILOGRAM);
        QuantityWeightUC9 w2 = new QuantityWeightUC9(1000, WeightUnitUC9.GRAM);

        System.out.println(w1.equals(w2));
        System.out.println(w1.add(w2));
        System.out.println(w1.add(w2, WeightUnitUC9.POUND));
        System.out.println(w1.convertTo(WeightUnitUC9.GRAM));
    }
}