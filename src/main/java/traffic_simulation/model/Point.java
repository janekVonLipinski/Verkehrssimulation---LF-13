package traffic_simulation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
@ToString
public class Point {

    private final double x;

    private final double y;
    

    public Point add(Point p) {
        return new Point(x + p.x, y + p.y);
    }


    public Point subtract(Point p) {
        return new Point(x - p.x, y - p.y);
    }

    public Point normalize() {
        double length = Math.sqrt(x * x + y * y);

        return new Point(x / length, y / length);
    }

    public Point getInverted() {
        return new Point(-x, -y);
    }

    public double getDistanceToPoint(Point p){
        return Math.sqrt((y - p.getY()) * (y - p.getY()) + (x - p.getX()) * (x - p.getX()));
    }
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public Point multiply(double scalar) {
        return new Point(
                x * scalar,
                y * scalar
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(x, point.x) == 0 && Double.compare(y, point.y) == 0;
    }
}
