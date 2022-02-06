package com.gh.mirocodij.inventory.system.springinventorysystem.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class DemoModelDto {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("demo_field")
    private String demoField;
}
