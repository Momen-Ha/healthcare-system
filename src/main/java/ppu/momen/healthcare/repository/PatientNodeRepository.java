package ppu.momen.healthcare.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import ppu.momen.healthcare.model.PatientNode;

@Repository
public interface PatientNodeRepository extends Neo4jRepository<PatientNode, String> {

}