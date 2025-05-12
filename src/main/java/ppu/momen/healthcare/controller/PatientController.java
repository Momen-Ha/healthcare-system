package ppu.momen.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppu.momen.healthcare.dto.PatientRequest;
import ppu.momen.healthcare.dto.PatientResponse;
import ppu.momen.healthcare.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/healthcare/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
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
}

