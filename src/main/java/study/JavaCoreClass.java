package study;

public class JavaCoreClass {
    public static void main(String[] args) {
        var sb = new StringBuilder(1024);
        // 链式操作
        sb.append("Mr ")
                .append("Bob")
                .append("!")
                .insert(0, "Hello, ");
        System.out.println(sb.toString());

        // 类装箱拆箱
        int num = 4;
        Integer n1 = new Integer(num);

        // 枚举
        Weekday day = Weekday.SUN;
        if (day.dayValue == 6 || day.dayValue == 0) {
            System.out.println("Today is " + day + ". Work at home!");
        } else {
            System.out.println("Today is " + day + ". Work at office!");
        }

        switch(day) {
            case MON:
            case TUE:
            case WED:
            case THU:
            case FRI:
                System.out.println("Today is " + day + ". Work at office!");
                break;
            case SAT:
            case SUN:
                System.out.println("Today is " + day + ". Work at home!");
                break;
            default:
                throw new RuntimeException("cannot process " + day);
        }


        // record类
        var z = Point.of();
        var p = Point.of(123, 456);
        System.out.println(p);
        System.out.println(Point.of());
    }
}

enum Weekday {
    MON(1, "星期一"), TUE(2, "星期二"), WED(3, "星期三"), THU(4, "星期四"), FRI(5, "星期五"), SAT(6, "星期六"), SUN(0, "星期日");

    public final int dayValue;
    private final String chinese;

    private Weekday(int dayValue, String chinese) {
        this.dayValue = dayValue;
        this.chinese = chinese;
    }

    @Override
    public String toString() {
        return this.chinese;
    }
}

record Point(int x, int y) {
    public static Point of() {
        return new Point(0, 0);
    }
    public static Point of(int x, int y) {
        return new Point(x, y);
    }
}
