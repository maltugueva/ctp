package Lab1;

import static java.lang.Math.sqrt;
import static java.lang.Math.pow;
//  трехмерный класс точки
public class Point3d {
    private double xCoord;
    private double yCoord;
    private double zCoord;
//  конструктор инициализации
    public Point3d(double x, double y, double z){xCoord=x; yCoord=y; zCoord=z; }
//  конструктор по умолчанию
    public Point3d(){xCoord=0; yCoord=0; zCoord=0;}
//  возвращают координаты
    public double getX(){return xCoord;}
    public double getY(){return yCoord;}
    public double getZ(){return zCoord;}
//  ввод координаты
    public void setX(double val){xCoord=val;}
    public void setY(double val){yCoord=val;}
    public void setZ(double val){zCoord=val;}
//проверка равенства двух точек
    public boolean isEqual(Point3d point){
        if ((point.getX()==xCoord)&&(point.getY()==yCoord)&&(point.getZ()==zCoord))
            return true;
        else return false;
    }
    public double distanceTo(Point3d point){
        return sqrt(pow(xCoord-point.getX(),2)+pow(yCoord-point.getY(),2)+pow(zCoord-point.getZ(),2));
    }
}

