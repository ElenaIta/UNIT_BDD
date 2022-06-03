package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.String.valueOf;

public class TransferPage {

    private SelenideElement sumField = $("[data-test-id=amount] input");
    private SelenideElement accountField = $("[data-test-id=from] input");
    private SelenideElement topUpButton = $("[data-test-id=action-transfer]");

    public DashboardPage successfulTopUp(int amount, DataHelper.CardNumber from) {
        sumField.setValue(valueOf(amount));
        accountField.setValue(valueOf(from));
        topUpButton.click();
        return new DashboardPage();
    }
    public void getErrorLimit() {
        $(byText("Ошибка! Сумма превышает допустимый лимит!")).shouldBe(visible);
    }

    public void getErrorInvalidCard() {
        $(byText("Ошибка! Проверьте номер карты!")).shouldBe(visible);
    }

}
