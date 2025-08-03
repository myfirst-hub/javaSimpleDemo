package study;

import java.lang.*;
import java.time.Month;

public class ReflectionD {
    public static void main(String[] args) {
        printClassInfo("".getClass());
        System.out.println('\n');
        printClassInfo(Runnable.class);
        System.out.println('\n');
        printClassInfo(Month.class);
        System.out.println('\n');
        printClassInfo(String[].class);
        System.out.println('\n');
        printClassInfo(int.class);
    }
    @SuppressWarnings({})
    static void printClassInfo(Class cls) {
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
}
