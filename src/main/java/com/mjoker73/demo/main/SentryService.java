package com.mjoker73.demo.main;

import com.mjoker73.demo.annotation.Parameterized;
import com.mjoker73.demo.utils.FuzzyMap;
import com.mjoker73.demo.utils.ListUtil;
import com.mjoker73.demo.utils.MapUtil;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;

public class SentryService {

    private List<Object> objects = ListUtil.newArrayList();

    private Map<FuzzyMap.IKey, Parameterized> ParameterizedMap;

    public void init() {
        if (ParameterizedMap == null) {
            ParameterizedMap = MapUtil.newHashMap();
            for (Object object : objects) {
                List<Parameterized> parameterizeds = Parameterized.collectList(object);
                for (Parameterized parameterized : parameterizeds) {
                    ParameterizedMap.put(new FuzzyMap.StringKey(parameterized.getName()), parameterized);
                }
            }
        }
    }

    public String getPADescription(String name) {
        Parameterized p = ParameterizedMap.get(new FuzzyMap.StringKey(name));
        if (p.getWrappedParameter().getParameterA() != null) {
            return p.getWrappedParameter().getParameterA().description();
        }
        return null;
    }

    public void validateStringField(String name) throws Exception {
        Parameterized p = ParameterizedMap.get(new FuzzyMap.StringKey(name));

        String n = p.getName();
        p.getField().setAccessible(true);
        Object v = p.getField().get(p.getObject());
        p.validateParameter(n, (String) v);

    }





    public final void addObject(Object object) {
        if (object instanceof Iterable) {
            for (Object o : (Iterable<?>) object) {
                objects.add(o);
            }
        } else if (object.getClass().isArray()) {
            for (Object o : (Object[]) object) {
                objects.add(o);
            }
        } else {
            objects.add(object);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private SentryService sentryService = new SentryService();
        private String[] args = null;

        public Builder() {
        }

        public Builder addObject(Object o) {
            sentryService.addObject(o);
            return this;
        }

        public SentryService build() {
            return sentryService;
        }

    }
}
