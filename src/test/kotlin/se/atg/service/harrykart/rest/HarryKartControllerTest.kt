package se.atg.service.harrykart.rest

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import se.atg.service.harrykart.service.HarryKartMapper
import se.atg.service.harrykart.service.HarryKartService
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.Exception

@WebAppConfiguration
@ContextConfiguration(classes = [HarryKartController::class, HarryKartService::class, HarryKartMapper::class])
@EnableWebMvc
@RunWith(
    SpringJUnit4ClassRunner::class
)
class HarryKartControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    @Throws(Exception::class)
    fun whenPostingValidInput_thenReturnOKAndReturnResult() {
        val requestBody = Files.readAllLines(Paths.get("src/main/resources/input_1.xml")).joinToString()
        val produces = Files.readAllLines(Paths.get("src/test/resources/output_1.json")).joinToString("")
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/play")
                .content(requestBody)
                .header("Content-Type", "application/xml")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.OK.value()))
            .andExpect(MockMvcResultMatchers.content().json(produces))
    }

    @Test
    @Throws(Exception::class)
    fun whenPostingInvalidInput_thenReturnBadRequestAndErrorMessage() {
        val requestBody = Files.readAllLines(
            Paths.get("src/test/resources/harry_kart_type_with_invalid_base_speed.xml")
        ).joinToString()
        val errorMessage = "Participant (CARGO DOOR) base speed (-10) must be greater than zero."
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/play")
                .content(requestBody)
                .header("Content-Type", "application/xml")
        )
            .andExpect(MockMvcResultMatchers.status().`is`(HttpStatus.BAD_REQUEST.value()))
            .andExpect(MockMvcResultMatchers.content().string(errorMessage))
    }
}