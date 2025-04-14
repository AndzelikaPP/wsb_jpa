package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PatientServiceTest {

    private static final Long PATIENT_ID_TO_DELETE = 20L;
    private static final Long PATIENT_ID_TO_FIND = 21L;

    @Autowired
    private PatientService patientService;
    @Autowired
    private VisitDao visitDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private DoctorDao doctorDao;

    @Test
    @Transactional
    public void deletebyId() {
        PatientEntity patientToDelete = patientDao.findOne(PATIENT_ID_TO_DELETE);
        List<Long> relatedVisitsIds = patientToDelete.getVisits().stream().map(VisitEntity::getId).toList();
        List<Long> relatedDoctorsIds = patientToDelete.getVisits().stream().map(v -> v.getDoctor().getId()).toList();

        patientService.deleteById(patientToDelete.getId());

        assertThat(patientDao.findOne(PATIENT_ID_TO_DELETE)).isNull();

        relatedVisitsIds.forEach(v -> {
            assertThat(visitDao.findOne(v)).isNull();
        });

        relatedDoctorsIds.forEach(d -> {
            assertThat(doctorDao.findOne(d)).isNotNull();
        });
    }

    @Test
    @Transactional
    public void findById() {
        PatientTO found = patientService.findById(PATIENT_ID_TO_FIND);

        assertThat(found).isNotNull();
        assertThat(found.isInsured()).isEqualTo(false);
        assertThat(found.getFinishedVisits()).isNotNull();
        assertThat(found.getFinishedVisits().size()).isEqualTo(1);
        VisitTO relatedVisit = found.getFinishedVisits().iterator().next();
        assertThat(relatedVisit.getTime()).isNotNull();
        assertThat(relatedVisit.getDoctorFirstName()).isNotNull();
        assertThat(relatedVisit.getDoctorLastName()).isNotNull();
    }

}