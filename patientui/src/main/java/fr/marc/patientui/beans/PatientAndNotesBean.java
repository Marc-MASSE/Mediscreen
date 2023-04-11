package fr.marc.patientui.beans;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientAndNotesBean {
	
	private PatientBean patient;
	
	private Iterable<NoteBean> notes;
}
