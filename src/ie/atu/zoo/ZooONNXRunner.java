package ie.atu.zoo;

import static java.lang.System.*;

public class ZooONNXRunner {
	private final String ZOO_LR_MODEL_PATH = "models/zoo_logistic_regression.onnx";
	private final String ZOO_NN_MODEL_PATH = "models/zoo_neural_network.onnx";
	
    private void go() throws Exception {
        //Features: hair,feathers,eggs,milk,airborne,aquatic,predator,toothed,
        //          backbone,breathes,venomous,fins,legs,tail,domestic,catsize
        
    	//Test with values copied directly from data in zoo.csv.
        var wolf = new AnimalFeatures(1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 4, 1, 0, 1);
        var chicken = new AnimalFeatures(0, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 2, 1, 1, 0);
        var octopus = new AnimalFeatures(0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 8, 0, 0, 1);

        out.println("------- Logistric Regression Classifier -------");
        try (var lr = new ZooOnnxPredictor(ZOO_LR_MODEL_PATH)) {
        	classify(lr, "wolf", wolf);
            classify(lr, "chicken", chicken);
            classify(lr, "octopus", octopus);
        }

        out.println("\n------- Neural Network Classifier -------");
        try (var nn = new ZooOnnxPredictor(ZOO_NN_MODEL_PATH)) {
        	classify(nn, "wolf", wolf);
            classify(nn, "chicken", chicken);
            classify(nn, "octopus", octopus);
        }

    }

    private void classify(ZooOnnxPredictor predictor, String animalName, AnimalFeatures features) throws Exception {
        Prediction prediction = predictor.predict(features);
        System.out.printf(
                "%-10s -> predicted: %-13s (confidence %.1f%%)%n",
                animalName,
                prediction.predictedClass().label(),
                prediction.confidence() * 100);
    }
    
    void main() throws Exception {
    	new ZooONNXRunner().go();
    }
}