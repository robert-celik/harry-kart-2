package se.atg.service.harrykart.model

data class Loop(
    val loopNumber: Int,
    val laneToPowerUp: Map<Int, Int>
)