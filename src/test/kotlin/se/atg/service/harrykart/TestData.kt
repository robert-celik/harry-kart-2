package se.atg.service.harrykart

import java.io.File
import java.lang.RuntimeException
import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBElement
import javax.xml.bind.JAXBException

object TestData {

    fun harryKartType(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_three_number_of_loops.xml")
    }

    fun harryKartTypeWithTwoParticipants(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_two_participants.xml")
    }

    fun harryKartTypeWithInvalidPowerUpLoops(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_invalid_power_up_loops.xml")
    }

    fun harryKartTypeWithInvalidNumberOfLoops(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_invalid_number_of_loops.xml")
    }

    fun harryKartTypeWithDuplicateParticipantNames(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_duplicate_participant_names.xml")
    }

    fun harryKartTypeWithDuplicateParticipantLaneNumbers(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_duplicate_participant_lane_numbers.xml")
    }

    fun harryKartTypeWithInvalidParticipantLaneNumbers(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_invalid_participant_lane_numbers.xml")
    }

    fun harryKartTypeWithInvalidBaseSpeed(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_invalid_base_speed.xml")
    }

    fun harryKartTypeWithInvalidSpeedAfterPowerUp(): HarryKartType {
        return harryKartFromFile("src/test/resources/harry_kart_type_with_invalid_speed_after_power_up.xml")
    }

    fun harryKartFromFile(filePath: String): HarryKartType {
        return try {
            val jaxbContext = JAXBContext.newInstance(ObjectFactory::class.java)
            val unmarshaller = jaxbContext.createUnmarshaller()
            val element = unmarshaller.unmarshal(File(filePath)) as JAXBElement<*>
            element.value as HarryKartType
        } catch (ex: JAXBException) {
            throw RuntimeException(ex)
        }
    }
}