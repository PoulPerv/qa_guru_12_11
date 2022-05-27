package com.pablo.tests;

import com.pablo.data.Generator;
import com.pablo.pages.RegistrationFormPage;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static io.qameta.allure.Allure.step;
import static java.lang.String.format;

@Tag("properties")
public class RegistrationFormTests extends TestBase {

    Generator gen = new Generator();
    String name = gen.getFirstName(),
            lastName = gen.getLastName(),
            email = gen.getEmail();
    LocalDate date = gen.getDate();
    String expectedMonth = StringUtils.capitalize(date.getMonth().toString().toLowerCase()); //Capitalized month name
    String expectedDate = format("%s %s,%s", date.getDayOfMonth(), expectedMonth, date.getYear());
    String      gender = gen.getGender(),
            hobby = gen.getHobby(),
            subject = gen.getSubject(),
            currentAddress = gen.getAddress();
    String state = gen.getState();
    String city = gen.getCity(state);
    String expectedFullName = format("%s %s", name, lastName);
    String mobile = gen.getPhoneNumber();
    String img = "cv.jpg";
    String expectedStateCity = format("%s %s", state, city);

    RegistrationFormPage registrationFormPage = new RegistrationFormPage();


    @Test
    @DisplayName("Successful fill registration test")
    void fillFormTest() {
        step("Open registration form", () -> {
                    registrationFormPage.openPage();
                });
        step("Fill registration form", () -> {
            registrationFormPage
                    .setFirstName(name)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setBirthDate(date)
                    .setGender(gender)
                    .setPhoneNumber(mobile)
                    .setAddress(currentAddress)
                    .setSubject(subject)
                    .setHobby(hobby)
                    .setStateAndCity(state, city)
                    .uploadPicture(img)
                    .submitForm();
        });
        step("Verify form data", () -> {
            registrationFormPage.checkResult("Student Name", expectedFullName)
                    .checkResult("Student Email", email)
                    .checkResult("Gender", gender)
                    .checkResult("Mobile", mobile)
                    .checkResult("Date of Birth", expectedDate)
                    .checkResult("Subjects", subject)
                    .checkResult("Hobbies", hobby)
                    .checkResult("Picture", img)
                    .checkResult("Address", currentAddress)
                    .checkResult("State and City", expectedStateCity);

        });
    }
}

