package ppu.momen.healthcare.dto;

import ppu.momen.healthcare.model.MedicalInfo;
import ppu.momen.healthcare.model.Patient;

public class PatientMapper {

    public static PatientResponse toResponse(Patient patient) {
        PatientResponse.PatientResponseBuilder builder = PatientResponse.builder()
                .patientNumber(patient.getPatientNumber())
                .fullName(patient.getFullName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .nationalId(patient.getNationalId())
                .phoneNumber(patient.getPhoneNumber())
                .address(patient.getAddress())
                .city(patient.getCity());

        MedicalInfo mi = patient.getMedicalInfo();
        if (mi != null) {
            MedicalInfo copy = new MedicalInfo();
            copy.setBloodType(mi.getBloodType());
            copy.setAllergies(mi.getAllergies());
            copy.setCurrentMedications(mi.getCurrentMedications());
            copy.setPastMedicalHistory(mi.getPastMedicalHistory());
            copy.setVaccinationRecords(mi.getVaccinationRecords());
            copy.setCurrentDiagnoses(mi.getCurrentDiagnoses());
            copy.setOngoingTreatments(mi.getOngoingTreatments());
            builder.medicalInfo(copy);
        }

        return builder.build();
    }
}
