package ppu.momen.healthcare.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ppu.momen.healthcare.model.Doctor;
import ppu.momen.healthcare.model.PatientNode;
import ppu.momen.healthcare.service.RelationsService;

import java.util.List;

@RestController
@RequestMapping("/healthcare/relations")
public class RelationsController {

    private final RelationsService relationsService;

    public RelationsController(RelationsService relationsService) {
        this.relationsService = relationsService;
    }

    @PostMapping("/patients")
    public ResponseEntity<String> createPatient(@RequestParam String patientNumber, @RequestParam String name) {
        try {
            relationsService.createPatient(patientNumber, name);
            return new ResponseEntity<>("Patient created successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/patients")
    public List<PatientNode> allNeo4jPatients() {
        return relationsService.getAllPatientNodes();
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable String doctorId) {
        Doctor doctor = relationsService.getDoctorById(doctorId);
        return ResponseEntity.ok(doctor);
    }

    @GetMapping("patients/{patientNumber}")
    public ResponseEntity<PatientNode> getPatientById(@PathVariable String patientNumber) {
        PatientNode patientNode = relationsService.getPatientById(patientNumber);
        return ResponseEntity.ok(patientNode);
    }

    @PostMapping("/doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestParam String doctorId, @RequestParam String name) {
        Doctor newDoctor = relationsService.createDoctor(doctorId, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
    }

    @PostMapping("/doctor/{doctorId}/{patientNumber}")
    public ResponseEntity<Doctor> addPatientToDoctor(
            @PathVariable String doctorId,
            @PathVariable String patientNumber) {
        Doctor updatedDoctor = relationsService.addPatientToDoctor(doctorId, patientNumber);
        return ResponseEntity.ok(updatedDoctor);
    }

    @GetMapping("/doctor/{doctorId}/patients")
    public ResponseEntity<List<PatientNode>> getPatientsByDoctorId(@PathVariable String doctorId) {
        List<PatientNode> patients = relationsService.getPatientsByDoctorId(doctorId);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/patient/{patientNumber}/doctors")
    public ResponseEntity<List<Doctor>> getDoctorsByPatientNumber(@PathVariable String patientNumber) {
        List<Doctor> doctors = relationsService.getDoctorsByPatientNumber(patientNumber);
        return ResponseEntity.ok(doctors);
    }
}

