package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.getFirstCardNumber;
import static ru.netology.data.DataHelper.getSecondCardNumber;
import static ru.netology.page.DashboardPage.pushFirstCard;
import static ru.netology.page.DashboardPage.pushSecondCard;

public class TransferTest {

    @BeforeEach
    void SetUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        val dashboardPage = new DashboardPage();
        int sum = 2_348;

        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        val transactionPage = pushSecondCard();
        transactionPage.successfulTopUp(sum, getSecondCardNumber());
        val firstCardBalanceResult = firstCardBalanceStart + sum;
        val secondCardBalanceResult = secondCardBalanceStart - sum;

        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        val dashboardPage = new DashboardPage();
        int sum = 1_563;

        val firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        val secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        val transactionPage = pushFirstCard();
        transactionPage.successfulTopUp(sum, getFirstCardNumber());
        val firstCardBalanceResult = firstCardBalanceStart - sum;
        val secondCardBalanceResult = secondCardBalanceStart + sum;

        assertEquals(firstCardBalanceResult, dashboardPage.getFirstCardBalance());
        assertEquals(secondCardBalanceResult, dashboardPage.getSecondCardBalance());
    }

    @Test
    void shouldNotTransferMoreThanAvailable() {
        int sum = 10_001;
        val transactionPage = pushSecondCard();
        transactionPage.successfulTopUp(sum, getFirstCardNumber());
        transactionPage.getErrorLimit();
    }
}
