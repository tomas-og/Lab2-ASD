package ie.atu.zoo;

//Result of one prediction: the winning class plus all 7 class probabilities.
public record Prediction(AnimalClass predictedClass, float[] probabilities) {

    // Confidence (0.0 - 1.0) the model assigned to the predicted class.
    public float confidence() {
        return probabilities[predictedClass.id() - 1];
    }
}