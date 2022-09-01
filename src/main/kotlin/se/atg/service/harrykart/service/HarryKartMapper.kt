package se.atg.service.harrykart.service

import org.springframework.stereotype.Component
import se.atg.service.harrykart.HarryKartType
import se.atg.service.harrykart.LaneType
import se.atg.service.harrykart.model.HarryKart
import se.atg.service.harrykart.PowerUpsType
import se.atg.service.harrykart.LoopType
import se.atg.service.harrykart.StartListType
import se.atg.service.harrykart.ParticipantType
import se.atg.service.harrykart.model.Loop
import se.atg.service.harrykart.model.Participant

@Component
class HarryKartMapper {

    fun mapToHarryKart(harryKartType: HarryKartType) = HarryKart(
        numberOfLoops = harryKartType.numberOfLoops.toInt(),
        loops = harryKartType.powerUps.mapLoops(),
        participants = harryKartType.startList.mapParticipants()
    )
    private fun PowerUpsType.mapLoops(): List<Loop> {
        return loop.map { it.mapLoop() }
    }

    private fun LoopType.mapLoop() = Loop(
        laneToPowerUp = mapLaneToPowerUp(lane),
        loopNumber = number.toInt()
    )

    private fun mapLaneToPowerUp(laneTypeList: List<LaneType>) =
        laneTypeList.associate { laneType -> laneType.number.toInt() to laneType.value.toInt() }


    private fun StartListType.mapParticipants(): List<Participant> {
        return participant.map { it.mapParticipant() }
    }

    private fun ParticipantType.mapParticipant() = Participant(
        name = name,
        lane = lane.toInt(),
        baseSpeed = baseSpeed.toInt()
    )
}