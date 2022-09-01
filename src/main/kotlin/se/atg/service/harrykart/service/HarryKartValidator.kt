package se.atg.service.harrykart.service

import org.springframework.util.Assert
import se.atg.service.harrykart.HarryKartType
import se.atg.service.harrykart.LaneType
import se.atg.service.harrykart.ParticipantType
import java.util.stream.IntStream
import java.util.stream.Collectors
import java.math.BigInteger
import se.atg.service.harrykart.LoopType
import java.util.function.Consumer

object HarryKartValidator {

    @JvmStatic
    fun validate(harryKartType: HarryKartType) {
        validateParticipants(harryKartType)
        validateNumberOfLoops(harryKartType)
        validateLanes(harryKartType)
        validateSpeed(harryKartType)
    }

    private fun validateParticipants(harryKartType: HarryKartType) {
        val participantTypeList = harryKartType.startList.participant
        val numberOfDistinctParticipants = participantTypeList.map { obj: ParticipantType -> obj.name }
            .distinct().count()
        Assert.isTrue(
            numberOfDistinctParticipants == participantTypeList.size,
            "Duplicate participants in startList."
        )
        Assert.isTrue(
            numberOfDistinctParticipants >= 3, String.format(
                "NumberOfParticipants (%s) must be at least 3 participants.",
                numberOfDistinctParticipants
            )
        )
    }

    private fun validateNumberOfLoops(harryKartType: HarryKartType) {
        val numberOfLoops = harryKartType.numberOfLoops.toInt()
        Assert.isTrue(
            numberOfLoops > 0, String.format(
                "Invalid numberOfLoops (%s). Must be greater than zero.",
                numberOfLoops
            )
        )
        val expectedLoopNumbers = IntStream.range(1, numberOfLoops).boxed().collect(Collectors.toList())
        val loopNumbers = harryKartType.powerUps.loop.map { obj: LoopType -> obj.number.toInt() }.sorted()
        Assert.isTrue(
            expectedLoopNumbers == loopNumbers, String.format(
                "NumberOfLoops (%s) requires loop numbers: (%s), got: (%s).",
                numberOfLoops, expectedLoopNumbers, loopNumbers
            )
        )
    }

    private fun validateLanes(harryKartType: HarryKartType) {
        val participants = harryKartType.startList.participant
        val participantLanes = participants.map { obj: ParticipantType -> obj.lane }.sorted().distinct()
        Assert.isTrue(
            participants.size == participantLanes.size,
            "Duplicate lanes in startList."
        )
        harryKartType.powerUps.loop.forEach(Consumer { loopType: LoopType ->
            val loopLanes = loopType.lane.stream()
                .map { obj: LaneType -> obj.number }.sorted().distinct().collect(Collectors.toList())
            Assert.isTrue(
                participantLanes == loopLanes, String.format(
                    "Participant lanes (%s) is not equal to loop number %s's lanes (%s)",
                    participantLanes, loopType.number, loopLanes
                )
            )
        })
    }

    private fun validateSpeed(harryKartType: HarryKartType) {
        harryKartType.startList.participant.forEach(Consumer { participant: ParticipantType ->
            var speed = participant.baseSpeed.toInt()
            val laneNumber = participant.lane
            Assert.isTrue(
                speed > 0, String.format(
                    "Participant (%s) base speed (%s) must be greater than zero.",
                    participant.name, participant.baseSpeed
                )
            )
            val powerUps = harryKartType.powerUps.loop
                .map { loop: LoopType -> getPowerUpForLoopAndLane(loop, laneNumber) }
            for (i in powerUps.indices) {
                Assert.isTrue(
                    speed + powerUps[i] > 0, String.format(
                        "Participant (%s) speed after power up number (%s) is non-positive (%s))",
                        participant.name, i + 1, speed + powerUps[i]
                    )
                )
                speed += powerUps[i]
            }
        })
    }

    private fun getPowerUpForLoopAndLane(loop: LoopType, laneNumber: BigInteger): Int {
        return loop.lane.first { lane: LaneType -> lane.number == laneNumber }.value.toInt()
    }
}