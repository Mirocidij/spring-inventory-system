package com.gh.mirocodij.inventory.system.springinventorysystem.unit.repository.demo;

import com.gh.mirocodij.inventory.system.springinventorysystem.fixtures.BaseRepositoryTest;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import com.gh.mirocodij.inventory.system.springinventorysystem.repository.DemoModelRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class DemoRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private DemoModelRepository repository;

    @Test
    public void findDemoModelByCreationDate_shouldReturnDemoModelCorrect() {
        var demoModel = new DemoModel();
        demoModel.setDemoField("field for demo");
        DemoModel savedDemoModel = repository.save(demoModel);

        var demoModelByCreationDate = repository.findDemoModelByCreationDate(demoModel.getCreationDate());
        assertThat(demoModelByCreationDate).isNotNull();
        assertThat(demoModelByCreationDate).hasSize(1);
        var first = demoModelByCreationDate.stream().findFirst().orElseThrow();
        assertThat(first.getId()).isEqualTo(savedDemoModel.getId());
        assertThat(first.getDemoField()).isEqualTo(savedDemoModel.getDemoField());
        assertThat(first.getCreationDate()).isEqualTo(savedDemoModel.getCreationDate());
    }

    @Test void findDemoModelByDemoField_shouldReturnDemoModelCorrect() {
        var field = "test";
        var demoModel = new DemoModel();
        demoModel.setDemoField(field);
        repository.save(demoModel);

        DemoModel demoModelByDemoField = repository.findDemoModelByDemoField(field);
        assertThat(demoModelByDemoField).isNotNull();
        assertThat(demoModelByDemoField.getDemoField()).isEqualTo(field);
    }
}
