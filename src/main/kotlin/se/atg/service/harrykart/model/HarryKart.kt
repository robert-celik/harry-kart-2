package se.atg.service.harrykart.model

data class HarryKart(
    val numberOfLoops: Int,
    val participants: List<Participant>,
    val loops: List<Loop>
)