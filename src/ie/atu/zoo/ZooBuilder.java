package ie.atu.zoo;

public interface ZooBuilder {
	
	Prediction predict() throws Exception;

	ZooBuilder withCatsize(boolean catsize);

	ZooBuilder withDomestic(boolean domestic);

	ZooBuilder withTail(boolean tail);

	ZooBuilder withLegs(int legs);

	ZooBuilder withFins(boolean fins);

	ZooBuilder withVenomous(boolean venomous);

	ZooBuilder withBreathes(boolean breathes);

	ZooBuilder withBackbone(boolean backbone);

	ZooBuilder withToothed(boolean toothed);

	ZooBuilder withPredator(boolean predator);

	ZooBuilder withAquatic(boolean aquatic);

	ZooBuilder withAirborne(boolean airborne);

	ZooBuilder withMilk(boolean milk);

	ZooBuilder withEggs(boolean eggs);

	ZooBuilder withFeathers(boolean feathers);

	ZooBuilder withHair(boolean hair);

}
