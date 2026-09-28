package ie.atu.zoo;

/*
 * This record represents the 16 input features for an animal (UCI Zoo style), in 
 * the **exact order** the ONNX models expect them. This order must 
 * match FEATURE_COLUMNS in the Python training scripts (train_logistic_regression.py /
 * train_neural_network.py). The model has no idea what each column "means", it 
 * just expects 16 numbers in a fixed order.
 *
 * Every field except "legs" is really a 0/1 boolean flag, but
 * they are stored as int because that is what the CSV and the ONNX model both use.
 * 
 *
 * Derived creation, with(feature, value), in pseudocode:
 *   1. Copy the components into an int[16] in declaration order (toArray).
 *   2. Overwrite the single slot at feature.ordinal().
 *   3. Rebuild the record from the array (fromArray), which calls the canonical constructor.
 *
 */
public record AnimalFeatures(int hair, int feathers, int eggs, int milk, int airborne,
                             int aquatic, int predator, int toothed, int backbone,
                             int breathes, int venomous, int fins, int legs, int tail,
                             int domestic, int catsize) {

	// One constant per component, in EXACTLY the same order as the record header.
	public enum Feature {
		HAIR, FEATHERS, EGGS, MILK, AIRBORNE, AQUATIC, PREDATOR, TOOTHED, BACKBONE, BREATHES, VENOMOUS, FINS, LEGS,
		TAIL, DOMESTIC, CATSIZE
	}

	// Used in withers method below. Note the positional order must be an exact match for ONNX.
	private int[] toArray() {
		return new int[] { hair, feathers, eggs, milk, airborne, aquatic, predator, toothed, backbone, breathes,
				venomous, fins, legs, tail, domestic, catsize };
	}

	/*
	 * This method illustrates record re-construction used by hand-written withers
	 * methods below. JEP 468: Derived Record Creation (Preview) provides for a
	 * "with" statement to simplify this, but the specification has only "candidate"
	 * status. We will be able to do the following in the future:
	 * 
	 * return this with {airborne = 1};
	 * 
	 * Note the positional order must be an exact match for ONNX.
	 */
	private AnimalFeatures fromArray(int[] vector) {
		return new AnimalFeatures(vector[0], vector[1], vector[2], vector[3], vector[4], vector[5], vector[6],
				vector[7], vector[8], vector[9], vector[10], vector[11], vector[12], vector[13], vector[14],
				vector[15]);
	}

	/*
	 * Converts these features into the flat float array ONNX Runtime needs for a
	 * single-row input tensor.
	 */
	public float[] toFloatArray() {
		return new float[] { hair, feathers, eggs, milk, airborne, aquatic, predator, toothed, backbone, breathes,
				venomous, fins, legs, tail, domestic, catsize };
	}

	// General withers method: Returns a copy with one component replaced.
	public AnimalFeatures with(Feature feature, int value) {
		int[] values = toArray();
		values[feature.ordinal()] = value;
		return fromArray(values);
	}

	// General withers method: Boolean convenience: true becomes 1, false becomes 0
	public AnimalFeatures with(Feature feature, boolean present) {
		return with(feature, present ? 1 : 0);
	}

    //Named withers: one line each, with no positional arguments to get wrong. ----
    public AnimalFeatures withHair(boolean hair)         { return with(Feature.HAIR, hair); }
    public AnimalFeatures withFeathers(boolean feathers) { return with(Feature.FEATHERS, feathers); }
    public AnimalFeatures withEggs(boolean eggs)         { return with(Feature.EGGS, eggs); }
    public AnimalFeatures withMilk(boolean milk)         { return with(Feature.MILK, milk); }
    public AnimalFeatures withAirborne(boolean airborne) { return with(Feature.AIRBORNE, airborne); }
    public AnimalFeatures withAquatic(boolean aquatic)   { return with(Feature.AQUATIC, aquatic); }
    public AnimalFeatures withPredator(boolean predator) { return with(Feature.PREDATOR, predator); }
    public AnimalFeatures withToothed(boolean toothed)   { return with(Feature.TOOTHED, toothed); }
    public AnimalFeatures withBackbone(boolean backbone) { return with(Feature.BACKBONE, backbone); }
    public AnimalFeatures withBreathes(boolean breathes) { return with(Feature.BREATHES, breathes); }
    public AnimalFeatures withVenomous(boolean venomous) { return with(Feature.VENOMOUS, venomous); }
    public AnimalFeatures withFins(boolean fins)         { return with(Feature.FINS, fins); }
    public AnimalFeatures withLegs(int legs)             { return with(Feature.LEGS, legs); }  // a count, not a flag
    public AnimalFeatures withTail(boolean tail)         { return with(Feature.TAIL, tail); }
    public AnimalFeatures withDomestic(boolean domestic) { return with(Feature.DOMESTIC, domestic); }
    public AnimalFeatures withCatsize(boolean catsize)   { return with(Feature.CATSIZE, catsize); }
}