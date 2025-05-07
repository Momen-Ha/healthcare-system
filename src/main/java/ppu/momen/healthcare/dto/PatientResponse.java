package ppu.momen.healthcare.dto;

import lombok.Builder;
import lombok.Data;
import ppu.momen.healthcare.model.MedicalInfo;

import java.time.LocalDate;

@Data
@Builder
public class PatientResponse {
    private String patientNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationalId;
    private String phoneNumber;
    private String address;
    private String city;

    private MedicalInfo medicalInfo;
}