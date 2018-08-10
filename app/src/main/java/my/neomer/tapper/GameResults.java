package my.neomer.tapper;

import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;

public class GameResults implements Parcelable {
    private long mTotalTime;

    public long getTotalTime() {
        return mTotalTime;
    }

    public void setTotalTime(long totalTime) {
        mTotalTime = totalTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mTotalTime);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<GameResults> CREATOR = new Parcelable.Creator<GameResults>() {
        public GameResults createFromParcel(Parcel in) {
            return new GameResults(in);
        }

        public GameResults[] newArray(int size) {
            return new GameResults[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    GameResults(Parcel in) {
        mTotalTime = in.readInt();
    }

    GameResults() {
        mTotalTime = 0;
    }
}
