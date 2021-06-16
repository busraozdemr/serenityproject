package bdd.steps.navigation;

import bdd.utils.StrongerDriver;
import net.thucydides.core.annotations.Step;
import bdd.page.DuckDuckGoHomePage;


public class NavigateTo extends StrongerDriver {

    DuckDuckGoHomePage duckDuckGoHomePage;

    @Step("Open the DuckDuckGo home page")
    public void theDuckDuckGoHomePage() {
        duckDuckGoHomePage.open();
    }
}
