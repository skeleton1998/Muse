package muse.muse_performance;

import android.media.SoundPool;
import android.util.Log;
import android.content.Context;

public class BackSE {
	int maxStreams;
	int streamType;
	int srcQuality;

	Context context;

	private static SoundPool soundPool;
	private static int[] soundID = new int[5];

	public BackSE(int max,int type,int src,Context ct){
		maxStreams=max;
		streamType=type;
		srcQuality=src;
		context=ct;
	}

	public void soundLoad(){
		soundPool = new SoundPool(maxStreams,streamType,srcQuality);
		Log.d("","make soundpool");
		soundID[0] = soundPool.load(context,R.raw.flog ,1);
		Log.d("","load1");
		soundID[1] = soundPool.load(context,R.raw.hiyoko,1);
		Log.d("","load2");
		soundID[2] = soundPool.load(context,R.raw.semi,1);
		Log.d("","load3");
		soundID[3] = soundPool.load(context,R.raw.hand,1);
		Log.d("","load4");
		soundID[4] = soundPool.load(context,R.raw.flog2,1);
		Log.d("","load5");
	}

	public void soundPlay(int i){
		soundPool.play(soundID[i],0.2f,0.2f,0,0,1);
	}
}
