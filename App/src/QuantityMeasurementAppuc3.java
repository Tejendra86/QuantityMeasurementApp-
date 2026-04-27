public class QuantityMeasurementAppuc3 {

    // Step 1: Enum for Units
    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);  // 1 inch = 1/12 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Step 2: Generic Quantity Class
    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        // Convert to base unit (feet)
        private double toFeet() {
            return unit.toFeet(value);
        }

        // Override equals()
        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) return true;

            // Null or type mismatch
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            // Compare after converting to common unit (feet)
            return Double.compare(this.toFeet(), other.toFeet()) == 0;
        }

        // Recommended when equals is overridden
        @Override
        public int hashCode() {
            return Double.hashCode(toFeet());
        }
    }

    // Main method
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("1 ft == 12 inch : " + q1.equals(q2)); // true
        System.out.println("1 inch == 1 inch : " + q3.equals(q4)); // true

        Quantity q5 = new Quantity(2.0, LengthUnit.FEET);
        System.out.println("1 ft == 2 ft : " + q1.equals(q5)); // false
    }
}