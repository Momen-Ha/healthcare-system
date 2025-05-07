package ppu.momen.healthcare.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Data
public class PatientNode {
    @Id
    private String patientNumber;
    private String name;

    @Relationship(type = "TREATS", direction = Relationship.Direction.INCOMING)
    private List<Doctor> doctors;

}
