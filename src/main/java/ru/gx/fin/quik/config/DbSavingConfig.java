package ru.gx.fin.quik.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.gx.core.data.save.*;
import ru.gx.core.jdbc.save.JdbcJsonDbSavingOperator;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamAllTradesPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamDealsPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamOrdersPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.channels.QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1;
import ru.gx.fin.gate.quik.provider.out.QuikAllTradesPackage;
import ru.gx.fin.gate.quik.provider.out.QuikDealsPackage;
import ru.gx.fin.gate.quik.provider.out.QuikOrdersPackage;
import ru.gx.fin.gate.quik.provider.out.QuikSecuritiesPackage;

import javax.annotation.PostConstruct;

@Configuration
public class DbSavingConfig extends AbstractDbSavingConfiguration {
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Constants">
    private static final String CALL_SAVE_ALL_TRADES = "CALL \"Quik\".\"AllTrades@SavePackage\"(?)";
    private static final String CALL_SAVE_ORDERS = "CALL \"Quik\".\"Orders@SavePackage\"(?)";
    private static final String CALL_SAVE_DEALS = "CALL \"Quik\".\"Deals@SavePackage\"(?)";
    private static final String CALL_SAVE_SECURITIES = "CALL \"Quik\".\"Securities@SavePackage\"(?)";
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="Fields">
    @NotNull
    private final JdbcJsonDbSavingOperator savingOperator;

    @NotNull
    private final QuikProviderStreamAllTradesPackageDataPublishChannelApiV1 allTradesChannelApi;

    @NotNull
    private final QuikProviderStreamDealsPackageDataPublishChannelApiV1 dealsChannelApi;

    @NotNull
    private final QuikProviderStreamOrdersPackageDataPublishChannelApiV1 ordersChannelApi;

    @NotNull
    private final QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1 securitiesChannelApi;

    @Autowired
    public DbSavingConfig(
            @NotNull final JdbcJsonDbSavingOperator savingOperator,
            @NotNull final QuikProviderStreamAllTradesPackageDataPublishChannelApiV1 allTradesChannelApi,
            @NotNull final QuikProviderStreamDealsPackageDataPublishChannelApiV1 dealsChannelApi,
            @NotNull final QuikProviderStreamOrdersPackageDataPublishChannelApiV1 ordersChannelApi,
            @NotNull final QuikProviderStreamSecuritiesPackageDataPublishChannelApiV1 securitiesChannelApi
    ) {
        super("db-saving-config");
        this.savingOperator = savingOperator;
        this.allTradesChannelApi = allTradesChannelApi;
        this.dealsChannelApi = dealsChannelApi;
        this.ordersChannelApi = ordersChannelApi;
        this.securitiesChannelApi = securitiesChannelApi;
    }

    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
    // <editor-fold desc="configure DbSaver">
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        this.getDescriptorsDefaults()
                .setSerializeMode(DbSavingSerializeMode.Json)
                .setAccumulateMode(DbSavingAccumulateMode.PerPackage)
                .setProcessMode(DbSavingProcessMode.Immediate)
                .setSaveOperator(this.savingOperator);

        this
                .newDescriptor(this.allTradesChannelApi, DbSavingDescriptor.class)
                .setSaveCommand(CALL_SAVE_ALL_TRADES)
                .setDataPackageClass(QuikAllTradesPackage.class)
                .init();

        this
                .newDescriptor(this.ordersChannelApi, DbSavingDescriptor.class)
                .setSaveCommand(CALL_SAVE_ORDERS)
                .setDataPackageClass(QuikOrdersPackage.class)
                .init();

        this
                .newDescriptor(this.dealsChannelApi, DbSavingDescriptor.class)
                .setSaveCommand(CALL_SAVE_DEALS)
                .setDataPackageClass(QuikDealsPackage.class)
                .init();

        this
                .newDescriptor(this.securitiesChannelApi, DbSavingDescriptor.class)
                .setSaveCommand(CALL_SAVE_SECURITIES)
                .setDataPackageClass(QuikSecuritiesPackage.class)
                .init();
    }
    // </editor-fold>
    // -----------------------------------------------------------------------------------------------------------------
}
