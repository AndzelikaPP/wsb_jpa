package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public VisitEntity addVisit(Long patientId, Long doctorId, LocalDateTime time, String description) {
        VisitEntity visitToSave = new VisitEntity();
        visitToSave.setPatient(this.findOne(patientId));
        visitToSave.setDoctor(doctorDao.findOne(doctorId));
        visitToSave.setTime(time);
        visitToSave.setDescription(description);
        return visitDao.save(visitToSave);
    }

}
