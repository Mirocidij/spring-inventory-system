package com.gh.mirocodij.inventory.system.springinventorysystem.config.converters;

import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModel;
import com.gh.mirocodij.inventory.system.springinventorysystem.model.DemoModelDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DemoModelConverters {
    @Service
    public class DemoModelToDemoModelDtoConverter implements Converter<DemoModel, DemoModelDto> {

        @Override
        public DemoModelDto convert(DemoModel source) {
            var demoModelDto = new DemoModelDto();
            demoModelDto.setId(source.getId());
            demoModelDto.setDemoField(source.getDemoField());
            return demoModelDto;
        }
    }

    @Service
    public class DemoModelDtoToDemoModelConverter implements Converter<DemoModelDto, DemoModel> {

        @Override
        public DemoModel convert(DemoModelDto source) {
            var demoModel = new DemoModel();
            demoModel.setDemoField(source.getDemoField());
            if(source.getId() != null) {
                demoModel.setId(source.getId());
            }
            return demoModel;
        }
    }
}
