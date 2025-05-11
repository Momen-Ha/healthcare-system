package ppu.momen.healthcare.service;

import ppu.momen.healthcare.model.PatientMeasurement;

public class AbnormalMeasurementsChecker {

    private final CacheService cacheService;

    public AbnormalMeasurementsChecker(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void checkMeasurements(PatientMeasurement patientMeasurement) {
        
    }
}
