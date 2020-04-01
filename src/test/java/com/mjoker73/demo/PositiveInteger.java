package com.mjoker73.demo;

import com.mjoker73.demo.validators.IParameterValidator;

public class PositiveInteger implements IParameterValidator {

    @Override
    public void validate(String name, String value) {
        if (!value.equals("Test1")) {
            throw new AssertionError("Parameter " + name + " should be TEST1 (found " + value +")");
        }
    }
}
