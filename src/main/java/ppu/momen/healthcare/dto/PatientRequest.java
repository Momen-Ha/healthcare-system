package ppu.momen.healthcare.dto;

import lombok.Data;
import ppu.momen.healthcare.model.MedicalInfo;

import java.time.LocalDate;

@Data
public class PatientRequest {
    private String fullName;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationalId;
    private String phoneNumber;
    private String address;
    private String city;

    MedicalInfo medicalInfo;
}
