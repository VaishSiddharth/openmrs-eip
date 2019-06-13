package org.openmrs.sync.core.service.light.impl;

import org.openmrs.sync.core.entity.light.VisitTypeLight;
import org.openmrs.sync.core.repository.OpenMrsRepository;
import org.openmrs.sync.core.service.light.AbstractLightServiceNoContext;
import org.springframework.stereotype.Service;

@Service
public class VisitTypeLightService extends AbstractLightServiceNoContext<VisitTypeLight> {

    public VisitTypeLightService(final OpenMrsRepository<VisitTypeLight> repository) {
        super(repository);
    }

    @Override
    protected VisitTypeLight getShadowEntity(final String uuid) {
        VisitTypeLight visitType = new VisitTypeLight();
        visitType.setUuid(uuid);
        visitType.setName(DEFAULT_STRING);
        visitType.setCreator(DEFAULT_USER_ID);
        visitType.setDateCreated(DEFAULT_DATE);
        return visitType;
    }
}