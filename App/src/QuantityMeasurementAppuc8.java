public class QuantityMeasurementAppuc8 {

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(q1.convertTo(LengthUnit.INCH));
        System.out.println(Quantity.add(q1, q2, LengthUnit.FEET));
        System.out.println(new Quantity(36.0, LengthUnit.INCH).equals(new Quantity(1.0, LengthUnit.YARD)));
        System.out.println(new Quantity(1.0, LengthUnit.YARD).add(new Quantity(3.0, LengthUnit.FEET), LengthUnit.YARD));
        System.out.println(new Quantity(2.54, LengthUnit.CENTIMETER).convertTo(LengthUnit.INCH));
        System.out.println(new Quantity(5.0, LengthUnit.FEET).add(new Quantity(0.0, LengthUnit.INCH), LengthUnit.FEET));
        System.out.println(LengthUnit.FEET.convertToBaseUnit(12.0));
        System.out.println(LengthUnit.INCH.convertToBaseUnit(12.0));
    }
}

enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

class Quantity {

    private final double value;
    private final LengthUnit unit;

    public Quantity(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value))
            throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    public Quantity convertTo(LengthUnit targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException();
        double base = toBase();
        double converted = targetUnit.convertFromBaseUnit(base);
        return new Quantity(converted, targetUnit);
    }

    public Quantity add(Quantity other) {
        if (other == null)
            throw new IllegalArgumentException();
        return add(this, other, this.unit);
    }

    public Quantity add(Quantity other, LengthUnit targetUnit) {
        return add(this, other, targetUnit);
    }

    public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
        if (q1 == null || q2 == null || targetUnit == null)
            throw new IllegalArgumentException();
        double sum = q1.toBase() + q2.toBase();
        double result = targetUnit.convertFromBaseUnit(sum);
        return new Quantity(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quantity other = (Quantity) obj;
        return Double.compare(this.toBase(), other.toBase()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(toBase());
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}