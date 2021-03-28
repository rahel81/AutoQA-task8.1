package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");

    public void dashboardPageVisible() {
        heading.shouldBe(Condition.visible).shouldHave(text("Личный кабинет"));
    }
}
