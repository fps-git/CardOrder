import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardOrderTest {
    @Test
    public void ShouldReturnSuccess() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Михаил Бестужев-Рюмин");
        $("span[data-test-id=phone] input").setValue("+12345678901");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("p[data-test-id=order-success]").shouldHave(Condition.exactOwnText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void ShouldSuccessWithSpecificSymbol() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Артём");
        $("span[data-test-id=phone] input").setValue("+79001234567");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("p[data-test-id=order-success]").shouldHave(Condition.exactOwnText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void ShouldSuccessWithShortName() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Ян");
        $("span[data-test-id=phone] input").setValue("+79001234567");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("p[data-test-id=order-success]").shouldHave(Condition.exactOwnText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void ShouldSuccessWithTwoSpaces() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Джеймс Смит Младший");
        $("span[data-test-id=phone] input").setValue("+79001234567");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("p[data-test-id=order-success]").shouldHave(Condition.exactOwnText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void ShouldNotSuccessIfEnglishName() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Vasiliy");
        $("span[data-test-id=phone] input").setValue("+12345678901");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("span[data-test-id=name] .input__sub").shouldHave(Condition.exactOwnText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

    @Test
    public void ShouldNotSuccessIfLongerNumber() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Кирилл Петров");
        $("span[data-test-id=phone] input").setValue("+790012345678");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("span[data-test-id=phone] .input__sub").shouldHave(Condition.exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

    @Test
    public void ShouldNotSuccessIfNoPlusSymbol() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Васильев Григорий");
        $("span[data-test-id=phone] input").setValue("790012345678");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("span[data-test-id=phone] .input__sub").shouldHave(Condition.exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

    @Test
    public void ShouldNotSuccessIfAllNumbersAreZero() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Сергей Иванович Нулев");
        $("span[data-test-id=phone] input").setValue("+00000000000");
        $("label[data-test-id=agreement]").click();
        $(".form-field button").click();
        $("span[data-test-id=phone] .input__sub").shouldHave(Condition.exactOwnText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

    @Test
    public void ShouldNotSuccessIfCheckBoxNotClicked() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Сергей Иванович Безчекбоксов");
        $("span[data-test-id=phone] input").setValue("+79031234567");
        $(".form-field button").click();
        $("label[data-test-id=agreement] span[role=presentation]").shouldHave(Condition.cssValue("color", "rgba(255, 92, 92, 1)"));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

    @Test
    public void ShouldNotSuccessIfEmptyName() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("");
        $("span[data-test-id=phone] input").setValue("+79031234567");
        $(".form-field button").click();
        $(".form-field button").click();
        $("span[data-test-id=name] .input__sub").shouldHave(Condition.exactOwnText("Поле обязательно для заполнения"));
        $("span[data-test-id=name] .input__sub").shouldHave(Condition.cssValue("color", "rgba(255, 92, 92, 1)"));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

    @Test
    public void ShouldNotSuccessIfEmptyPhone() {
        open("http://localhost:9999");
        $("span[data-test-id=name] input").setValue("Василий Безтелефонов");
        $("span[data-test-id=phone] input").setValue("");
        $(".form-field button").click();
        $(".form-field button").click();
        $("span[data-test-id=phone] .input__sub").shouldHave(Condition.exactOwnText("Поле обязательно для заполнения"));
        $("span[data-test-id=phone] .input__sub").shouldHave(Condition.cssValue("color", "rgba(255, 92, 92, 1)"));
        $("p[data-test-id=order-success]").shouldNot(Condition.exist);
    }

}
