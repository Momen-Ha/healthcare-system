package ppu.momen.healthcare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ppu.momen.healthcare.model.Patient;

import java.util.Optional;

public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByPatientNumber(String patientNumber);

    Optional<Patient> findByNationalId(String nationalId);
}
