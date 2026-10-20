package com.martin.car.core;

import com.martin.car.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.martin.car.core.TestCars.*;
import static org.assertj.core.api.Assertions.assertThat;

class CarValidatorImplTest {
    private final CarValidatorImpl validator = new CarValidatorImpl();

    // ---------- Yes/No ----------

    @ParameterizedTest
    @CsvSource({
            "yes, Yes",
            "NO, No",
            "yEs, Yes"
    })
    void getYesNo_normalizesCapitalization(String typed, String expected) {
        assertThat(validator.getYesNo(scanner(typed), "Prompt: ")).isEqualTo(expected);
    }

    @Test
    void getYesNo_asksAgainAfterBlankAndInvalidInput() {
        assertThat(validator.getYesNo(scanner("", "   ", "maybe", "no"), "Prompt: ")).isEqualTo("No");
    }

    // ---------- Names and models ----------

    @ParameterizedTest
    @CsvSource({
            "john   smith, John Smith",
            "mARY jane, Mary Jane",
            "123 main st, 123 Main St"
    })
    void checkCapitalization_capitalizesEachWord(String typed, String expected) {
        assertThat(validator.checkCapitalization(scanner(typed), "Prompt: ")).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "cx-5, CX-5",
            "CR-V HYBRID, CR-V Hybrid",
            "model y, Model Y",
            "camry, Camry",
            "grand cherokee, Grand Cherokee"
    })
    void modelMethod_usesKnownSpellingsAndCapitalizesUnknownModels(String typed, String expected) {
        assertThat(validator.modelMethod(scanner(typed), "Prompt: ")).isEqualTo(expected);
    }

    // ---------- VIN ----------

    @Test
    void vin_acceptsValidVinAndUppercasesIt() {
        String vin = validator.vin(scanner("1hgcm82633a004352"), List.of(), "VIN: ", true);

        assertThat(vin).isEqualTo(SEDAN_VIN);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123",
            "1HGCM82633A0043520"
    })
    void vin_rejectsWrongLengthAndAsksAgain(String badVin) {
        String vin = validator.vin(scanner(badVin, SEDAN_VIN), List.of(), "VIN: ", true);

        assertThat(vin).isEqualTo(SEDAN_VIN);
    }

    @Test
    void vin_rejectsDuplicateWhenCheckingDuplicates() {
        List<Car> cars = List.of(newSedan(SEDAN_VIN));

        // The first entry matches an existing car (in lowercase), so it must be rejected.
        String vin = validator.vin(scanner("1hgcm82633a004352", OTHER_VIN), cars, "VIN: ", true);

        assertThat(vin).isEqualTo(OTHER_VIN);
    }

    // ---------- Numbers ----------

    @Test
    void checkShort_acceptsWholeNumber() {
        assertThat(validator.checkShort(scanner("2020"), "Prompt: ")).isEqualTo((short) 2020);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "1.5",
            "99999"
    })
    void checkShort_rejectsTextDecimalsAndOutOfRangeNumbers(String badInput) {
        assertThat(validator.checkShort(scanner(badInput, "2020"), "Prompt: ")).isEqualTo((short) 2020);
    }

    @Test
    void checkDouble_acceptsDecimalNumber() {
        assertThat(validator.checkDouble(scanner("15.5"), "Prompt: ")).isEqualTo(15.5);
    }

    @Test
    void checkDouble_rejectsTextAndAsksAgain() {
        assertThat(validator.checkDouble(scanner("abc", "2"), "Prompt: ")).isEqualTo(2.0);
    }

    // ---------- Menu ----------

    @ParameterizedTest
    @ValueSource(ints = {
            1,
            5,
            11
    })
    void menuMethod_acceptsOptionsOneThroughEleven(int option) {
        assertThat(validator.menuMethod(scanner(String.valueOf(option)))).isEqualTo((byte) option);
    }

    @Test
    void menuMethod_rejectsOutOfRangeAndNonNumericInput() {
        assertThat(validator.menuMethod(scanner("0", "12", "abc", "5"))).isEqualTo((byte) 5);
    }

    // ---------- Enums ----------

    @ParameterizedTest
    @CsvSource({
            "4wd, FOUR_WD",
            "AWD, AWD",
            "rwd, RWD",
            "fwd, FWD"
    })
    void checkDriveType_acceptsAnyCaseAndMapsFourWheelDrive(String typed, String expectedName) {
        assertThat(validator.checkDriveType(scanner(typed), "Prompt: ").name()).isEqualTo(expectedName);
    }

    @Test
    void checkDriveType_asksAgainAfterInvalidInput() {
        assertThat(validator.checkDriveType(scanner("xyz", "rwd"), "Prompt: ")).isEqualTo(DriveType.RWD);
    }

    @Test
    void checkCarSize_acceptsAnyCase() {
        assertThat(validator.checkCarSize(scanner("midsize"), "Prompt: ")).isEqualTo(CarSize.MidSize);
    }

    @Test
    void checkTransmissionType_acceptsAnyCase() {
        assertThat(validator.checkTransmissionType(scanner("cvt"), "Prompt: ")).isEqualTo(TransmissionType.CVT);
    }

    @Test
    void checkCabType_asksAgainAfterInvalidInput() {
        assertThat(validator.checkCabType(scanner("huge", "extended"), "Prompt: ")).isEqualTo(CabType.Extended);
    }
}
