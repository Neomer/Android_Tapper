package my.neomer.tapper;

public interface IPhysicsObject {

    void ApplyForce(IForce force);

    void UpdatePhysics(long timespan);

}
