package se.atg.service.harrykart.rest

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import se.atg.service.harrykart.HarryKartType
import se.atg.service.harrykart.service.HarryKartMapper
import se.atg.service.harrykart.service.HarryKartService
import se.atg.service.harrykart.service.HarryKartValidator
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class HarryKartController(
    private val harryKartService: HarryKartService,
    private val harryKartMapper: HarryKartMapper
) {

    @PostMapping(
        path = ["/play"],
        consumes = [MediaType.APPLICATION_XML_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun playHarryKart(@RequestBody harryKartType: @Valid HarryKartType): ResponseEntity<Any> {
        try {
            HarryKartValidator.validate(harryKartType)
        } catch (exception: IllegalArgumentException) {
            return ResponseEntity(exception.message, HttpStatus.BAD_REQUEST)
        }
        val harryKart = harryKartMapper.mapToHarryKart(harryKartType)
        val results = harryKartService.playHarryKart(harryKart)
        return ResponseEntity(results, HttpStatus.OK)
    }
}