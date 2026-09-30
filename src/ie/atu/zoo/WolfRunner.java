package ie.atu.zoo;

public class WolfRunner {

	
	void main() throws Exception {
		var fac = ZooBuilderFactory.getInstance();
		//var zoo = fac.newZooBuilder(Model.NEURAL_NETWORK);
		  var zoo = fac.newZooBuilder(Model.SUPPORT_VECTOR_MACHINE);
		
	    //(1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 4, 1, 0, 1); 
		Prediction result = zoo.withHair(true)
							   .withFeathers(false)
							   .withEggs(false)
							   .withMilk(true)
							   .withAirborne(false)
							   .withAquatic(false)
							   .withPredator(true)
							   .withToothed(true)
							   .withBackbone(true)
							   .withBreathes(true)
							   .withVenomous(false)
							   .withFins(false)
							   .withLegs(4)
							   .withTail(true)
							   .withDomestic(false)
							   .withCatsize(false)
							   .predict();
		
	System.out.println("Wolf: " + result.predictedClass().label() + " : " + result.confidence() * 100);
							   
				
	}
}
