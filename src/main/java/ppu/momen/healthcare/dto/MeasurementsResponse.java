package ppu.momen.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeasurementsResponse {
    private String   patientNumber;
    private Integer  heartRate;
    private Double   bodyTemperature;
    private Integer  systolicPressure;
    private Integer  diastolicPressure;
    private Double   bloodGlucose;
    private Integer  spo2;
    private Instant timestamp;
}