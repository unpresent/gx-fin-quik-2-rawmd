package ru.gxfin.quik.config;

import org.springframework.beans.factory.annotation.Autowired;
import ru.gxfin.common.data.DataServiceWithTopics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomeTopicsServices {
    private final Map<String, DataServiceWithTopics> map = new HashMap<>();

    @Autowired
    public void setDataServices(List<DataServiceWithTopics> dataServices) {
        dataServices.forEach(x -> map.put(x.incomeTopic(), x));
    }

    public DataServiceWithTopics get(String topic) {
        return map.get(topic);
    }
}
