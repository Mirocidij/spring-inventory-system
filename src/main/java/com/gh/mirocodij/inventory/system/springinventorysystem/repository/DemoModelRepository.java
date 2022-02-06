package com.gh.mirocodij.inventory.system.springinventorysystem.repository;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface DemoModelRepository extends JpaRepository<DemoModel, UUID> {

    List<DemoModel> findDemoModelByCreationDate(LocalDate creationDate);

    DemoModel findDemoModelByDemoField(String demoField);
}
