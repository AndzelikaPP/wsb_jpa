package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;
    private final VisitDao visitDao;

    @PersistenceContext
    private EntityManager entityManager;

    public PatientServiceImpl(PatientDao patientDao, VisitDao visitDao) {
        this.patientDao = patientDao;
        this.visitDao = visitDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity patientEntity = patientDao.findOne(id);
        return PatientMapper.mapToTO(patientEntity);
    }

    @Override
    public void deleteById(Long id) {
        patientDao.delete(id);
    }

    @Override
    public List<VisitTO> getVisitsByPatientId(Long patientId) {
        List<VisitEntity> visits = entityManager.createQuery(
                        "SELECT v FROM VisitEntity v WHERE v.patient.id = :id", VisitEntity.class)
                .setParameter("id", patientId)
                .getResultList();

        return visits.stream().map(visit -> {
            VisitTO dto = new VisitTO();
            dto.setId(visit.getId());
            dto.setTime(visit.getTime());
            dto.setDescription(visit.getDescription());
            dto.setDoctorFirstName(visit.getDoctor().getFirstName());
            dto.setDoctorLastName(visit.getDoctor().getLastName());
            dto.setTreatmentTypes(
                    visit.getMedicalTreatments().stream()
                            .map(t -> t.getType())
                            .toList()
            );
            return dto;
        }).toList();
    }
}
