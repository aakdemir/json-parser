package com.parser.parser.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String date;
    private String hour;
    private Float netVolume;
    private Float Price;
}
