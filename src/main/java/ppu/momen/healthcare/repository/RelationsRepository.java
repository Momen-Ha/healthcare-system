package ppu.momen.healthcare.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;
import ppu.momen.healthcare.model.Doctor;
import ppu.momen.healthcare.model.PatientNode;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationsRepository extends Neo4jRepository<Doctor, String> {

    @Query("MATCH (d:Doctor {id: $doctorId}) RETURN d")
    Optional<Doctor> findDoctorNodeById(String doctorId);

    @Query("MATCH (p:PatientNode {patientNumber: $patientNumber}) RETURN p")
    Optional<PatientNode> findPatientNodeById(String patientNumber);

    @Query("MATCH (d:Doctor {id: $doctorId})-[:TREATS]->(p:PatientNode) RETURN p")
    List<PatientNode> findPatientsByDoctorId(String doctorId);

    @Query("MATCH (d:Doctor)-[:TREATS]->(p:PatientNode {patientNumber: $patientNumber}) RETURN d")
    List<Doctor> findDoctorsByPatientNumber(String patientNumber);

}

