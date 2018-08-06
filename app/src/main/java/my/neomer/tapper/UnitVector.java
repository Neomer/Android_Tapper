package my.neomer.tapper;

public class UnitVector extends Vector {

    UnitVector() {

    }

    UnitVector(double x, double y) {
        super(x, y);

        setX(x / getLength());
        setY(y / getLength());

        updateLength();
    }

}
