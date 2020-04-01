package com.mjoker73.demo.annotation;

import com.mjoker73.demo.utils.ClassUtil;
import com.mjoker73.demo.utils.ListUtil;
import com.mjoker73.demo.validators.IParameterValidator;
import com.mjoker73.demo.validators.NoValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;


/**
 * parse fields and find related annotation
 */
public class Parameterized {
    private Object object;
    private Field field;
    private Method method;
    private WrappedParameter wrappedParameter;

    public Parameterized(WrappedParameter wrappedParameter, Field field, Method method, Object object) {
        this.field = field;
        this.method = method;
        this.wrappedParameter = wrappedParameter;
        this.object = object;
    }

    public String getName() {
        if (field != null) {
            return field.getName();
        }
        return method.getName();
    }

    public Field getField() {
        return field;
    }

    public Method getMethod() {
        return method;
    }

    public WrappedParameter getWrappedParameter() {
        return wrappedParameter;
    }

    public static List<Parameterized> collectList(Object arg) {
        List<Parameterized> result = ListUtil.newArrayList();
        Class<?> rootClass = arg.getClass();

        Set<Class<?>> types = ClassUtil.describeClassTree(rootClass);

        for (Class<?> cls : types) {
            for (Field f : cls.getDeclaredFields()) {
                Annotation pa = f.getAnnotation(ParameterA.class);
                Annotation pb = f.getAnnotation(ParameterB.class);
                if (pa != null) {
                    result.add(new Parameterized(new WrappedParameter((ParameterA) pa), f, null, arg));
                } else if (pb != null) {
                    result.add(new Parameterized(new WrappedParameter((ParameterB) pb), f, null, arg));
                }
            }

            for (Method m : cls.getDeclaredMethods()) {
                Annotation pa = m.getAnnotation(ParameterA.class);
                Annotation pb = m.getAnnotation(ParameterB.class);
                if (pa != null) {
                    result.add(new Parameterized(new WrappedParameter((ParameterA) pa), null, m, arg));
                } else if (pb != null) {
                    result.add(new Parameterized(new WrappedParameter((ParameterB) pb), null, m, arg));
                }
            }
        }
        return result;
    }

    public void validateParameter(String name, String value) throws Exception {
        final Class<? extends IParameterValidator> validators[] = wrappedParameter.validateWith();
        if (validators != null && validators.length > 0) {
            for(final Class<? extends IParameterValidator> validator: validators) {
                try {
                    if (validator != NoValidator.class) {
                        System.out.println("Validating parameter:" + name + " value:" + value + " validator:" + validator);
                        validator.newInstance().validate(name, value);
                    }
                } catch (Exception ex) {
                    throw new Exception(ex);
                }
            }
        }
    }

    public Object getObject() {
        return object;
    }
}
