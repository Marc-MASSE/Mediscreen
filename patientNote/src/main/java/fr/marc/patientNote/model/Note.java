package fr.marc.patientNote.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime date;

	private String body;

	public Note(Integer patId, LocalDateTime date, String body) {
		this.patId = patId;
		this.date = date;
		this.body = body;
	}

}
