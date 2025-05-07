package ppu.momen.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalInfo {
    private String bloodType;
    private List<String> allergies;
    private List<String> currentMedications;
    private List<String> pastMedicalHistory;
    private List<String> vaccinationRecords;
    private List<String> currentDiagnoses;
    private List<String> ongoingTreatments;
}
