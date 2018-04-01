package Lab1;

import java.util.Scanner;
import static java.lang.Math.sqrt;

public class Lab1 {
    public static void main() {
        Point3d[] points = new Point3d[]{new Point3d(), new Point3d(), new Point3d()};
        Scanner in = new Scanner(System.in);
        int i = 0;
        do {
            System.out.println("Введите координаты " + (i + 1) + "точки: ");
            points[i].setX(Double.valueOf(in.next()));
            points[i].setY(Double.valueOf(in.next()));
            points[i].setZ(Double.valueOf(in.next()));
            i++;
        } while (i < 3);
        boolean err = false;
        if (points[0].isEqual(points[1]) || points[0].isEqual(points[2]) || points[2].isEqual(points[1])) {
            err = true;
            System.out.println("Ошибка! Точки не должны быть равны.");
        } else {
            System.out.println("Площадь треугольника равна " + conputeArea(points));
        }
    }

    public static double conputeArea(Point3d points[]) {
        double a = points[0].distanceTo(points[1]);
        double b = points[0].distanceTo(points[2]);
        double c = points[2].distanceTo(points[1]);
        double p = (a + b + c) / 2;
        return sqrt(p * (p - a) * (p - b) * (p - c));
    }
}
