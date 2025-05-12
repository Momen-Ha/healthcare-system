package ppu.momen.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppu.momen.healthcare.dto.PatientRequest;
import ppu.momen.healthcare.dto.PatientResponse;
import ppu.momen.healthcare.model.AbnormalReading;
import ppu.momen.healthcare.service.CacheService;
import ppu.momen.healthcare.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/healthcare/patients")
public class PatientController {

    private final PatientService patientService;
    private final CacheService cacheService;

    public PatientController(PatientService patientService, CacheService cacheService) {
        this.patientService = patientService;
        this.cacheService = cacheService;
    }


    @GetMapping
    public ResponseEntity<List<PatientResponse>> getAllPatients() {
        List<PatientResponse> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest request) {
        PatientResponse response = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/by-number/{patientNumber}")
    public ResponseEntity<PatientResponse> getByNumber(@PathVariable String patientNumber) {
        PatientResponse response = patientService.getPatientByPatientNumber(patientNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-national-id/{nationalId}")
    public ResponseEntity<PatientResponse> getByNationalId(@PathVariable String nationalId) {
        PatientResponse response = patientService.getPatientByNationalId(nationalId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{patientNumber}")
    public ResponseEntity<PatientResponse> update(@PathVariable String patientNumber,
                                                  @RequestBody PatientRequest request) {
        PatientResponse response = patientService.updatePatient(patientNumber, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{patientNumber}")
    public ResponseEntity<Void> delete(@PathVariable String patientNumber) {
        patientService.deletePatient(patientNumber);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/abnormal-readings/{patientNumber}")
    public ResponseEntity<List<AbnormalReading>> getPatientAbnormalReadings(@PathVariable String patientNumber) {
        List<AbnormalReading> readings = cacheService.getAbnormalReadings(patientNumber);
        return ResponseEntity.ok(readings);
    }
}

