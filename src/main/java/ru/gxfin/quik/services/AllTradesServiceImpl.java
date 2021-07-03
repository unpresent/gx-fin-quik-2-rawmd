package ru.gxfin.quik.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.AbstractDataService;
import ru.gxfin.common.settings.SettingsController;
import ru.gxfin.quik.api.DbAdapterSettingsController;
import ru.gxfin.quik.db.AllTradeEntitiesPackage;
import ru.gxfin.quik.db.AllTradeEntity;
import ru.gxfin.quik.repository.AllTradesRepository;

public class AllTradesServiceImpl
        extends AbstractDataService<AllTradeEntity, AllTradeEntitiesPackage, Long, AllTradesRepository>
        implements AllTradesService {

    @Autowired
    private DbAdapterSettingsController settings;

    public AllTradesServiceImpl(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Class<AllTradeEntitiesPackage> getPackageClass() {
        return AllTradeEntitiesPackage.class;
    }

    @Autowired
    @Override
    protected void setRepository(AllTradesRepository repository) {
        super.setRepository(repository);
    }

    @Override
    public String incomeTopic() {
        return settings.getIncomeTopicAlltrades();
    }

    @Override
    public String outcomeTopic() {
        return null;
    }

    /*
    @Override
    public AllTradeEntity findByExchangeCodeAndTradeNum(String exchangeCode, String tradeNum) {
        final var result = this.getRepository().findByExchangeCodeAndTradeNum(exchangeCode, tradeNum);
        return result.orElse(null);
    }
    //*/
}
