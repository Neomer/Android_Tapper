package my.neomer.tapper;

import android.graphics.Rect;

public class CollisionSolver {

    public static boolean CheckRectToCircleIntersection(RectangleCollisionElement rectangleCollisionElement, CircleCollisionElement circleCollisionElement) {
        Coordinate coordsCircle = circleCollisionElement.getPhysicsObject().GetCoordinates().Clone(),
                coordsRect = rectangleCollisionElement.getPhysicsObject().GetCoordinates().Clone();

        coordsCircle.Add(circleCollisionElement.getCenter());
        Rect rect = RectangleCollisionElement.getMappedRect(rectangleCollisionElement, coordsRect);

        double len = GeometryHelper.SquareLength(rect.left, rect.top, coordsCircle.getX(), coordsCircle.getY());
        if (len < circleCollisionElement.getSquareRaduis()) {
            return true;
        }
        len = GeometryHelper.SquareLength(rect.left, rect.bottom, coordsCircle.getX(), coordsCircle.getY());
        if (len < circleCollisionElement.getSquareRaduis()) {
            return true;
        }
        len = GeometryHelper.SquareLength(rect.right, rect.top, coordsCircle.getX(), coordsCircle.getY());
        if (len < circleCollisionElement.getSquareRaduis()) {
            return true;
        }
        len = GeometryHelper.SquareLength(rect.right, rect.bottom, coordsCircle.getX(), coordsCircle.getY());
        if (len < circleCollisionElement.getSquareRaduis()) {
            return true;
        }

        return false;
    }

    public static boolean CheckRectToRectIntersection(RectangleCollisionElement rectangleCollisionElement1, RectangleCollisionElement rectangleCollisionElement2) {
        Coordinate coords1 = rectangleCollisionElement1.getPhysicsObject().GetCoordinates().Clone(),
                coords2 = rectangleCollisionElement2.getPhysicsObject().GetCoordinates().Clone();

        Rect rect1 = RectangleCollisionElement.getMappedRect(rectangleCollisionElement1, coords1),
                rect2 = RectangleCollisionElement.getMappedRect(rectangleCollisionElement2, coords2);

        return rect1.intersect(rect2);
    }

    public static boolean CheckCircleToCircleIntersection(CircleCollisionElement circleCollisionElement1, CircleCollisionElement circleCollisionElement2) {
        Coordinate c1 = circleCollisionElement1.getPhysicsObject().GetCoordinates().Clone();
        c1.Add(circleCollisionElement1.getCenter());

        Coordinate c2 = circleCollisionElement2.getPhysicsObject().GetCoordinates().Clone();
        c2.Add(circleCollisionElement2.getCenter());

        return GeometryHelper.SquareLength(c1.getX(), c1.getY(), c2.getX(), c2.getY()) <= circleCollisionElement2.getSquareRaduis();
    }


}
