package com.gh.mirocodij.inventory.system.springinventorysystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "demo_table", schema = "demo")
public class DemoModel {
    @Id @Column(name = "id") private UUID id = UUID.randomUUID();
    @Column(name = "demo_field") private String demoField;
    @Column(name = "creation_date") private LocalDate creationDate = LocalDate.now();
}
