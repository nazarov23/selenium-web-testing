package ru.netology.testing;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitFormWithValidData() {
        // Заполняем форму
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об успехе
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldShowErrorWithInvalidName() {
        // Заполняем форму с невалидным именем
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivanov Ivan");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expectedText = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldShowErrorWithInvalidPhone() {
        // Заполняем форму с невалидным телефоном
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7921123456");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expectedText = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldShowErrorWithoutAgreement() {
        // Заполняем форму без согласия
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        // Чекбокс не отмечаем
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем, что чекбокс стал невалидным
        WebElement checkbox = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
        Assertions.assertTrue(checkbox.isDisplayed());
    }

    @Test
    void shouldShowErrorWithEmptyName() {
        // Заполняем форму с пустым именем
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldShowErrorWithEmptyPhone() {
        // Заполняем форму с пустым телефоном
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об ошибке
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        String expectedText = "Поле обязательно для заполнения";
        assertEquals(expectedText, actualText);
    }

    @Test
    void shouldSubmitFormWithComplexName() {
        // Заполняем форму с составным именем
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Анна-Мария Петрова-Иванова");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79211234567");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        // Проверяем сообщение об успехе
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expectedText = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expectedText, actualText);
    }
}