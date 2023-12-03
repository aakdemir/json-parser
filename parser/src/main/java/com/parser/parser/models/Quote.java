package com.parser.parser.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quote {

    @JsonProperty("market")
    private String market;
 
    @JsonProperty("date_applied")
    private String dateApplied;

    @JsonProperty("values")
    private List<Value> values;
}
