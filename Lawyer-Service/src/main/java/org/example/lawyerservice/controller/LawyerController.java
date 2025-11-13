package org.example.lawyerservice.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.lawyerservice.domain.DTO.LawyerDTO;
import org.example.lawyerservice.domain.Lawyer;
import org.example.lawyerservice.service.LawyerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/lawyer")
public class LawyerController {

    private final LawyerService lawyerService;

    ModelMapper modelMapper;

    private static final Logger LOGGER
            = LoggerFactory.getLogger(LawyerController.class);

    private final String NUMBER_VARIABLE_PATH = "lawyerId";
    private final String NAME_VARIABLE_PATH = "lawyerName";


    @PostMapping
    ResponseEntity<LawyerDTO> createLawyer(@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer added = lawyerService.addLawyer(modelMapper.map(lawyerDTO,Lawyer.class));
        LOGGER.info("Lawyer: {} was added tp the database!", added.getName());
        return new ResponseEntity<>(modelMapper.map(added,LawyerDTO.class),
                HttpStatus.valueOf(201));
    }

    @GetMapping("/getById/{lawyerId}")
    ResponseEntity<LawyerDTO> getLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId) {
        LawyerDTO foundedById = modelMapper.map(lawyerService.getLawyerByID(lawyerId),LawyerDTO.class);
        LOGGER.info("Lawyer: {} was founded by id in the database!", foundedById.getName());
        return new ResponseEntity<>(foundedById, HttpStatus.valueOf(200));
    }

    @GetMapping("/getByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> getLawyerByName(@RequestParam(NAME_VARIABLE_PATH) String lawyerName) {
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
                                               @Valid @RequestBody LawyerDTO lawyerDTO) {
        Lawyer toUpdate = modelMapper.map(lawyerDTO, Lawyer.class);
        Lawyer updated = lawyerService.updateLawyerById(lawyerId, toUpdate);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated by id to the database!", lawyerId);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @PutMapping("/updateByName") // ?lawyerName=
    ResponseEntity<LawyerDTO> updateLawyerByName(@RequestParam String lawyerName,@Valid @RequestBody LawyerDTO lawyerDTO){
        Lawyer toUpdate = modelMapper.map(lawyerDTO, Lawyer.class);
        Lawyer updated = lawyerService.updateLawyerByName(lawyerName, toUpdate);
        LawyerDTO updatedDTO = modelMapper.map(updated, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was updated by name to the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/deleteById/{lawyerId}")
    ResponseEntity <LawyerDTO> deleteLawyerById(@PathVariable(NUMBER_VARIABLE_PATH) String lawyerId){
        LawyerDTO deleted = modelMapper.map(lawyerService.deleteLawyerById(lawyerId), LawyerDTO.class);
        LOGGER.info("Lawyer deleted: {} by id from the database!", deleted.getName());
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteByName") // ?lawyerName=
    ResponseEntity <LawyerDTO> deleteLawyerByName(@RequestParam String lawyerName){
        Lawyer deleted= lawyerService.deleteLawyerByName(lawyerName);
        LawyerDTO updatedDTO = modelMapper.map(deleted, LawyerDTO.class);
        LOGGER.info("Lawyer: {} was deleted from the database!", lawyerName);
        return new ResponseEntity<>(updatedDTO, HttpStatus.valueOf(200));
    }

    @DeleteMapping("/deleteAll")
    ResponseEntity <String> deleteAlLawyers(){
        lawyerService.deleteAlLawyers();
        LOGGER.info("Database is empty");
        return new ResponseEntity<>("Database is empty", HttpStatus.OK);
    }
}
