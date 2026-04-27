public class QuantityMeasurementAppuc7 {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value))
                throw new IllegalArgumentException();
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toFeet(value);
        }

        public Quantity add(Quantity other) {
            if (other == null)
                throw new IllegalArgumentException();
            return add(this, other, this.unit);
        }

        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {
            if (q1 == null || q2 == null || targetUnit == null)
                throw new IllegalArgumentException();
            double sumFeet = q1.toFeet() + q2.toFeet();
            double resultValue = targetUnit.fromFeet(sumFeet);
            return new Quantity(resultValue, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Quantity other = (Quantity) obj;
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(Quantity.add(q1, q2, LengthUnit.FEET));
        System.out.println(Quantity.add(q1, q2, LengthUnit.INCH));
        System.out.println(Quantity.add(q1, q2, LengthUnit.YARD));

        Quantity q3 = new Quantity(1.0, LengthUnit.YARD);
        Quantity q4 = new Quantity(3.0, LengthUnit.FEET);

        System.out.println(Quantity.add(q3, q4, LengthUnit.YARD));

        Quantity q5 = new Quantity(36.0, LengthUnit.INCH);
        Quantity q6 = new Quantity(1.0, LengthUnit.YARD);

        System.out.println(Quantity.add(q5, q6, LengthUnit.FEET));

        Quantity q7 = new Quantity(2.54, LengthUnit.CENTIMETER);
        Quantity q8 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println(Quantity.add(q7, q8, LengthUnit.CENTIMETER));
    }
}