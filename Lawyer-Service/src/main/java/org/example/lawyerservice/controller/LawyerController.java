package org.example.lawyerservice.controller;

import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class LawyerController {

    private final LawyerService lawyerService;

    ModelMapper modelMapper = new ModelMapper();

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_PATH = "/findById/{lawyerId}";
    private final String NUMBER_VARIABLE_PATH = "lawyerId";

    private final String NAME_PATH = "/fingByName/{lawyerName}";
    private final String NAME_VARIABLE_PATH = "lawyerName";

    @GetMapping("/testMethod")
    ResponseEntity<LawyerDTO> testMethod() {
        Lawyer testLawyer = Lawyer.builder().id("1").name("test_Method").build();
        LawyerDTO responseDTO = modelMapper.map(testLawyer, LawyerDTO.class);
        ResponseEntity<LawyerDTO> response = new ResponseEntity<>(responseDTO, HttpStatusCode.valueOf(201));
        LOGGER.info("Test Method was requested!");
        return response;
    }

    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(modelMapper.map(lawyerDTO,Lawyer.class));
        LOGGER.info("Lawyer: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawyerDTO.class),
                HttpStatus.valueOf(201));
    }

    @GetMapping(NUMBER_PATH)
    ResponseEntity<LawyerDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId) {
        LawyerDTO foundedById = modelMapper.map(lawyerService.getLawyerByID(lawyerId),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @GetMapping(NAME_PATH)
    ResponseEntity<LawyerDTO> getLawyerByName(@PathVariable(NAME_VARIABLE_PATH) String lawyerName) {
        LawyerDTO foundedByName = modelMapper.map(lawyerService.getLawyerByName(lawyerName),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by name in the database!", foundedByName.getName());
        return new ResponseEntity<>(foundedByName, HttpStatus.valueOf(200));
    }

    @GetMapping("/getAllLawyers")
    ResponseEntity<List<LawyerDTO>> getAllLawyers() {
        List<Lawyer> lawyers = lawyerService.getAllLawyers();
        if (lawyers.isEmpty()) {
            throw new LayerInstantiationException("There is no lawyers in the database!");
        }
        LOGGER.info("All Lawyers were founded!");
        List<LawyerDTO> lawyersDTO = lawyers.stream()
                .map(lawyer -> modelMapper.map(lawyer,LawyerDTO.class))
                .toList();
        return new ResponseEntity<>(lawyersDTO, HttpStatus.valueOf(200));
    }

    @PutMapping("/updateById/{lawyerId}")
    ResponseEntity<LawyerDTO> updateLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId,
                                               @RequestBody Lawyer lawyer) {
        Lawyer updated = lawyerService.updateLawyerById(lawyerId, lawyer);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated tp the database!", lawyerId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> updateLawyerByName(@RequestParam String lawyerName, @RequestBody Lawyer lawyer){
        Lawyer updated = lawyerService.updateLawyerByName(lawyerName, lawyer);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated tp the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }
    Lawyer deleteLawyerById(String id){
        return null;
    }
    Lawyer deleteLawyerByName(String name){
        return null;
    }
    ResponseEntity <String> deleteAlLawyers(){
        return null;
    }
}
