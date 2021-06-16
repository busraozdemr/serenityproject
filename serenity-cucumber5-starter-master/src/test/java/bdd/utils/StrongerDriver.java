package bdd.utils;

import net.serenitybdd.core.steps.UIInteractionSteps;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class StrongerDriver extends UIInteractionSteps {

    public void clickToBy(By by) {
        scrollTo(by);
        highlightElement(by);
        $(by).waitUntilClickable().click();
    }

    public void hoverElement(By by) {
        WebElement ele = getDriver().findElement(by);
        Actions action = new Actions(getDriver());
        action.moveToElement(ele).build().perform();
    }

    private void highlightElement(By by) {
        WebElement element = $(by).waitUntilPresent();
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].style.background = 'yellow';", element);
    }

    private WebElement scrollTo(By by) {
        WebElement element = $(by).waitUntilVisible();
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0, arguments[0]);", element);
        return element;
    }

    public void clickToByWithJs(By by) {
        scrollTo(by);
        highlightElement(by);
        WebElement element = $(by).waitUntilClickable();

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);

    }

    public void type(By by, String text, boolean clear) {
        $(by).waitUntilEnabled().waitUntilClickable();
        highlightElement(by);
        WebElement element = scrollTo(by);

        if (clear) {
            element.clear();
        }

        element.sendKeys(text);

    }

    public void typeWithJs(By by, String text, boolean clear) {
        $(by).waitUntilEnabled().waitUntilClickable();
        highlightElement(by);
        WebElement element = scrollTo(by);

        if (clear) {
            element.clear();

        }

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].value='" + text + "';", element);

    }

    public void typeKeyByKey(By trackingCodeTextBox, String trackingCode) {
        highlightElement(trackingCodeTextBox);
        WebElement element = scrollTo(trackingCodeTextBox);
        element.clear();
        char[] trackingCodeAsArray = trackingCode.toCharArray();
        for (char character : trackingCodeAsArray) {
            element.sendKeys(String.valueOf(character));
        }
    }

    public String getElementTextById(String id) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        $(By.id(id)).waitUntilPresent();
        return (String) js.executeScript("return document.getElementById('" + id + "').value");
    }

    public void waitForPageToCompleteState(WebDriver driver) {
        int counter = 0;
        int maxNoOfRetries = 15;
        while (counter != maxNoOfRetries) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                if (js.executeScript("return document.readyState").toString().equals("complete")) {
                    break;
                }
                waitABit(2000);
            } catch (Exception e) {

                System.out.println("No success!");
            }
            counter++;
        }
    }
    public void clickWithJs(By by) {
        WebElement element = getDriver().findElement(by);

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    public void waitForAjax() {
        try {
            WebDriverWait myWait = new WebDriverWait(getDriver(), 60);
            ExpectedCondition<Boolean> conditionToCheck = input -> {
                JavascriptExecutor jsDriver = (JavascriptExecutor) getDriver();
                boolean stillRunningAjax = (Boolean) jsDriver
                        .executeScript("return (window.jQuery != undefined && ($(':animated').length != 0 || jQuery.active != 0)) || document.readyState != \"complete\"");
                return !stillRunningAjax;
            };

            myWait.until(conditionToCheck);
        } catch (RuntimeException rte) {
            System.out.println(rte);
        }
    }

    private void checkLoadingBar() {
        try {
            if ($(By.id("loadingBar")).isPresent() && $(By.id("loadingBar")).getAttribute("style").contains("width")) {
                waitForLoadingBar();
            }
        } catch (RuntimeException rte) {
            System.out.println("Loading bar has disappeared while checking width!" + rte);
        }
    }

    private void waitForLoadingBar() {
        int count = 0;
        try {
            while (!$(By.id("loadingBar")).getAttribute("style").contains("0px") && !"".equals($(By.id("loadingBar")).getAttribute("style")) && count < 30) {
                waitForAjax();
                if (count == 29) {
                    System.out.println("DIALOG TIMEOUT");
                }
                count++;
            }
        } catch (RuntimeException rte) {
            System.out.println(rte);
        }
    }
}
