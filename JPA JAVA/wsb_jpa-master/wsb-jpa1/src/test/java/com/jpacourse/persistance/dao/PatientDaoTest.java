package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    void shouldFindByLastName() {
        List<PatientEntity> results = patientDao.findByLastName("Kowalski");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getFirstName()).isEqualTo("Jan");
    }

    @Test
    void shouldFindPatientsWithMoreThan3Visits() {
        List<PatientEntity> results = patientDao.findPatientsWithMoreThanXVisits(3);
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getLastName()).isEqualTo("Kura");
    }

    @Test
    void shouldFindPatientsRegisteredAfterGivenDate() {
        List<PatientEntity> results = patientDao.findByRegistrationDateAfter(LocalDate.of(2024, 1, 1));
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getLastName()).isEqualTo("Sobieska");
    }



}