package com.queue.demo.domain;

public class Relation {
	
	 
	 
    private String relationId; //关系Id
    private String parentName; //关系名称
    private String relationOptionId; //关系项ID
    private String relationOptionName; //关系项名称
    private String  relationMasterName; //主关系
    private String relationSlaveName; //从关系
    private String relationName; // relationOrder=1时候为主关系名称，relationOrder=0时候为从关系名称，
    private String relationOrder;
    private Record relationRecord;

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getRelationOptionId() {
		return relationOptionId;
	}

	public void setRelationOptionId(String relationOptionId) {
		this.relationOptionId = relationOptionId;
	}

	public String getRelationOptionName() {
		return relationOptionName;
	}

	public void setRelationOptionName(String relationOptionName) {
		this.relationOptionName = relationOptionName;
	}

	public String getRelationMasterName() {
		return relationMasterName;
	}

	public void setRelationMasterName(String relationMasterName) {
		this.relationMasterName = relationMasterName;
	}

	public String getRelationSlaveName() {
		return relationSlaveName;
	}

	public void setRelationSlaveName(String relationSlaveName) {
		this.relationSlaveName = relationSlaveName;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getRelationOrder() {
		return relationOrder;
	}

	public void setRelationOrder(String relationOrder) {
		this.relationOrder = relationOrder;
	}

	public Record getRelationRecord() {
		return relationRecord;
	}

	public void setRelationRecord(Record relationRecord) {
		this.relationRecord = relationRecord;
	}
	
    
}
