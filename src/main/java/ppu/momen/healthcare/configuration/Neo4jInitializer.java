package ppu.momen.healthcare.configuration;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jInitializer implements CommandLineRunner {
    private final Driver driver;

    public Neo4jInitializer(Driver driver) {
        this.driver = driver;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Session session = driver.session()) {
            session.run("CREATE CONSTRAINT patientnode_patientnumber IF NOT EXISTS FOR (p:PatientNode) REQUIRE p.patientNumber IS UNIQUE");
            session.run("CREATE CONSTRAINT doctor_id IF NOT EXISTS FOR (d:Doctor) REQUIRE d.id IS UNIQUE");
        }
    }
}