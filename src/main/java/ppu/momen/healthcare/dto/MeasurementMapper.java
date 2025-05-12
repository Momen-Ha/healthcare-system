package ppu.momen.healthcare.dto;

import ppu.momen.healthcare.model.PatientMeasurement;

public class MeasurementMapper {

    public static PatientMeasurement fromRequest(String patientNumber, MeasurementsRequest req) {
        return PatientMeasurement.builder()
                .patientNumber(patientNumber)
                .heartRate(req.getHeartRate())
                .bodyTemperature(req.getBodyTemperature())
                .systolicPressure(req.getSystolicPressure())
                .diastolicPressure(req.getDiastolicPressure())
                .bloodGlucose(req.getBloodGlucose())
                .spo2(req.getSpo2())
                .build();
    }

    public static MeasurementsResponse toResponse(PatientMeasurement m) {
        return MeasurementsResponse.builder()
                .patientNumber(m.getPatientNumber())
                .heartRate(m.getHeartRate())
                .bodyTemperature(m.getBodyTemperature())
                .systolicPressure(m.getSystolicPressure())
                .diastolicPressure(m.getDiastolicPressure())
                .bloodGlucose(m.getBloodGlucose())
                .spo2(m.getSpo2())
                .timestamp(m.getTimestamp())
                .build();
    }
}

