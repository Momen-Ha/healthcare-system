package ppu.momen.healthcare.service;

import org.springframework.stereotype.Service;
import ppu.momen.healthcare.model.Doctor;
import ppu.momen.healthcare.model.PatientNode;
import ppu.momen.healthcare.repository.PatientNodeRepository;
import ppu.momen.healthcare.repository.RelationsRepository;

import java.util.List;

@Service
public class RelationsService {

    private final RelationsRepository relationsRepository;
    private final PatientNodeRepository patientNodeRepository;

    public RelationsService(RelationsRepository relationsRepository, PatientNodeRepository patientNodeRepository) {
        this.relationsRepository = relationsRepository;
        this.patientNodeRepository = patientNodeRepository;
    }

    public List<PatientNode> getAllPatientNodes() {
        return patientNodeRepository.findAll();
    }

    public Doctor getDoctorById(String doctorId) {
        return relationsRepository.findDoctorNodeById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));
    }

    public PatientNode getPatientById(String patientNumber) {
        return relationsRepository.findPatientNodeById(patientNumber)
                .orElseThrow(() -> new RuntimeException("Patient not found with patientNumber: " + patientNumber));
    }

    public Doctor createDoctor(String doctorId, String name) {
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName(name);
        return relationsRepository.save(doctor);
    }

    public void createPatient(String patientNumber, String name) {
        PatientNode patientNode = new PatientNode();
        patientNode.setPatientNumber(patientNumber);
        patientNode.setName(name);
        patientNodeRepository.save(patientNode);

        boolean exists = patientNodeRepository.existsById(patientNumber);
        if (!exists) {
            throw new RuntimeException("Failed to create PatientNode " + patientNumber);
        }
    }

    public Doctor addPatientToDoctor(String doctorId, String patientNumber) {
        Doctor doctor = getDoctorById(doctorId);
        PatientNode patientNode = patientNodeRepository.findById(patientNumber)
                .orElseThrow(() -> new RuntimeException("No such patient: " + patientNumber));


        // Avoid duplicates
        boolean exists = doctor.getPatients().stream()
                .anyMatch(p -> p.getPatientNumber().equals(patientNode.getPatientNumber()));
        if (!exists) {
            doctor.getPatients().add(patientNode);
            return relationsRepository.save(doctor);
        } else {
            return doctor;
        }
    }

    public List<PatientNode> getPatientsByDoctorId(String doctorId) {
        return relationsRepository.findPatientsByDoctorId(doctorId);
    }

    public List<Doctor> getDoctorsByPatientNumber(String patientNumber) {
        return relationsRepository.findDoctorsByPatientNumber(patientNumber);
    }
}
