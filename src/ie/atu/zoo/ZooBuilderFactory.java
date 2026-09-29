package ie.atu.zoo;

public class ZooBuilderFactory {
	private static ZooBuilderFactory fact = new ZooBuilderFactory();

	private ZooBuilderFactory() {
		
	}
	
	public static ZooBuilderFactory getInstance() {
		return fact;
	}
	
	public ZooBuilder newZooBuilder (Model name) {
		return new DefaultZooBuilder(name);
	}
}
