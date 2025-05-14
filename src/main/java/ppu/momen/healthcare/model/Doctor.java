package ppu.momen.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    private String id;
    private String name;

    @Relationship(type = "TREATS")
    private List<PatientNode> patients = new ArrayList<>();
}
