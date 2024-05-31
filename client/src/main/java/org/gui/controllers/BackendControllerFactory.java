package org.gui.controllers;

import org.client.ClientAppBackend;

import java.io.IOException;
import java.util.HashMap;
import java.util.function.Supplier;

public class BackendControllerFactory {
    private final ClientAppBackend data;
    private final MainController parentController;

    private static final HashMap<Class<?>, Supplier<?>> controllerCreators = new HashMap<>();

    public BackendControllerFactory(MainController parent, ClientAppBackend data) {
        this.data = data;
        parentController = parent;
        initControllersCreators();
    }

    public Object controllerCreate(Class<?> param) {
        try {
            if (controllerCreators.containsKey(param)) {// пассуем любую хуйню внутрь контроллера теперь
                return controllerCreators.get(param).get();
            }
            return param.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void initControllersCreators() {
        controllerCreators.put(TableTabController.class, () -> {
            TableTabController controller = new TableTabController(parentController, data);
            parentController.setTableController(controller);
            return controller;
        }); // пассуем любую хуйню внутрь контроллера теперь
        controllerCreators.put(RadarTabController.class, () -> {
            RadarTabController controller = new RadarTabController(parentController, data);
            parentController.setRadarController(controller);
            return controller;
        });
    }
}
