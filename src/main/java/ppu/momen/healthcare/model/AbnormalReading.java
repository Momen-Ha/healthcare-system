package ppu.momen.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalReading {
    private String patientNumber;
    private String measurementName;
    private String measurementValue;
}
