package bdd.page;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;

@DefaultUrl("page:webdriver.base.url")
//@DefaultUrl("https://duckduckgo.com")
public class DuckDuckGoHomePage extends PageObject {

    public static final By PRODUCT_ID_TEXTBOX = By.id("productId");
}
