package study;

public class AnnotationD {
    @ReportA(min = 1, max = 20)
    public String name;

    @ReportA(max = 10)
    public String city;

    public static void main(String[] args) {

    }
}
