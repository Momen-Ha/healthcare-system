package ppu.momen.healthcare.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ppu.momen.healthcare.model.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByPatientNumber(String patientNumber);

    Optional<Patient> findByNationalId(String nationalId);
}
