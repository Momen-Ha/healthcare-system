package ppu.momen.healthcare.fake;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ppu.momen.healthcare.model.Doctor;
import ppu.momen.healthcare.model.MedicalInfo;
import ppu.momen.healthcare.model.Patient;
import ppu.momen.healthcare.model.PatientNode;
import ppu.momen.healthcare.repository.PatientNodeRepository;
import ppu.momen.healthcare.repository.PatientRepository;
import ppu.momen.healthcare.repository.RelationsRepository;
import ppu.momen.healthcare.service.RelationsService;

import java.util.*;


@Component
@RequiredArgsConstructor
public class PatientDataGenerator implements CommandLineRunner {

    private final PatientRepository mongoPatientRepo;
    private final PatientNodeRepository graphPatientRepo;
    private final RelationsRepository relationsRepo;
    private final RelationsService relationsService;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) {
        boolean seedMongo = mongoPatientRepo.count() == 0;
        boolean seedGraph = graphPatientRepo.count() == 0;

        if (!seedMongo && !seedGraph) {
            System.out.println("Data already exists. Skipping seeding.");
            return;
        }

        List<Doctor> doctors = generateFakeDoctors(10);
        relationsRepo.saveAll(doctors);

        List<Patient> patients = generateFakePatients(100);
        if (seedMongo) {
            mongoPatientRepo.saveAll(patients);
            System.out.println("Inserted " + patients.size() + " fake patients into MongoDB!");
        }
        if (seedGraph) {
            List<PatientNode> patientNodes = patients.stream()
                    .map(p -> new PatientNode(p.getPatientNumber(), p.getFullName()))
                    .toList();
            graphPatientRepo.saveAll(patientNodes);
            System.out.println("Inserted " + patientNodes.size() + " PatientNode into Neo4j!");
        }

        for (Patient p : patients) {
            List<String> docIds = new ArrayList<>(doctors.stream().map(Doctor::getId).toList());
            Collections.shuffle(docIds);
            int cnt = new Random().nextInt(3) + 1;
            for (String did : docIds.subList(0, cnt)) {
                relationsService.addPatientToDoctor(did, p.getPatientNumber());
            }
        }
        System.out.println("Patient-doctor relationships created in Neo4j!");
    }

    public List<Doctor> generateFakeDoctors(int count) {
        List<Doctor> doctors = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            doctors.add(new Doctor(UUID.randomUUID().toString(), faker.name().fullName(), new ArrayList<>()));
        }
        return doctors;
    }

    public List<Patient> generateFakePatients(int count) {
        List<Patient> patients = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Patient p = createFakePatient();
            relationsService.createPatient(p.getPatientNumber(), p.getFullName());
            patients.add(p);
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
                .city(faker.options().option("Hebron", "Ramallah", "Jerusalem"))
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


