package ppu.momen.healthcare.configuration;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ppu.momen.healthcare.model.MedicalInfo;
import ppu.momen.healthcare.model.Patient;
import ppu.momen.healthcare.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class PatientDataGenerator implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        if (patientRepository.count() == 0) {
            List<Patient> patients = generateFakePatients(100);
            patientRepository.saveAll(patients);
            System.out.println("Inserted 100 fake patients!");
        } else {
            System.out.println("Patients already exist. Skipping seeding.");
        }
    }

    public List<Patient> generateFakePatients(int count) {
        List<Patient> patients = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            patients.add(createFakePatient());
        }

        return patients;
    }

    private Patient createFakePatient() {
        return Patient.builder()
                .patientNumber(generatePatientNumber())
                .fullName(faker.name().fullName())
                .dateOfBirth(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                .gender(faker.options().option("Male", "Female"))
                .nationalId(faker.idNumber().valid())
                .phoneNumber(faker.phoneNumber().phoneNumber())
                .address(faker.address().streetAddress())
                .city(faker.address().city())
                .medicalInfo(generateFakeMedicalInfo())
                .build();
    }

    private MedicalInfo generateFakeMedicalInfo() {
        return new MedicalInfo(
                faker.options().option("O+", "A+", "B+", "AB+", "O-", "A-", "B-", "AB-"),
                faker.lorem().words(3),
                faker.lorem().words(3),
                faker.lorem().words(3),
                faker.lorem().words(3),
                faker.lorem().words(3),
                faker.lorem().words(3)
        );
    }

    private String generatePatientNumber() {
        String year = String.valueOf(java.time.Year.now().getValue());
        String randomPart = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return "PAT-" + year + "-" + randomPart;
    }
}


