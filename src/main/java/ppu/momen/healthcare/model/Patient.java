package ppu.momen.healthcare.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "patients")
@Builder
public class Patient {
    @Id
    private String id;

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
