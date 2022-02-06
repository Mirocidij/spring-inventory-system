package com.gh.mirocodij.inventory.system.springinventorysystem.repository;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface DemoModelRepository extends JpaRepository<DemoModel, UUID> {

    DemoModel findDemoModelByCreationDate(LocalDate creationDate);

    @Query("select d from DemoModel d where d.creationDate = ?1 and d.demoField = ?2")
    DemoModel findDemoModelByCreationDateAndDemoField(LocalDate creationDate, String demoField);

    DemoModel findDemoModelByDemoField(String demoField);
}
