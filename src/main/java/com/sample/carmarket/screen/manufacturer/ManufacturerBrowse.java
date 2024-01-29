package com.sample.carmarket.screen.manufacturer;

import com.sample.carmarket.app.CarStatsService;
import com.sample.carmarket.entity.EngineType;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.*;
import com.sample.carmarket.entity.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@UiController("Manufacturer.browse")
@UiDescriptor("manufacturer-browse.xml")
@LookupComponent("table")
public class ManufacturerBrowse extends MasterDetailScreen<Manufacturer> {
    @Autowired
    private Notifications notifications;
    @Autowired
    private CarStatsService carStatsService;
    @Autowired
    private Table<Manufacturer> table;

    @Subscribe("table.calculateCars")
    public void onTableCalculateCars(final Action.ActionPerformedEvent event) {
        Manufacturer selected = table.getSingleSelected();

        String stats = carStatsService.calculateCarStatusAsString(selected);
        notifications.create()
                .withCaption(stats)
                .show();
    }
}