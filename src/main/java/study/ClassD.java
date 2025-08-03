package study;

import com.example.simoledemo.HelloController;

public class ClassD {
    public static void main(String[] args) {
        HelloController helloController = new HelloController();
        Person ming = new Person();
//        ming.age = 6; // 编译错误： age 在 study.Person 中是 private 访问控制

        Student stu = new Student("tiantian", 90);
        System.out.println(stu.name + "...." + stu.score);
    }
}

// 包作用域
class Person {
    private int age;
    protected String name;
    public Person() {
    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // 包作用域 该方法在ArrayOperate类中有引用
    void printD() {
        System.out.println("print...." + this.age);
    }
    // public作用域
    public void printP() {
        System.out.println("print...." + this.age);
    }

}

class Student extends Person {
    protected int score;

    public Student(String name, int score) {
//        super(); // 自动调用父类的构造方法 (此方法所有构造函数都会默认执行)
        this.score = score;
        this.name = name;
    }
}
