package org.example.lawyerservice.controller.crud;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.lawyerservice.controller.ParentController;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.mapper.LawCaseMapper;
import org.example.lawyerservice.mapper.LawyerMapper;
import org.example.lawyerservice.service.LawyerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lawyer/get")
public class GetController extends ParentController {

    public GetController(LawyerService lawyerService, LawyerMapper lawyerMapper, LawCaseMapper lawCaseMapper) {
        super(lawyerService, lawyerMapper, lawCaseMapper);
    }

    @Operation(
            description = "Get Lawyer from the database by the id - api/lawyer/get/byId/1"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully by id"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_ID),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)

    })
    @GetMapping("/byId" + NUMBER_QUERY_PATH)
    ResponseEntity<LawyerDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId) {
        LawyerDTO foundedById = lawyerMapper.toDTO(lawyerService.getLawyerByID(lawyerId));
        LOGGER.info("Lawyer: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get Lawyer from the database by the name - api/lawyer/get/byName?lawyerName=Piotr+Hic"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lawyer delivered successfully by name"),
            @ApiResponse(responseCode = "404", description = DESCRIPTION_404_NAME),
            @ApiResponse(responseCode = "500", description = DESCRIPTION_500_LONG)
    })
    @GetMapping("/byName") // ?lawyerName=
    ResponseEntity<LawyerDTO> getLawyerByName(@RequestParam(NAME_VARIABLE_PATH) String lawyerName) {
        LawyerDTO foundedByName = lawyerMapper.toDTO(lawyerService.getLawyerByName(lawyerName));
        LOGGER.info("Lawyer: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @Operation(
            description = "Get all Lawyers from the Database - api/lawyer/get/allLawyers"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Lawyers delivered successfully"),
            @ApiResponse(responseCode = "500", description = "Empty Database or Internal Server Errors")
    })
    @GetMapping("/allLawyers")
    ResponseEntity<List<LawyerDTO>> getAllLawyers() {
        List<Lawyer> lawyers = lawyerService.getAllLawyers();
        if (lawyers.isEmpty()) {
            throw new LayerInstantiationException("There is no lawyers in the database!");
        }
        LOGGER.info("All Lawyers were founded!");
        List<LawyerDTO> lawyersDTO = lawyers.stream()
                .map(lawyerMapper::toDTO)
                .toList();
        return new ResponseEntity<>(lawyersDTO, HttpStatus.valueOf(200));
    }

}
