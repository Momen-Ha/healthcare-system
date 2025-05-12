package ppu.momen.healthcare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppu.momen.healthcare.dto.MeasurementMapper;
import ppu.momen.healthcare.dto.MeasurementsRequest;
import ppu.momen.healthcare.dto.MeasurementsResponse;
import ppu.momen.healthcare.service.PatientMeasurementsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/healthcare/patient/measurements")
public class PatientMeasurementsController {
    private final PatientMeasurementsService service;

    public PatientMeasurementsController(PatientMeasurementsService service) {
        this.service = service;
    }

    @PostMapping("/{patientNumber}")
    public ResponseEntity<Void> record(
            @PathVariable String patientNumber,
            @RequestBody MeasurementsRequest req) {

        service.record(MeasurementMapper.fromRequest(patientNumber, req));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{patientNumber}")
    public ResponseEntity<List<MeasurementsResponse>> latest(
            @PathVariable String patientNumber,
            @RequestParam(defaultValue = "10") int limit) {

        List<MeasurementsResponse> dtoList = service.getLatest(patientNumber, limit).stream()
                .map(MeasurementMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }
}