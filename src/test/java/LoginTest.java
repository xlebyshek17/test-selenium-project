import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private WebDriver driver;

    @BeforeEach
    void setUp() {
        //System.setProperty("webdriver.chrome.driver", "/home/kridtina/selenium-drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        // Pozwalaj na załadowanie lokalnych plików (file://)
        options.addArguments("--allow-file-access-from-files");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }


    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testPoprawnegoLogowania() {
        // Generujemy URL do lokalnego pliku HTML
        String path = Paths.get("/src/test/resources/login.html")
                .toAbsolutePath()
                .toUri()
                .toString();
        driver.get(path);

        WebElement usernameInput = driver.findElement(By.id("username"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement loginButton   = driver.findElement(By.id("loginButton"));

        usernameInput.sendKeys("JanKowalski");
        passwordInput.sendKeys("tajneHaslo");
        loginButton.click();

        WebElement messageDiv = driver.findElement(By.id("message"));
        String actualText = messageDiv.getText();
        assertEquals("Witaj, JanKowalski!", actualText);
    }

    @Test
    void testBrakuDanych() {
        String path = Paths.get("src/test/resources/login.html")
                .toAbsolutePath()
                .toUri()
                .toString();
        System.out.println("Otwieram URL: " + path);
        driver.get(path);

        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();

        WebElement messageDiv = driver.findElement(By.id("message"));
        String actualText = messageDiv.getText();
        assertEquals("Proszę podać nazwę i hasło!", actualText);
    }
}
