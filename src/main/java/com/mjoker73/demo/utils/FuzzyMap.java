package com.mjoker73.demo.utils;

import java.util.Map;

public class FuzzyMap {
    public interface IKey {
        String getName();
    }

    public static class StringKey implements IKey {

        private String name;

        public StringKey(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            StringKey other = (StringKey) obj;
            if (name == null) {
                return other.name == null;
            } else {
                return name.equals(other.name);
            }
        }

    }

    public static <V> V findInMap(Map<? extends IKey, V> map, IKey name,
                                  boolean caseSensitive, boolean allowAbbreviations) {
        if (allowAbbreviations) {
            return findAbbreviatedValue(map, name, caseSensitive);
        } else {
            if (caseSensitive) {
                return map.get(name);
            } else {
                for (IKey c : map.keySet()) {
                    if (c.getName().equalsIgnoreCase(name.getName())) {
                        return map.get(c);
                    }
                }
            }
        }
        return null;
    }

    private static <V> V findAbbreviatedValue(Map<? extends IKey, V> map, IKey name,
                                              boolean caseSensitive) {
        String string = name.getName();
        Map<String, V> results = MapUtil.newHashMap();
        for (IKey c : map.keySet()) {
            String n = c.getName();
            boolean match = (caseSensitive && n.startsWith(string))
                    || ((! caseSensitive) && n.toLowerCase().startsWith(string.toLowerCase()));
            if (match) {
                results.put(n, map.get(c));
            }
        }
        V result;
        if (results.size() > 1) {
            throw new AssertionError("Ambiguous option: " + name + " matches " + results.keySet());
        } else if (results.size() == 1) {
            result = results.values().iterator().next();
        } else {
            result = null;
        }
        return result;
    }
}
