package com.dax.walker.engine.definitions;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.component.Interfaces;

public enum PopUpInterfaces {
    STRONGHOLD_PROMPT(() -> Interfaces.getComponent(579, 17)),
    WILDERNESS_PROMPT(() -> Interfaces.getComponent(475, 11));

    private RSPopUp rsPopUp;

    PopUpInterfaces(RSPopUp rsPopUp) {
        this.rsPopUp = rsPopUp;
    }

    public boolean handle() {
        InterfaceComponent interfaceComponent = this.rsPopUp.get();
        if (interfaceComponent == null) return false;
        if (!interfaceComponent.click()) return false;
        Time.sleep(450, 1000);
        return true;
    }

    public boolean isVisible() {
        InterfaceComponent interfaceComponent = this.rsPopUp.get();
        return interfaceComponent != null && interfaceComponent.isVisible();
    }

    public static boolean resolve() {
        for (PopUpInterfaces popUpInterfaces : values()) {
            if (!popUpInterfaces.isVisible()) continue;
            return popUpInterfaces.handle();
        }
        return false;
    }

}
