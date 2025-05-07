package ppu.momen.healthcare.model;

import lombok.Data;

import java.util.List;

@Data
public class MedicalInfo {
    private String bloodType;
    private List<String> allergies;
    private List<String> currentMedications;
    private List<String> pastMedicalHistory;
    private List<String> vaccinationRecords;
    private List<String> currentDiagnoses;
    private List<String> ongoingTreatments;
}
