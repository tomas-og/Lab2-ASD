package ie.atu.zoo;

import java.nio.FloatBuffer;
import java.util.Map;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtException;
import ai.onnxruntime.OrtSession;

/*
 * Loads and runs predictions against one of the exported Zoo ONNX models.
 *
 * Both zoo_logistic_regression.onnx and zoo_neural_network.onnx
 * were exported with the same input/output shape (see train_logistic_regression.py 
 * and train_neural_network.py), so this one class works for either model. It only 
 * needs to be pointed at a different file path.
 *
 * ZooOnnxPredictor implements AutoCloseable so it can be used in a
 * try-with-resources block, which guarantees the native ONNX Runtime
 * session is released even if an exception is thrown.
 */
public final class ZooOnnxPredictor implements AutoCloseable{
    /*
     * OrtEnvironment is a shared, process-wide resource managed by ONNX Runtime itself, 
     * so unlike the session below, we do not close it here.
     */
    private final OrtEnvironment environment;
    private final OrtSession session;
    

    //Loads the ONNX model at the given path.
    public ZooOnnxPredictor(String onnxModelPath) throws OrtException {
        this.environment = OrtEnvironment.getEnvironment();
        this.session = environment.createSession(onnxModelPath, new OrtSession.SessionOptions());
    }

    
    /*
     * Runs the model on one animal's features and returns the
     * predicted class plus the full probability distribution.
     *
     *
     * PSEUDOCODE:
     *   1) convert the 16 features into a flat float array
     *   2) wrap the array as a [1 x 16] ONNX tensor ("batch of 1 animal")
     *   3) run the model with that tensor as input "input"
     *   4) read the "label" output -> the predicted class id
     *   5) read the "probabilities" output -> confidence per class
     *   6) look up the class id in AnimalClass and return both
     */
    public Prediction predict(AnimalFeatures features) throws OrtException {
        float[] inputValues = features.toFloatArray();
        long[] shape = {1, inputValues.length}; // 1 row, 16 columns

        try (OnnxTensor inputTensor = OnnxTensor.createTensor(environment, FloatBuffer.wrap(inputValues), shape)){
            Map<String, OnnxTensor> modelInputs = Map.of("input", inputTensor);
            
            try (OrtSession.Result result = session.run(modelInputs)) {
                long[] labelOutput = (long[]) result.get("label").orElseThrow().getValue();
                float[][] probabilityOutput = (float[][]) result.get("probabilities").orElseThrow().getValue();

                AnimalClass predictedClass = AnimalClass.fromId(labelOutput[0]);
                return new Prediction(predictedClass, probabilityOutput[0]);
            }
        }
    }

    // AutoCloseable requires a close() method.
    public void close() throws OrtException {
        session.close();
    }
}