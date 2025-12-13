package org.example.lawyerservice.controller.crud;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.lawyerservice.controller.ParentController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawCaseMapper;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lawyer/update")
public class UpdateController extends ParentController {

    public UpdateController(LawyerService lawyerService, LawyerMapper lawyerMapper, LawCaseMapper lawCaseMapper) {
        super(lawyerService, lawyerMapper, lawCaseMapper);
    }

    @Operation(
            description = "Update Lawyer from the database by the id  - /api/lawyer/update/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer updated successfully by id"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @PutMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> updateLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId,
                                               @Valid @RequestBody LawyerDTO lawyerDTO) {
        LOGGER.info("BODY = {}", lawyerDTO);
        Lawyer toUpdate = lawyerMapper.toEntity(lawyerDTO);
        Lawyer updated = lawyerService.updateLawyerById(lawyerId, toUpdate);
        LawyerDTO updatedDTO = lawyerMapper.toDTO(updated);
        LOGGER.info("Lawyer with id: {} was updated by id to the database!", lawyerId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Update Lawyer from the database by the name  " +
                    "- /api/lawyer/update/byName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer updated successfully by name"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @PutMapping("/byName") // ?lawyerName=
    ResponseEntity<LawyerDTO> updateLawyerByName(@RequestParam String lawyerName,
                                                 @Valid @RequestBody LawyerDTO lawyerDTO){
        LOGGER.info("BODY = {}", lawyerDTO);
        Lawyer toUpdate = lawyerMapper.toEntity(lawyerDTO);
        Lawyer updated = lawyerService.updateLawyerByName(lawyerName, toUpdate);
        LawyerDTO updatedDTO = lawyerMapper.toDTO(updated);
        LOGGER.info("Lawyer with name: {} was updated by name to the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }
}
