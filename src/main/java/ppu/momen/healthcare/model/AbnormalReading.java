package ppu.momen.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbnormalReading {
    private String patientNumber;
    private String metric;
    private String measurementValue;
    private Instant timestamp;
}
