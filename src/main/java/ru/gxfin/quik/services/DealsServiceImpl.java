package ru.gxfin.quik.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.AbstractDataService;
import ru.gxfin.quik.api.DbAdapterSettingsController;
import ru.gxfin.quik.db.AllTradeEntitiesPackage;
import ru.gxfin.quik.db.AllTradeEntity;
import ru.gxfin.quik.db.DealEntitiesPackage;
import ru.gxfin.quik.db.DealEntity;
import ru.gxfin.quik.repository.AllTradesRepository;
import ru.gxfin.quik.repository.DealsRepository;

public class DealsServiceImpl
        extends AbstractDataService<DealEntity, DealEntitiesPackage, Long, DealsRepository>
        implements DealsService {

    @Autowired
    private DbAdapterSettingsController settings;

    public DealsServiceImpl(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Class<DealEntitiesPackage> getPackageClass() {
        return DealEntitiesPackage.class;
    }

    @Autowired
    @Override
    protected void setRepository(DealsRepository repository) {
        super.setRepository(repository);
    }

    @Override
    public String incomeTopic() {
        return settings.getIncomeTopicDeals();
    }

    @Override
    public String outcomeTopic() {
        return null;
    }
}
