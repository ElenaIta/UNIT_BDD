package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    public static SelenideElement card1 = $("[data-test-id='92df3f1c-a033-48e6-8390-206f6b1f56c0'] > .button");
    public static SelenideElement card2 = $("[data-test-id='0f3f5c2a-249e-4c3d-8287-09f7a039391d'] > .button");

    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public static TransferPage pushFirstCard() {
        card1.click();
        return new TransferPage();
    }

    public static TransferPage pushSecondCard() {
        card2.click();
        return new TransferPage();
    }

    public int getFirstCardBalance() {
        val balance = cards.first().text();
        return extractBalance(balance);
    }

    public int getSecondCardBalance() {
        val balance = cards.last().text();
        return extractBalance(balance);
    }

    private int extractBalance(String balance) {
        val start = balance.indexOf(balanceStart);
        val finish = balance.indexOf(balanceFinish);
        val value = balance.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
