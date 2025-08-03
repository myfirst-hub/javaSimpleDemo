package study; // 使用命令行javac, java执行程序传参 arg1 arg2 (需要注释package) // 在文件目录通过终端执行命令

import java.util.Arrays;

public class ArrayOperate {
    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(args));
        int[][] ns = {
                { 1, 2, 3, 4 },
                { 5, 6 },
                { 7, 8, 9 }
        };
        System.out.println(Arrays.deepToString(ns));

        // 只要可以引入Person类，就可以访问Person类中的public作用域的属性和方法
        Person person = new Person();
        person.printD();
        person.printP();
    }
}
