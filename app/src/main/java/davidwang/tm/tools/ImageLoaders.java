package davidwang.tm.tools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaders {

	public static DisplayImageOptions optionstop = null;
	public static DisplayImageOptions options = null;
	public static DisplayImageOptions sendoptions = null;
	public static DisplayImageOptions dailyfines = null;

	public static void setheadimg(int dis, String url, ImageView imageView) {
		if (options == null) {
			options = new DisplayImageOptions.Builder().showImageOnLoading(0)
					.showImageForEmptyUri(0).showImageOnFail(0)
					.cacheInMemory(true).cacheOnDisk(true)
					.considerExifParams(true)
					.displayer(new RoundedBitmapDisplayer(dis)).build();
		}

		ImageLoader.getInstance().displayImage(url, imageView, options);
	}

	public static void settopimg(int dis, String url, ImageView imageView) {

		if (optionstop == null) {
			optionstop = new DisplayImageOptions.Builder()
					.showImageOnLoading(0).showImageForEmptyUri(0)
					.showImageOnFail(0)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.considerExifParams(true)
					.displayer(new RoundedBitmapDisplayer(dis)).build();
		}

		ImageLoader.getInstance().displayImage(url, imageView, optionstop);

	}

	public static void setsendimg(String url, ImageView imageView) {
		if (sendoptions == null) {
			sendoptions = new DisplayImageOptions.Builder()
					.showImageOnLoading(0)
					.showImageForEmptyUri(0)
					.showImageOnFail(0)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.considerExifParams(false)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
					.build();
		}

		ImageLoader.getInstance().displayImage(url, imageView, sendoptions);
	}

	public static void setdailyfineimg(String url, ImageView imageView,
			ImageLoadingListener listener) {
		if (dailyfines == null) {
			dailyfines = new DisplayImageOptions.Builder()
					.showImageOnLoading(0)
					.showImageForEmptyUri(0)
					.showImageOnFail(0)
					.cacheInMemory(true)
//					.cacheOnDisk(true)
					.considerExifParams(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
					.build();
		}

		ImageLoader.getInstance().displayImage(url, imageView, dailyfines,
				listener);
	}


}
