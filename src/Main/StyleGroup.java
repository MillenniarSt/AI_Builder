package Main;

public class StyleGroup {

	private String id;
	private RandomCollection<String> random;
	
	public StyleGroup(String id) {
		this.id = id;
		this.random = new RandomCollection<>();
	}
	public StyleGroup(String id, RandomCollection<String> random) {
		this.id = id;
		this.random = random;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public RandomCollection<String> getRandom() {
		return random;
	}
	public void setRandom(RandomCollection<String> random) {
		this.random = random;
	}
}
