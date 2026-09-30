package ie.atu.zoo;

public enum Model {
	
	LOGISTIC_REGRESSION ("models/zoo_logistic_regression.onnx"),
	NEURAL_NETWORK ("models/zoo_neural_network.onnx"),
	SUPPORT_VECTOR_MACHINE("models/zoo_svm.onnx");
	
	private final String path;
	
	public String getPath() {
		return path;
	}

	
	Model(String path) {
		this.path = path;
	}
	
	


}
