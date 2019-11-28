package com.queue.demo.domain;

import java.util.List;

public class Record {
 
	private String metedataStandard;
	private String identifier;
	private String url;
	private String state;
	private String errorMessage;
	private List<Metadata> metadatas;
	private List<Relation> relations;
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMetedataStandard() {
		return metedataStandard;
	}

	public void setMetedataStandard(String metedataStandard) {
		this.metedataStandard = metedataStandard;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Metadata> getMetadatas() {
		return metadatas;
	}

	public void setMetadatas(List<Metadata> metadatas) {
		this.metadatas = metadatas;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}

	 
}
