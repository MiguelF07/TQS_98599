package com.tqs.homework1;

import io.cucumber.java.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CovidPlatformSteps {

    private WebDriver driver;
    private WebDriverWait wait;

    @When("I navigate to {string}")
    public void i_navigate_to(String url) {
        FirefoxOptions options= new FirefoxOptions().setHeadless(true);
        driver = new FirefoxDriver(options);
//        driver = new FirefoxDriver();
        driver.get(url);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I navigate to");

    }
    @When("I choose {string} as the country I want to see")
    public void i_choose_as_the_country_i_want_to_see(String string) {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("select")).click();
        {
            WebElement dropdown = driver.findElement(By.cssSelector("select"));
            dropdown.findElement(By.xpath("//option[. = \'"+string+"\']")).click();
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I choose as the country i want to see");

    }
    @Then("I should see the {string} modal")
    public void i_should_see_the_modal(String string) {
        wait = new WebDriverWait(driver,5);
        wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div[1]/h4")),string));
        assertThat(driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div[1]/h4")).getText()).contains(string);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I should see the modal");
    }

    @Then("I should see the COVID data for the country {string}")
    public void i_should_see_the_covid_data_for_the_country(String string) {
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.or(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector(".row:nth-child(1) > .col"),"")),ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector(".row:nth-child(1) > .col"),"No Data"))));
        driver.findElement(By.cssSelector(".row:nth-child(1) > .col")).click();
        assertThat(driver.findElement(By.cssSelector(".row:nth-child(1) > .col")).getText()).contains(string);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I should see the covid data for the country");
    }

    @When("I enter the From date as {string}")
    public void i_enter_the_from_date_as(String string) {
        WebElement from = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[2]/input"));
        from.sendKeys(Keys.COMMAND + "a"); //In Windows the Keys.COMMAND must be replaced by Keys.CONTROL
//        from.sendKeys(Keys.CONTROL + "a"); //For Windows
        from.sendKeys(Keys.DELETE);
        from.click();
        from.sendKeys(string);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/h2")).click(); //To dismiss the datepicker
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I enter the from date");
    }

    @When("I enter the To date as {string}")
    public void i_enter_the_to_date_as(String string) {
        WebElement from = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div/div[1]/div[3]/input"));
        from.sendKeys(Keys.COMMAND + "a"); //In Windows the Keys.COMMAND must be replaced by Keys.CONTROL
//        from.sendKeys(Keys.CONTROL + "a"); //For Windows
        from.sendKeys(Keys.DELETE);
        from.click();
        from.sendKeys(string);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div/header/h2")).click(); //To dismiss the datepicker
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I enter the to date");
    }
    @When("I click the {string} button")
    public void i_click_the_button(String string) {
        assertThat(driver.findElement(By.cssSelector(".btn")).getText()).isEqualTo("See History");
        driver.findElement(By.cssSelector(".btn")).click();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I click the button");
    }
    @Then("I should see the day {string}")
    public void i_should_see_the_day(String string) {
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div/div[2]/div/div[2]/div/div/b[1]")));
        driver.findElement(By.cssSelector(".row:nth-child(1) > .col")).click();
        assertThat(driver.findElement(By.cssSelector(".row:nth-child(1) > .col")).getText()).contains(string);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "I should see the day");
    }
}
