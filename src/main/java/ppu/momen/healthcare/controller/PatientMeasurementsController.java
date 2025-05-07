package ppu.momen.healthcare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppu.momen.healthcare.model.PatientMeasurement;
import ppu.momen.healthcare.service.PatientMeasurementsService;

import java.util.List;

@RestController
@RequestMapping("/healthcare/measurements")
public class PatientMeasurementsController {
    private final PatientMeasurementsService service;

    public PatientMeasurementsController(PatientMeasurementsService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> record(@RequestBody PatientMeasurement m) {
        service.record(m);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{patientNumber}")
    public ResponseEntity<List<PatientMeasurement>> latest(
            @PathVariable String patientNumber,
            @RequestParam(defaultValue="10") int limit) {
        return ResponseEntity.ok(service.getLatest(patientNumber, limit));
    }
}