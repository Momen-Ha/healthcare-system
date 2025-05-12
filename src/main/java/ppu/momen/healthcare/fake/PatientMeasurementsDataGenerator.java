package ppu.momen.healthcare.fake;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ppu.momen.healthcare.model.Patient;
import ppu.momen.healthcare.model.PatientMeasurement;
import ppu.momen.healthcare.repository.PatientRepository;
import ppu.momen.healthcare.service.PatientMeasurementsService;

import java.time.Instant;
import java.util.List;

@Component
public class PatientMeasurementsDataGenerator implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final PatientMeasurementsService measurementsService;
    private final Faker faker = new Faker();

    public PatientMeasurementsDataGenerator(PatientRepository patientRepository,
                                            PatientMeasurementsService measurementsService) {
        this.patientRepository = patientRepository;
        this.measurementsService = measurementsService;
    }

    @Override
    public void run(String... args) {
        if (patientRepository.count() == 0) {
            System.out.println("No patients found.");
            return;
        }

        System.out.println("Generating measurements for all patients...");

        List<String> patientNumbers = patientRepository.findAll()
                .stream()
                .map(Patient::getPatientNumber)
                .toList();

        for (String patientNumber : patientNumbers) {
            PatientMeasurement measurement = PatientMeasurement.builder()
                    .patientNumber(patientNumber)
                    .heartRate(faker.number().numberBetween(60, 110))
                    .bodyTemperature(faker.number().randomDouble(1, 36, 39))
                    .systolicPressure(faker.number().numberBetween(90, 140))
                    .diastolicPressure(faker.number().numberBetween(60, 90))
                    .bloodGlucose(faker.number().randomDouble(1, 70, 160))
                    .spo2(faker.number().numberBetween(95, 100))
                    .timestamp(Instant.now())
                    .build();

            measurementsService.record(measurement);
        }

        System.out.println("Fake patient measurements inserted!");
    }
}
