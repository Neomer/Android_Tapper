package my.neomer.tapper;

public class PhysicsWorld implements IPhysicsObject
{
    @Override
    public void SetMaterial(Material material) {

    }

    @Override
    public void ApplyForce(Vector force) {

    }

    @Override
    public void ApplyImpulse(Vector impulse) {

    }

    @Override
    public void Update(double timeSpan)
    {

    }

    @Override
    public boolean IsStatic() {
        return true;
    }

    @Override
    public ICollisionRegion GetCollisionRegion() {
        return null;
    }

    @Override
    public void Kill() {

    }

    @Override
    public boolean IsDead() {
        return false;
    }

    @Override
    public Coordinate GetCoordinates() {
        return null;
    }
}
