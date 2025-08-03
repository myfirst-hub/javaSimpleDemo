package study;

import java.util.*;

public class IteratorD {
    public static void main(String[] args) {

        // Make a collection
        ArrayList<String> cars = new ArrayList<String>();
        cars.add("Volvo");
        cars.add("BMW");
        cars.add("Ford");
        cars.add("Mazda");

        // Get the iterator
        Iterator<String> it = cars.iterator();

        // Print the first item
    //    System.out.println(it.next());
    //    System.out.println(it.next());
    //    System.out.println(it.next());
    //    System.out.println(it.next());

        while(it.hasNext()) {
            String str = it.next();
            System.out.println("--1111------，" + str);
            if(str.equals("BMW")) {
                it.remove();
            }
            System.out.println(cars);
        }

        LinkedList<String> linkCars = new LinkedList<String>();
            linkCars.add("Volvo1");
            linkCars.add("BMW1");
            linkCars.add("Ford1");
            linkCars.add("Mazda1");
        System.out.println(linkCars);

        HashSet<String> hashCars = new HashSet<String>();
            hashCars.add("Volvo");
            hashCars.add("BMW");
            hashCars.add("Ford");
            hashCars.add("BMW");
            hashCars.add("Mazda");
        System.out.println(hashCars);

        // 创建一个名为 people 的 HashMap 对象
        HashMap<String, Integer> people = new HashMap<String, Integer>();

        // 添加键和值 (Name, Age)
        people.put("John", 32);
        people.put("Steve", 30);
        people.put("Angie", 33);

        System.out.println(people);

        for (String i : people.keySet()) {
            System.out.println("key: " + i + " value: " + people.get(i));
        }

        Scanner scanner = new Scanner(System.in); // 创建Scanner对象
        System.out.print("Input your name: "); // 打印提示
        String name = scanner.nextLine(); // 读取一行输入并获取字符串
        System.out.print("Input your age: "); // 打印提示
        int age = scanner.nextInt(); // 读取一行输入并获取整数
        System.out.printf("Hi, %s, you are %d\n", name, age); // 格式化输出
    }
}
