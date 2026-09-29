package ie.atu.zoo;

public class DefaultZooBuilder implements ZooBuilder {
	private Model model = Model.NEURAL_NETWORK;
	private AnimalFeatures features;
	
	public DefaultZooBuilder(Model model) {
		super();
		this.model = model;
		this.features = new AnimalFeatures(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public Prediction predict() throws Exception {
		try(var predictor = new ZooOnnxPredictor(model.getPath())) {
			return predictor.predict(features);
		}
	}
	

	@Override
	public ZooBuilder withHair(boolean hair) {
		features = features.withHair(hair);
		return this;
	}

	@Override
	public ZooBuilder withFeathers(boolean feathers) {
		features = features.withFeathers(feathers);
		return this;
	}

	@Override
	public ZooBuilder withEggs(boolean eggs) {
		features = features.withEggs(eggs);
		return this;
	}

	@Override
	public ZooBuilder withMilk(boolean milk) {
		features = features.withMilk(milk);
		return this;
	}

	@Override
	public ZooBuilder withAirborne(boolean airborne) {
		features = features.withAirborne(airborne);
		return this;
	}

	@Override
	public ZooBuilder withAquatic(boolean aquatic) {
		features = features.withAquatic(aquatic);
		return this;
	}

	@Override
	public ZooBuilder withPredator(boolean predator) {
		features = features.withPredator(predator);
		return this;
	}

	@Override
	public ZooBuilder withToothed(boolean toothed) {
		features = features.withToothed(toothed);
		return this;
	}

	@Override
	public ZooBuilder withBackbone(boolean backbone) {
		features = features.withBackbone(backbone);
		return this;
	}

	@Override
	public ZooBuilder withBreathes(boolean breathes) {
		features = features.withBreathes(breathes);
		return this;
	}

	@Override
	public ZooBuilder withVenomous(boolean venomous) {
		features = features.withVenomous(venomous);
		return this;
	}

	@Override
	public ZooBuilder withFins(boolean fins) {
		features = features.withFins(fins);
		return this;
	}

	@Override
	public ZooBuilder withLegs(int legs) {
		features = features.withLegs(legs);
		return this;
	}

	@Override
	public ZooBuilder withTail(boolean tail) {
		features = features.withTail(tail);
		return this;
	}

	@Override
	public ZooBuilder withDomestic(boolean domestic) {
		features = features.withDomestic(domestic);
		return this;
	}

	@Override
	public ZooBuilder withCatsize(boolean catsize) {
		features = features.withCatsize(catsize);
		return this;
	}
	
	

}
