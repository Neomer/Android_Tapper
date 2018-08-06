package my.neomer.tapper;

class BaseForce implements IForce
{

    private Vector mVector;
    private float mAmplitude;

    BaseForce()
    {
        mVector = new Vector();
        mAmplitude = 0;
    }

    BaseForce(Vector vector, float amplitude)
    {
        mVector = vector;
        mAmplitude = amplitude;
    }


    @Override
    public Vector getVector()
    {
        return mVector;
    }

    @Override
    public float getAmplitude()
    {
        return mAmplitude;
    }

    @Override
    public IForce add(IForce force)
    {

        return null;
    }
}
