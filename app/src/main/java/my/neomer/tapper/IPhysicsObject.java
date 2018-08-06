package my.neomer.tapper;

public interface IPhysicsObject {

    void ApplyForce(Vector force);
    void ApplyImpulse(Vector impulse);

    void UpdatePhysics(double timespan);

}
