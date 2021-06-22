package com.nnk.springboot.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AddBidListForm {
    private String account;
    private String type;
    private String bidQuantity;
}
