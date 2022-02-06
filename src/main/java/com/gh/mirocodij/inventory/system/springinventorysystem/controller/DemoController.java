package com.gh.mirocodij.inventory.system.springinventorysystem.controller;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import com.gh.mirocodij.inventory.system.springinventorysystem.repository.DemoModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    private final DemoModelRepository demoModelRepository;
    private final ConversionService conversionService;

    @GetMapping
    public List<DemoModelDto> findAllDemoModels() {
        log.info("IN <findAllDemoModels> METHOD");

        return demoModelRepository.findAll().stream()
                .map(demoModel -> conversionService.convert(demoModel, DemoModelDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DemoModelDto findDemoModelById(@PathVariable UUID id) {
        log.info("IN <findDemoModelById> METHOD WITH id= {}", id);

        var demoModel = demoModelRepository.findById(id).orElseThrow();
        return conversionService.convert(demoModel, DemoModelDto.class);
    }

    @PostMapping
    public DemoModelDto saveDemoModel(@RequestBody DemoModelDto demoModel) {
        log.info("IN <saveDemoModel> METHOD WITH demoModel= {}", demoModel);

        var demoModelToSave = conversionService.convert(demoModel, DemoModel.class);
        assert demoModelToSave != null;
        DemoModel savedDemoModel = demoModelRepository.save(demoModelToSave);

        return conversionService.convert(savedDemoModel, DemoModelDto.class);
    }

    @PutMapping
    public DemoModelDto updateDemoModel(@RequestBody DemoModelDto demoModel) {
        log.info("IN <updateDemoModel> METHOD WITH demoModel= {}", demoModel);

        if (demoModel.getId() == null) {
            throw new NoSuchElementException();
        }

        var demoModelToUpdate = conversionService.convert(demoModel, DemoModel.class);
        assert demoModelToUpdate != null;
        DemoModel updatedDemoModel = demoModelRepository.save(demoModelToUpdate);

        return conversionService.convert(updatedDemoModel, DemoModelDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteDemoModelById(@PathVariable UUID id) {
        log.info("IN <deleteDemoModelById> METHOD WITH id= {}", id);

        var demoModelToRemove = demoModelRepository.findById(id).orElseThrow();

        demoModelRepository.delete(demoModelToRemove);
    }

    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<String> noSuchElementExceptionHandler(NoSuchElementException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<String> exceptionHandler(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
