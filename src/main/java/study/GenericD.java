package study;

import java.util.Arrays;

// 泛型就是编写模板代码来适应任意类型
public class GenericD {
    public static void main(String[] args) {
        Person1[] ps = new Person1[] {
                new Person1("Bob", 61),
                new Person1("Alice", 88),
                new Person1("Lily", 75),
        };
        Arrays.sort(ps);
        System.out.println(Arrays.toString(ps));
    }
}

//  Comparable<Person1> 接口泛型
class Person1 implements Comparable<Person1> {
    String name;
    int score;
    Person1(String name, int score) {
        this.name = name;
        this.score = score;
    }
    public int compareTo(Person1 other) {
        return this.name.compareTo(other.name);
    }
    public String toString() {
        return this.name + "," + this.score;
    }
}
