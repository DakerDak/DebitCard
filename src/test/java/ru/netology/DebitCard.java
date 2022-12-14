package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebitCard {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {

        WebDriverManager.chromedriver().setup();

    }


    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");

    }

    @AfterEach
    void tearsDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void testLineFIO1() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Корсак Петр");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+79498562586");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void testLineFIO2() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Petrov");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+79498562586");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void testLineFIO3() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+79498562586");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testLinePhone() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+123456");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void testLinePhone2() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid  .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void testLineCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] .input__control")).sendKeys("+79498562586");
        driver.findElement(By.cssSelector(".button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }
}




