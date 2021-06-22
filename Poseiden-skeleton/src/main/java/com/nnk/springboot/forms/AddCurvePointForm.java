package com.nnk.springboot.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddCurvePointForm {
    private String curveId;
    private String term;
    private String value;
}
