package com.sample.carmarket.screen.car;

import com.sample.carmarket.entity.AvailabilityStatus;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import com.sample.carmarket.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@UiController("Car.browse")
@UiDescriptor("car-browse.xml")
@LookupComponent("carsTable")
public class CarBrowse extends StandardLookup<Car> {
    @Autowired
    private GroupTable<Car> carsTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionContainer<Car> carsDc;

    @Subscribe("carsTable.markAsSold")
    public void onCarsTableMarkAsSold(final Action.ActionPerformedEvent event) {
        Car selected = carsTable.getSingleSelected();
        if (selected == null) {
            notifications.create()
                    .withCaption("Please, select a car to mark as sold")
                    .show();
            return;
        }
        if (AvailabilityStatus.SOLD.equals(selected.getStatus())) {
            notifications.create()
                    .withCaption("Selected car already sold")
                    .show();
            return;
        }

        selected.setStatus(AvailabilityStatus.SOLD);
        selected.setDateOfSale(LocalDate.now());

        Car savedCar = dataManager.save(selected);
        carsDc.replaceItem(savedCar);

        notifications.create()
                .withCaption("Done")
                .show();
    }
}