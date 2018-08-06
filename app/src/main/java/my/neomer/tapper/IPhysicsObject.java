package my.neomer.tapper;

public interface IPhysicsObject {

    void ApplyForce(IForce force);
    void ApplyImpulse(IForce impulse);

    void UpdatePhysics(long timespan);

}
