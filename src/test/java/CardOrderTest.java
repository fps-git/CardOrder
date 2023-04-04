import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class CardOrderTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }

    boolean isDisplayed(By by) {
        try {
            return driver.findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void ShouldReturnSuccess() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Михаил Бестужев-Рюмин");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

//    @Test
//    public void ShouldSuccessWithSpecificSymbol() {
//        driver.get("http://localhost:9999");
//        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Артём");
//        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79001234567");
//        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
//        driver.findElement(By.cssSelector(".form-field button")).click();
//
//        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
//        String actual = driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText().trim();
//
//        assertEquals(expected, actual);
//    }

    @Test
    public void ShouldSuccessWithShortName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Ян");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79001234567");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    public void ShouldSuccessWithTwoSpaces() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Джеймс Смит Младший");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79001234567");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    public void ShouldNotSuccessIfEnglishName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Vasiliy");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("span[data-test-id=name] .input__sub")).getText();

        assertEquals(expected, actual);
        assertFalse(isDisplayed(By.cssSelector("p[data-test-id=order-success]")));
    }

    @Test
    public void ShouldNotSuccessIfLongerNumber() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Кирилл Петров");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+790123456789");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("span[data-test-id=phone] .input__sub")).getText();

        assertEquals(expected, actual);
        assertFalse(isDisplayed(By.cssSelector("p[data-test-id=order-success]")));
    }

    @Test
    public void ShouldNotSuccessIfNoPlusSymbol() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Васильев Григорий");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("790012345678");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("span[data-test-id=phone] .input__sub")).getText();

        assertEquals(expected, actual);
        assertFalse(isDisplayed(By.cssSelector("p[data-test-id=order-success]")));
    }

//    @Test
//    public void ShouldNotSuccessIfAllNumbersAreZero() {
//        driver.get("http://localhost:9999");
//        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Сергей Иванович Нулев");
//        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+00000000000");
//        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
//        driver.findElement(By.cssSelector(".form-field button")).click();
//
//        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
//        String actual = driver.findElement(By.cssSelector("span[data-test-id=phone] .input__sub")).getText();
//
//        assertEquals(expected, actual);
//        assertNull(driver.findElement(By.cssSelector("p[data-test-id=order-success]")).getText());
//    }

    @Test
    public void ShouldNotSuccessIfCheckBoxNotClicked() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Сергей Иванович Безчекбоксов");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79031234567");
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "rgba(255, 92, 92, 1)";
        String actual = driver.findElement(By.cssSelector("label[data-test-id=agreement] span[role=presentation]")).getCssValue("color");

        assertEquals(expected, actual);
        assertFalse(isDisplayed(By.cssSelector("p[data-test-id=order-success]")));
    }

    @Test
    public void ShouldNotSuccessIfEmptyName() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("+79031234567");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("span[data-test-id=name] .input__sub")).getText();

        String expectedColor = "rgba(255, 92, 92, 1)";
        String actualColor = driver.findElement(By.cssSelector("span[data-test-id=name] .input__sub")).getCssValue("color");

        assertFalse(isDisplayed(By.cssSelector("p[data-test-id=order-success]")));
        assertEquals(expected, actual);
        assertEquals(expectedColor, actualColor);
    }

    @Test
    public void ShouldNotSuccessIfEmptyPhone() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("span[data-test-id=name] input")).sendKeys("Василий Безтелефонов");
        driver.findElement(By.cssSelector("span[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("label[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".form-field button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("span[data-test-id=phone] .input__sub")).getText();

        String expectedColor = "rgba(255, 92, 92, 1)";
        String actualColor = driver.findElement(By.cssSelector("span[data-test-id=phone] .input__sub")).getCssValue("color");

        assertFalse(isDisplayed(By.cssSelector("p[data-test-id=order-success]")));
        assertEquals(expected, actual);
        assertEquals(expectedColor, actualColor);
    }
}
