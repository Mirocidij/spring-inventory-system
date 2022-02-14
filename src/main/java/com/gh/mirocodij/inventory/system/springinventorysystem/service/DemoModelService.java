package com.gh.mirocodij.inventory.system.springinventorysystem.service;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import com.gh.mirocodij.inventory.system.springinventorysystem.repository.DemoModelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Anatoly Tsybulya
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DemoModelService {
    private final DemoModelRepository demoModelRepository;
    private final ConversionService conversionService;

    public List<DemoModelDto> findAllDemoModels() {
        log.info("IN <findAllDemoModels> METHOD");

        return demoModelRepository.findAll().stream()
                .map(demoModel -> conversionService.convert(demoModel, DemoModelDto.class))
                .collect(Collectors.toList());
    }

    public DemoModelDto findDemoModelById(@PathVariable UUID id) {
        log.info("IN <findDemoModelById> METHOD WITH id= {}", id);

        var demoModel = demoModelRepository.findById(id).orElseThrow();
        return conversionService.convert(demoModel, DemoModelDto.class);
    }

    public DemoModelDto saveDemoModel(@RequestBody DemoModelDto demoModel) {
        log.info("IN <saveDemoModel> METHOD WITH demoModel= {}", demoModel);

        var demoModelToSave = conversionService.convert(demoModel, DemoModel.class);
        assert demoModelToSave != null;
        DemoModel savedDemoModel = demoModelRepository.save(demoModelToSave);

        return conversionService.convert(savedDemoModel, DemoModelDto.class);
    }

    public DemoModelDto updateDemoModel(@RequestBody DemoModelDto demoModel) {
        log.info("IN <updateDemoModel> METHOD WITH demoModel= {}", demoModel);

        if (demoModel.getId() == null || findDemoModelById(demoModel.getId()) == null) {
            throw new NoSuchElementException();
        }

        var demoModelToUpdate = conversionService.convert(demoModel, DemoModel.class);
        System.out.println(demoModelToUpdate.getId());
        System.out.println(demoModelToUpdate.getId());
        System.out.println(demoModelToUpdate.getId());
        assert demoModelToUpdate != null;
        DemoModel updatedDemoModel = demoModelRepository.save(demoModelToUpdate);

        return conversionService.convert(updatedDemoModel, DemoModelDto.class);
    }

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
