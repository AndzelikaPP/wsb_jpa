package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PatientDaoTest {

    private static final Long EXISTING_PATIENT_ID = 21L;
    private static final Long EXISTING_DOCTOR_ID = 10L;

    @Autowired
    private PatientDao patientDao;

    @Test
    @Transactional
    public void addVisitTest() {
        VisitEntity added = patientDao.addVisit(EXISTING_PATIENT_ID, EXISTING_DOCTOR_ID, LocalDateTime.now(), "opis wizyty");

        PatientEntity toTest = patientDao.findOne(EXISTING_PATIENT_ID);
        assertThat(toTest).isNotNull();
        assertThat(toTest.getVisits()).isNotNull();
        VisitEntity addedVisitAlsoAddedInPatientVisits = toTest.getVisits()
                .stream()
                .filter(v -> v.getId().equals(added.getId()))
                .findFirst()
                .orElse(null);
        assertThat(addedVisitAlsoAddedInPatientVisits).isNotNull();
    }

}