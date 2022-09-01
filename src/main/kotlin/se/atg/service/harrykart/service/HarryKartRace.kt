package se.atg.service.harrykart.service

import se.atg.service.harrykart.model.HarryKart
import se.atg.service.harrykart.model.Loop
import se.atg.service.harrykart.model.Participant
import se.atg.service.harrykart.rest.model.PlayResults
import se.atg.service.harrykart.rest.model.RankingPosition

class HarryKartRace private constructor(val harryKart: HarryKart) {

    data class LaneData(
        val participant: String,
        var totalTime: Double,
        var currentSpeed: Int
    )

    private val laneMap = mutableMapOf<Int, LaneData>()
    val playResults: PlayResults
        get() = calculateResults()

    private fun play(): HarryKartRace {
        harryKart.participants.forEach { participant: Participant ->
            laneMap[participant.lane] = LaneData(
                participant = participant.name,
                totalTime = 1000.0 / participant.baseSpeed,
                currentSpeed = participant.baseSpeed
            )
        }
        harryKart.loops.forEach { loop: Loop ->
            loop.laneToPowerUp.forEach { (lane: Int, powerUp: Int) ->
                laneMap[lane]?.let {
                    val speed = it.currentSpeed + powerUp
                    it.currentSpeed = speed
                    it.totalTime += 1000.0 / speed
                }
            }
        }
        return this
    }

    private fun calculateResults(): PlayResults {
        val totalTimeToLanes = mutableMapOf<Double, MutableList<Int>>()
        laneMap.forEach { (lane, laneData) ->
            totalTimeToLanes.getOrPut(laneData.totalTime) { mutableListOf() }.apply {
                add(lane)
            }
        }
        val results = mutableListOf<RankingPosition>()
        totalTimeToLanes.toSortedMap().forEach { (_, lanes: List<Int>) ->
            if (results.size < 3) {
                val currentPosition = results.size + 1
                lanes.forEach { lane: Int ->
                    results.add(
                        RankingPosition(
                            currentPosition,
                            laneMap[lane]?.participant ?: throw RuntimeException()
                        )
                    )
                }
            }
        }
        return PlayResults(results)
    }

    companion object {

        @JvmStatic
        fun play(harryKart: HarryKart): HarryKartRace {
            return HarryKartRace(harryKart).play()
        }
    }
}