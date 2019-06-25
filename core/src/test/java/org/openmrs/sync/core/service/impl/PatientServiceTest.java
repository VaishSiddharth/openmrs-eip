package org.openmrs.sync.core.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.sync.core.entity.Patient;
import org.openmrs.sync.core.mapper.EntityMapper;
import org.openmrs.sync.core.model.PatientModel;
import org.openmrs.sync.core.repository.SyncEntityRepository;
import org.openmrs.sync.core.service.TableToSyncEnum;

import static org.junit.Assert.assertEquals;

public class PatientServiceTest {

    @Mock
    private SyncEntityRepository<Patient> repository;

    @Mock
    private EntityMapper<Patient, PatientModel> mapper;

    private PatientService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PatientService(repository, mapper);
    }

    @Test
    public void getTableToSync() {
        assertEquals(TableToSyncEnum.PATIENT, service.getTableToSync());
    }
}
