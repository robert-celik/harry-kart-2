package se.atg.service.harrykart.service

import org.springframework.stereotype.Service
import se.atg.service.harrykart.service.HarryKartRace.Companion.play
import se.atg.service.harrykart.model.HarryKart
import se.atg.service.harrykart.rest.model.PlayResults

@Service
class HarryKartService {

    fun playHarryKart(harryKart: HarryKart): PlayResults {
        return play(harryKart).playResults
    }
}