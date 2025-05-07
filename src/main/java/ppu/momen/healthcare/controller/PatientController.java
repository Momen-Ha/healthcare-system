package ppu.momen.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppu.momen.healthcare.dto.PatientMapper;
import ppu.momen.healthcare.dto.PatientRequest;
import ppu.momen.healthcare.dto.PatientResponse;
import ppu.momen.healthcare.model.Patient;
import ppu.momen.healthcare.service.PatientService;

@RestController
@RequestMapping("/healthcare/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRequest request) {
        Patient saved = patientService.createPatient(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PatientMapper.toResponse(saved));
    }

    @GetMapping("/by-number/{patientNumber}")
    public ResponseEntity<PatientResponse> getByNumber(@PathVariable String patientNumber) {
        Patient p = patientService.getPatientByPatientNumber(patientNumber);
        return ResponseEntity.ok(PatientMapper.toResponse(p));
    }

    @GetMapping("/by-national-id/{nationalId}")
    public ResponseEntity<PatientResponse> getByNationalId(@PathVariable String nationalId) {
        Patient p = patientService.getPatientByNationalId(nationalId);
        return ResponseEntity.ok(PatientMapper.toResponse(p));
    }

    @PutMapping("/{patientNumber}")
    public ResponseEntity<PatientResponse> update(@PathVariable String patientNumber,
                                                  @RequestBody PatientRequest request) {
        Patient updated = patientService.updatePatient(patientNumber, request);
        return ResponseEntity.ok(PatientMapper.toResponse(updated));
    }

    @DeleteMapping("/{patientNumber}")
    public ResponseEntity<Void> delete(@PathVariable String patientNumber) {
        patientService.deletePatient(patientNumber);
        return ResponseEntity.noContent().build();
    }
}

