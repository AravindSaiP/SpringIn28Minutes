package com.example.RESTAPI.SocialMediaApplication.filtering;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/static-filtering")
    public SomeBean filter(){
        return new SomeBean("value 1","value 2","value 3","value 4");
    }

    @GetMapping("/static-filtering-list")
    public List<SomeBean> filterList(){
        return Arrays.asList(
                new SomeBean("value 1","value 2","value 3","value 4"),
                new SomeBean("value10","value20","value30","value40")
        );
    }

    @GetMapping("/dynamic-filtering")
    public MappingJacksonValue dynamicFilter(){
        SomeBean someBean = new SomeBean("value 1", "value 2", "value 3", "value 4");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
        FilterProvider filters = new SimpleFilterProvider().addFilter("some_bean_filter",filter);

        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }

    @GetMapping("/dynamic-filtering-list")
    public MappingJacksonValue dynamicFilterList(){
        List<SomeBean> someBeans = Arrays.asList(
                new SomeBean("value 1", "value 2", "value 3", "value 4"),
                new SomeBean("value10", "value20", "value30", "value40")
        );

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBeans);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");

        FilterProvider filters = new SimpleFilterProvider().addFilter("some_bean_filter",filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
