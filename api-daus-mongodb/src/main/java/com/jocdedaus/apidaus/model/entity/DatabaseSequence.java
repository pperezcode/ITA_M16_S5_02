package com.jocdedaus.apidaus.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {
	
	@Id
	private String id;
	private Integer seq;
	
	public DatabaseSequence() {}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}