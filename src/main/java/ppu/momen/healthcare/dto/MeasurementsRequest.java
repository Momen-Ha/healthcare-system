package ppu.momen.healthcare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementsRequest {
    private Integer heartRate;
    private Double  bodyTemperature;
    private Integer systolicPressure;
    private Integer diastolicPressure;
    private Double  bloodGlucose;
    private Integer spo2;
}
