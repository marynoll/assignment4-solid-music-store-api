package utils;

import java.lang.reflect.*;

public class ReflectionUtils {

    // Inspect class: name, superclass, fields, methods
    public static void inspectClass(Object obj) {
        Class<?> clazz = obj.getClass();
        System.out.println("Class: " + clazz.getSimpleName());
        if (clazz.getSuperclass() != null) System.out.println("Super: " + clazz.getSuperclass().getSimpleName());

        for (Field f : clazz.getDeclaredFields()) {
            System.out.println("Field: " + f.getType().getSimpleName() + " " + f.getName());
        }

        for (Method m : clazz.getDeclaredMethods()) {
            System.out.print("Method: " + m.getReturnType().getSimpleName() + " " + m.getName() + "(");
            Class<?>[] types = m.getParameterTypes();
            for (int i = 0; i < types.length; i++) {
                System.out.print(types[i].getSimpleName());
                if (i < types.length - 1) System.out.print(", ");
            }
            System.out.println(")");
        }
    }

    // Get or set field dynamically
    public static Object getField(Object obj, String name) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setField(Object obj, String name, Object value) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (Exception ignored) {}
    }
}