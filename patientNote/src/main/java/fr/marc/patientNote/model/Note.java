package fr.marc.patientNote.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notes")
public class Note {
	
	@Id
	private String id;
	
	private Integer patId;
	
    private String body;
    
    public Note(Integer patId, String body) {
        this.patId = patId;
        this.body = body;
      }

}
