package se.atg.service.harrykart.service

import org.junit.Assert
import org.junit.Test
import se.atg.service.harrykart.LaneType
import se.atg.service.harrykart.LoopType
import se.atg.service.harrykart.ParticipantType
import se.atg.service.harrykart.TestData
import se.atg.service.harrykart.model.Loop
import se.atg.service.harrykart.model.Participant
import java.math.BigInteger
import java.util.function.Consumer

class HarryKartMapperTest {

    @Test
    fun mapperTest() {
        val harryKartType = TestData.harryKartType()
        val (numberOfLoops, participants, loops) = harryKartMapper.mapToHarryKart(harryKartType)
        Assert.assertEquals(
            "Unexpected number of loops.", harryKartType.numberOfLoops,
            BigInteger.valueOf(numberOfLoops.toLong())
        )
        assertParticipants(harryKartType.startList.participant, participants)
        assertLoops(harryKartType.powerUps.loop, loops)
    }

    companion object {

        private val harryKartMapper = HarryKartMapper()
        private fun assertLoops(loop: List<LoopType>, loops: List<Loop>) {
            Assert.assertEquals("Unexpected loops size", loop.size.toLong(), loops.size.toLong())
            for (i in loop.indices) {
                assertLoop(loop[i], loops[i])
            }
        }

        private fun assertLoop(loopType: LoopType, loop: Loop) {
            Assert.assertEquals(
                "Unexpected loop number", loopType.number,
                BigInteger.valueOf(loop.loopNumber.toLong())
            )
            loopType.lane.forEach(Consumer { lane: LaneType ->
                Assert.assertEquals(
                    "Unexpected power up value", lane.value.toInt(), loop.laneToPowerUp[lane.number.toInt()]
                )
            })
        }

        private fun assertParticipants(
            participant: List<ParticipantType>,
            participants: List<Participant>
        ) {
            Assert.assertEquals(
                "Unexpected start list participants size",
                participant.size.toLong(),
                participants.size.toLong()
            )
            for (i in participant.indices) {
                assertParticipant(participant[i], participants[i])
            }
        }

        private fun assertParticipant(participantType: ParticipantType, participant: Participant) {
            Assert.assertEquals("Unexpected participant name", participantType.name, participant.name)
            Assert.assertEquals(
                "Unexpected lane number",
                participantType.lane,
                BigInteger.valueOf(participant.lane.toLong())
            )
            Assert.assertEquals(
                "Unexpected base speed",
                participantType.baseSpeed,
                BigInteger.valueOf(participant.baseSpeed.toLong())
            )
        }
    }
}