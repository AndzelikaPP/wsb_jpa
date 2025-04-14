package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.PatientEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public final class PatientMapper {

    public static PatientTO mapToTO(PatientEntity entity) {
        if (entity == null) return null;

        PatientTO to = new PatientTO();
        to.setId(entity.getId());
        to.setFirstName(entity.getFirstName());
        to.setLastName(entity.getLastName());
        to.setTelephoneNumber(entity.getTelephoneNumber());
        to.setEmail(entity.getEmail());
        to.setPatientNumber(entity.getPatientNumber());
        to.setDateOfBirth(entity.getDateOfBirth());
        to.setInsured(entity.isInsured());

        if (entity.getVisits() != null) {
            List<VisitTO> visitTOs = entity.getVisits()
                    .stream()
                    .filter(visit -> LocalDateTime.now().isAfter(visit.getTime()))
                    .map(visit -> {
                        VisitTO visitTO = new VisitTO();
                        visitTO.setId(visit.getId());
                        visitTO.setTime(visit.getTime());
                        visitTO.setDescription(visit.getDescription());
                        visitTO.setDoctorFirstName(visit.getDoctor().getFirstName());
                        visitTO.setDoctorLastName(visit.getDoctor().getLastName());
                        visitTO.setTreatmentTypes(
                                visit.getMedicalTreatments().stream().map(MedicalTreatmentEntity::getType).toList()
                        );
                        return visitTO;
                    })
                    .collect(Collectors.toList());
            to.setFinishedVisits(visitTOs);
        }

        return to;
    }
}
