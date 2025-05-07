package ppu.momen.healthcare.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
public class Doctor {
    @Id
    private String id;
    private String name;

    @Relationship(type = "TREATS")
    private List<PatientNode> patients = new ArrayList<>();
}
