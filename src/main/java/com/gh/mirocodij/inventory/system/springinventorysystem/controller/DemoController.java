package com.gh.mirocodij.inventory.system.springinventorysystem.controller;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import com.gh.mirocodij.inventory.system.springinventorysystem.repository.DemoModelRepository;
import com.gh.mirocodij.inventory.system.springinventorysystem.service.DemoModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {
    DemoModelService demoModelService;

    @GetMapping
    public List<DemoModelDto> findAllDemoModels() {
        return demoModelService.findAllDemoModels();
    }

    @GetMapping("/{id}")
    public DemoModelDto findDemoModelById(@PathVariable UUID id) {
        return demoModelService.findDemoModelById(id);
    }

    @PostMapping
    public DemoModelDto saveDemoModel(@RequestBody DemoModelDto demoModel) {
        return demoModelService.saveDemoModel(demoModel);
    }

    @PutMapping
    public DemoModelDto updateDemoModel(@RequestBody DemoModelDto demoModel) {
        return demoModelService.updateDemoModel(demoModel);
    }

    @DeleteMapping("/{id}")
    public void deleteDemoModelById(@PathVariable UUID id) {
        demoModelService.deleteDemoModelById(id);
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
