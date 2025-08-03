package study;

public class BasicDataType {
    public static void main(String[] args) {
        int n = 3300;
        System.out.println(n++); // 3300 n++表示先引用n再加1
        System.out.println(n); // 3301
        int m = 3300;
        System.out.println(++m); // 3301 ++m表示先加1再引用m

        double x = 0.1 / 10;
        double y = (1 - 0.9) / 10;
        // 观察x和y是否相等:
        System.out.println(x);
        System.out.println(y);

        // 比较x和y是否相等，先计算其差的绝对值:
        double r = Math.abs(x - y);
        // 再判断绝对值是否足够小:
        if (r < 0.00001) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");// 不相等
        }

        // 多行字符串
        String s = """
                   SELECT * FROM
                     users
                   WHERE id > 100
                   ORDER BY name DESC
                   """;
        System.out.println(s);

        // 字符串不可变特性
        String ss = "hello";
        String t = ss;
        ss = "world";
        System.out.println(t); // t是"hello"还是"world"? // 是 hello

        // 通过char强制类型转换为Unicode编码，并且拼接字符串
        int a=72;
        int b=105;
        int c=65281;
        String str=""+(char)a+(char)b+(char)c;
        System.out.println(str);
    }
}
