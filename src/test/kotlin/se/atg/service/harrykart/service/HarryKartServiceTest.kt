package se.atg.service.harrykart.service

import com.google.gson.Gson
import org.junit.Assert
import org.junit.Test
import se.atg.service.harrykart.TestData
import se.atg.service.harrykart.rest.model.PlayResults
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class HarryKartServiceTest {

    private lateinit var harryKartService: HarryKartService
    private lateinit var playResults: PlayResults
    @Test
    @Throws(IOException::class)
    fun input0Test() {
        givenHarryKartService()
        whenPlayingHarryKartWithInputDataFromFile("src/main/resources/input_0.xml")
        thenPlayResultsAreEqualToOutputDataFromFile("src/test/resources/output_0.json")
    }

    @Test
    @Throws(IOException::class)
    fun input1Test() {
        givenHarryKartService()
        whenPlayingHarryKartWithInputDataFromFile("src/main/resources/input_1.xml")
        thenPlayResultsAreEqualToOutputDataFromFile("src/test/resources/output_1.json")
    }

    private fun givenHarryKartService() {
        harryKartService = HarryKartService()
    }

    private fun whenPlayingHarryKartWithInputDataFromFile(inputFile: String) {
        val harryKart = HarryKartMapper().mapToHarryKart(TestData.harryKartFromFile(inputFile))
        playResults = harryKartService.playHarryKart(harryKart)
    }

    @Throws(IOException::class)
    private fun thenPlayResultsAreEqualToOutputDataFromFile(outputFile: String) {
        val json = java.lang.String.join("", Files.readAllLines(Paths.get(outputFile)))
        val expected = Gson().fromJson(json, PlayResults::class.java)
        Assert.assertEquals(expected, playResults)
    }
}