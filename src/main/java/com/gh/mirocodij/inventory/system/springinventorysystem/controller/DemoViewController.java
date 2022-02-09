package com.gh.mirocodij.inventory.system.springinventorysystem.controller;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import com.gh.mirocodij.inventory.system.springinventorysystem.service.DemoModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

/**
 * @author Anatoly Tsybulya
 */
@Controller
@RequestMapping("demo-view")
public class DemoViewController {
    private final DemoModelService demoModelService;

    @Autowired
    public DemoViewController(DemoModelService demoModelService) {
        this.demoModelService = demoModelService;
    }

    @GetMapping("/models")
    public String findAllDemoModels(Model model) {
        List<DemoModelDto> dtoList = demoModelService.findAllDemoModels();
        if (dtoList.size() != 0) {
            model.addAttribute("models", dtoList);
        }
        return "demomodel-list";
    }

    @GetMapping("/model-create")
    public String createDemoModelForm(DemoModelDto demoModelDto) {
        return "demomodel-create";
    }

    @PostMapping("/model-create")
    public String createDemoModel(DemoModelDto demoModelDto) {
        demoModelService.saveDemoModel(demoModelDto);
        return"redirect:models";
    }

    @GetMapping("/model-update/{id}")
    public String updateProjectForm(@PathVariable("id") UUID id, Model model) {
        DemoModelDto demoModelDto = demoModelService.findDemoModelById(id);
        model.addAttribute("model", demoModelDto);
        return "demomodel-update";
    }

    @PostMapping("/model-update")
    public String updateProject(DemoModelDto demoModelDto) {
        demoModelService.updateDemoModel(demoModelDto);
        return "redirect:models";
    }

    @GetMapping("/model-delete/{id}")
    public String deleteProject(@PathVariable("id") UUID id) {
        demoModelService.deleteDemoModelById(id);
        return "redirect:/demo-view/models";
    }
}
