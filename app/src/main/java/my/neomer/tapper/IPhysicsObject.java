package my.neomer.tapper;

public interface IPhysicsObject {

    void SetMaterial(Material material);

    void ApplyForce(Vector force);
    void ApplyImpulse(Vector impulse);

    void UpdatePhysics(double timeSpan);

    boolean IsStatic();

    void Kill();
    boolean IsDead();

    ICollisionRegion GetCollisionRegion();

}
