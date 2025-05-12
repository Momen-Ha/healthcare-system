package ppu.momen.healthcare.service;

import org.springframework.stereotype.Component;
import ppu.momen.healthcare.model.AbnormalReading;
import ppu.momen.healthcare.model.PatientMeasurement;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AbnormalMeasurementsChecker {

    private final CacheService cacheService;

    private static final double HR_MIN = 60,     HR_MAX = 100;
    private static final double TEMP_MIN = 36.1, TEMP_MAX = 37.2;
    private static final double SYS_PRESSURE_MIN  = 90,  SYS_PRESSURE_MAX  = 120;
    private static final double DIA_PRESSURE_MIN  = 60,  DIA_PRESSURE_MAX  = 80;
    private static final double GLUCOSE_MIN = 70,  GLUCOSE_MAX = 140;
    private static final double SPO2_MIN    = 95,  SPO2_MAX    = 100;

    public AbnormalMeasurementsChecker(CacheService cacheService) {
        this.cacheService = cacheService;
    }


    public void checkMeasurements(PatientMeasurement m) {
        String patientNumber = m.getPatientNumber();
        Instant ts = Optional.ofNullable(m.getTimestamp()).orElse(Instant.now());

        List<AbnormalReading> batch = new ArrayList<>();

        if (m.getHeartRate() < HR_MIN || m.getHeartRate() > HR_MAX) {
            batch.add(new AbnormalReading(patientNumber, "heartRate",
                    String.valueOf(m.getHeartRate()), ts));
        }
        if (m.getBodyTemperature() < TEMP_MIN || m.getBodyTemperature() > TEMP_MAX) {
            batch.add(new AbnormalReading(patientNumber, "bodyTemperature",
                    String.valueOf(m.getBodyTemperature()), ts));
        }
        if (m.getSystolicPressure() < SYS_PRESSURE_MIN || m.getSystolicPressure() > SYS_PRESSURE_MAX) {
            batch.add(new AbnormalReading(patientNumber, "systolicPressure",
                    String.valueOf(m.getSystolicPressure()), ts));
        }
        if (m.getDiastolicPressure() < DIA_PRESSURE_MIN || m.getDiastolicPressure() > DIA_PRESSURE_MAX) {
            batch.add(new AbnormalReading(patientNumber, "diastolicPressure",
                    String.valueOf(m.getDiastolicPressure()), ts));
        }
        if (m.getBloodGlucose() < GLUCOSE_MIN || m.getBloodGlucose() > GLUCOSE_MAX) {
            batch.add(new AbnormalReading(patientNumber, "bloodGlucose",
                    String.valueOf(m.getBloodGlucose()), ts));
        }
        if (m.getSpo2() < SPO2_MIN || m.getSpo2() > SPO2_MAX) {
            batch.add(new AbnormalReading(patientNumber, "spo2",
                    String.valueOf(m.getSpo2()), ts));
        }

        cacheService.cacheAbnormalReadings(patientNumber, batch);
    }
}


