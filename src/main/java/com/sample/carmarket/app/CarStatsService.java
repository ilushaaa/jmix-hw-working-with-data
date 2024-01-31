package com.sample.carmarket.app;

import com.sample.carmarket.entity.EngineType;
import com.sample.carmarket.entity.Manufacturer;
import io.jmix.core.DataManager;
import io.jmix.core.FluentValuesLoader;
import io.jmix.core.entity.KeyValueEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;

@Component
public class CarStatsService {
    private final DataManager dataManager;

    public CarStatsService(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public String calculateCarStatusAsString(@Nullable Manufacturer manufacturer) {
        List<KeyValueEntity> list = withParameter(withQuery(dataManager, manufacturer), manufacturer)
                .properties("engineType", "count")
                .list();

        StringBuilder sb = new StringBuilder();
        sb.append("Statistics for ")
                .append(manufacturer != null ? manufacturer.getName() : "all manufacturers");

        for (KeyValueEntity queryResult : list) {
            Number count = queryResult.getValue("count");
            String engineType = queryResult.getValue("engineType");

            sb.append("\n");

            if (EngineType.ELECTRIC.getId().equals(engineType)) {
                sb.append("Electric cars: ").append(count);
            }
            if (EngineType.GASOLINE.getId().equals(engineType)) {
                sb.append("Gasoline cars: ").append(count);
            }
        }

        return sb.toString();
    }

    private FluentValuesLoader withQuery(DataManager dataManager, Manufacturer manufacturer) {
        String query = "select c.model.engineType, count(c) from Car c" +
                (manufacturer != null ? " where c.model.manufacturer.id = :manufacturerId" : "") +
                " group by c.model.engineType";
        return dataManager.loadValues(query);
    }

    private FluentValuesLoader withParameter(FluentValuesLoader valuesLoader, Manufacturer manufacturer) {
        if (manufacturer != null) {
            valuesLoader.parameter("manufacturerId", manufacturer.getId());
        }
        return valuesLoader;
    }
}