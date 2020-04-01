package com.mjoker73.demo.annotation;

import com.mjoker73.demo.validators.IParameterValidator;

public class WrappedParameter {
    private ParameterA parameterA;
    private ParameterB parameterB;

    public WrappedParameter(ParameterA parameterA) {
        this.parameterA = parameterA;
    }

    public WrappedParameter(ParameterB parameterB) {
        this.parameterB = parameterB;
    }

    public ParameterA getParameterA() {
        return parameterA;
    }

    public ParameterB getParameterB() {
        return parameterB;
    }

    public Class<? extends IParameterValidator>[] validateWith() {
        return parameterA != null ? parameterA.validateWith() : parameterB.validateWith();
    }
}
