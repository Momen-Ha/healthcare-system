package ppu.momen.healthcare.service;

import org.springframework.stereotype.Service;
import ppu.momen.healthcare.dto.PatientRequest;
import ppu.momen.healthcare.model.MedicalInfo;
import ppu.momen.healthcare.model.Patient;
import ppu.momen.healthcare.repository.PatientRepository;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final RelationsService relationsService;

    public PatientService(PatientRepository patientRepository, RelationsService relationsService) {
        this.patientRepository = patientRepository;
        this.relationsService = relationsService;
    }

    private String generatePatientNumber() {
        String year = String.valueOf(Year.now().getValue());
        String randomPart = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "PAT-" + year + "-" + randomPart;
    }

    public Patient createPatient(PatientRequest patientRequest) {
        String patientNumber = generatePatientNumber();
        Patient patient = Patient.builder()
                .patientNumber(patientNumber)
                .fullName(patientRequest.getFullName())
                .dateOfBirth(patientRequest.getDateOfBirth())
                .gender(patientRequest.getGender())
                .nationalId(patientRequest.getNationalId())
                .phoneNumber(patientRequest.getPhoneNumber())
                .address(patientRequest.getAddress())
                .city(patientRequest.getCity())
                .medicalInfo(patientRequest.getMedicalInfo())
                .build();

        relationsService.createPatient(patientNumber, patientRequest.getFullName());
        return patientRepository.save(patient);
    }

    public Patient getPatientByPatientNumber(String patientNumber) {
        Optional<Patient> optionalPatient = patientRepository.findByPatientNumber(patientNumber);
        return optionalPatient.orElseThrow(() -> new RuntimeException("Patient not found with number: " + patientNumber));
    }

    public Patient getPatientByNationalId(String nationalId) {
        Optional<Patient> optionalPatient = patientRepository.findByNationalId(nationalId);
        return optionalPatient.orElseThrow(() -> new RuntimeException("Patient not found with national ID: " + nationalId));
    }
    public void deletePatient(String patientNumber) {
        Patient patient = getPatientByPatientNumber(patientNumber);
        patientRepository.delete(patient);
    }

    public Patient updatePatient(String patientNumber, PatientRequest updatedData) {
        Patient existingPatient = getPatientByPatientNumber(patientNumber);

        if (updatedData.getFullName() != null) {
            existingPatient.setFullName(updatedData.getFullName());
        }
        if (updatedData.getPhoneNumber() != null) {
            existingPatient.setPhoneNumber(updatedData.getPhoneNumber());
        }
        if (updatedData.getAddress() != null) {
            existingPatient.setAddress(updatedData.getAddress());
        }
        if (updatedData.getCity() != null) {
            existingPatient.setCity(updatedData.getCity());
        }

        if (updatedData.getMedicalInfo() != null) {
            MedicalInfo existingMedicalInfo = existingPatient.getMedicalInfo();
            if (existingMedicalInfo == null) {
                existingMedicalInfo = new MedicalInfo();
            }

            MedicalInfo newMedicalInfo = updatedData.getMedicalInfo();

            if (newMedicalInfo.getBloodType() != null) {
                existingMedicalInfo.setBloodType(newMedicalInfo.getBloodType());
            }
            if (newMedicalInfo.getAllergies() != null) {
                existingMedicalInfo.setAllergies(newMedicalInfo.getAllergies());
            }
            if (newMedicalInfo.getCurrentMedications() != null) {
                existingMedicalInfo.setCurrentMedications(newMedicalInfo.getCurrentMedications());
            }
            if (newMedicalInfo.getPastMedicalHistory() != null) {
                existingMedicalInfo.setPastMedicalHistory(newMedicalInfo.getPastMedicalHistory());
            }
            if (newMedicalInfo.getVaccinationRecords() != null) {
                existingMedicalInfo.setVaccinationRecords(newMedicalInfo.getVaccinationRecords());
            }
            if (newMedicalInfo.getCurrentDiagnoses() != null) {
                existingMedicalInfo.setCurrentDiagnoses(newMedicalInfo.getCurrentDiagnoses());
            }
            if (newMedicalInfo.getOngoingTreatments() != null) {
                existingMedicalInfo.setOngoingTreatments(newMedicalInfo.getOngoingTreatments());
            }

            existingPatient.setMedicalInfo(existingMedicalInfo);
        }

        return patientRepository.save(existingPatient);
    }


}
