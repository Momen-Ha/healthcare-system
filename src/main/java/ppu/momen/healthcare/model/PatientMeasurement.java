package ppu.momen.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientMeasurement {
    private String patientNumber;

    private Integer heartRate;       // bpm
    private Double bodyTemperature;  // °C
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Double bloodGlucose;     // mg/dL
    private Integer spo2;            // %

    private Instant timestamp;
}
