package com.mjoker73.demo.utils;

import java.util.Collections;
import java.util.Set;

public class ClassUtil {
    public static Set<Class<?>> describeClassTree(Class<?> inputClass) {
        if(inputClass == null) {
            return Collections.emptySet();
        }

        // create result collector
        Set<Class<?>> classes = SetUtil.newLinkedHashSet();

        // describe tree
        describeClassTree(inputClass, classes);

        return classes;
    }

    private static void describeClassTree(Class<?> inputClass, Set<Class<?>> setOfClasses) {
        // can't map null class
        if(inputClass == null) {
            return;
        }

        // don't further analyze a class that has been analyzed already
        if(Object.class.equals(inputClass) || setOfClasses.contains(inputClass)) {
            return;
        }

        // add to analysis set
        setOfClasses.add(inputClass);

        // perform super class analysis
        describeClassTree(inputClass.getSuperclass(), setOfClasses);

        // perform analysis on interfaces
        for(Class<?> hasInterface : inputClass.getInterfaces()) {
            describeClassTree(hasInterface, setOfClasses);
        }
    }
}
