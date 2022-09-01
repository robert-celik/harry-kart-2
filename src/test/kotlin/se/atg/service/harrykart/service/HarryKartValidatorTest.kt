package se.atg.service.harrykart.service

import org.junit.Assert
import org.junit.Test
import se.atg.service.harrykart.service.HarryKartValidator.validate
import se.atg.service.harrykart.HarryKartType
import se.atg.service.harrykart.TestData
import java.lang.Exception
import java.lang.IllegalArgumentException

class HarryKartValidatorTest {

    @Test
    fun duplicateParticipantNamesTest() {
        val harryKartType = TestData.harryKartTypeWithDuplicateParticipantNames()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "Duplicate participants in startList."
        )
    }

    @Test
    fun tooFewParticipantsTest() {
        val harryKartType = TestData.harryKartTypeWithTwoParticipants()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "NumberOfParticipants (2) must be at least 3 participants."
        )
    }

    @Test
    fun invalidNumberOfLoopsTest() {
        val harryKartType = TestData.harryKartTypeWithInvalidNumberOfLoops()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "Invalid numberOfLoops (0). Must be greater than zero."
        )
    }

    @Test
    fun invalidPowerUpLoopsTest() {
        val harryKartType = TestData.harryKartTypeWithInvalidPowerUpLoops()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "NumberOfLoops (4) requires loop numbers: ([1, 2, 3]), got: ([1, 3])."
        )
    }

    @Test
    fun duplicateParticipantLaneNumbersTest() {
        val harryKartType = TestData.harryKartTypeWithDuplicateParticipantLaneNumbers()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "Duplicate lanes in startList."
        )
    }

    @Test
    fun invalidParticipantLaneNumbersTest() {
        val harryKartType = TestData.harryKartTypeWithInvalidParticipantLaneNumbers()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "Participant lanes ([1, 2, 3, 5]) is not equal to loop number 1's lanes ([1, 2, 3, 4])"
        )
    }

    @Test
    fun invalidBaseSpeedTest() {
        val harryKartType = TestData.harryKartTypeWithInvalidBaseSpeed()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "Participant (CARGO DOOR) base speed (-10) must be greater than zero."
        )
    }

    @Test
    fun invalidSpeedAfterPowerUpTest() {
        val harryKartType = TestData.harryKartTypeWithInvalidSpeedAfterPowerUp()
        validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
            harryKartType,
            "Participant (CARGO DOOR) speed after power up number (2) is non-positive (0))"
        )
    }

    private fun validateHarryKartAndExpectIllegalArgumentExceptionWithMessage(
        harryKartType: HarryKartType,
        errorMessage: String
    ) {
        val exception: Exception = Assert.assertThrows(
            IllegalArgumentException::class.java
        ) { validate(harryKartType) }
        Assert.assertEquals(errorMessage, exception.message)
    }
}