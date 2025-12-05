package traffic_simulation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
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

        return new Point(
                x / length, y / length
        );
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }
}
