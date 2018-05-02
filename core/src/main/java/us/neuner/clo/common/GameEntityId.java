package us.neuner.clo.common;

public enum GameEntityId {
	InvalidValue("Invalid Value"),
	MissScarlet("Miss Scarlet")
	;
	//TODO: fill out remaining GameEntityIds
	
	private final String text;
	
	GameEntityId(final String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}
