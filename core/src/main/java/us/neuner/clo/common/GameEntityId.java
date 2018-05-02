package us.neuner.clo.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GameEntityId {
	InvalidValue("Invalid Value"),
	MissScarlet("Miss Scarlet")
	;
	//TODO: fill out remaining GameEntityIds
	
	private final String text;
	
	GameEntityId(final String text) {
		this.text = text;
	}
	
	@JsonValue
	@Override
	public String toString() {
		return text;
	}
}
