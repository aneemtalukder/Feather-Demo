package com.android.feather.demo;

import com.aviary.android.feather.FeatherActivity;
import com.aviary.android.feather.library.utils.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private static final String API_KEY = "xxxxx";
	private static final int ACTION_REQUEST_FEATHER = 100;
	private static final String sourcePath = "/sdcard/DCIM/Camera/test_image.jpg";
	private static final String destPath = "/sdcard/DCIM/Camera/test_image.jpg"; 
	boolean init = false;

	ImageView view = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		Button launch = (Button) (findViewById(R.id.launch));

		view = (ImageView) findViewById(R.id.imageView1);

		Bitmap bitmap = BitmapFactory.decodeFile(sourcePath);

		view.setImageBitmap(bitmap);

		launch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				// Create the intent needed to start feather
				Intent newIntent = new Intent( getApplicationContext(), FeatherActivity.class );
				// set the source image uri
				Uri uri = Uri.parse(sourcePath);

				newIntent.setData( uri );
				// pass the required api key ( http://developers.aviary.com/ )
				newIntent.putExtra( "API_KEY", "xxx" );
				// pass the uri of the destination image file (optional)
				// This will be the same uri you will receive in the onActivityResult

				Uri path = Uri.parse("file://" + destPath);

				newIntent.putExtra( "output", Uri.parse(  path.getPath() ) );
				// format of the destination image (optional)
				newIntent.putExtra( "output-format", Bitmap.CompressFormat.JPEG.name() );
				// output format quality (optional)
				newIntent.putExtra( "output-quality", 85 );
				// you can force feather to display only a certain tools
				// newIntent.putExtra( "tools-list", new String[]{"ADJUST", "BRIGHTNESS" } );

				// enable fast rendering preview
				newIntent.putExtra( "effect-enable-fast-preview", true );

				// limit the image size
				// You can pass the current display size as max image size because after
				// the execution of Aviary you can save the HI-RES image so you don't need a big
				// image for the preview
				// newIntent.putExtra( "max-image-size", 800 );

				// HI-RES
				// You need to generate a new session id key to pass to Aviary feather
				// this is the key used to operate with the hi-res image ( and must be unique for every new instance of Feather )
				// The session-id key must be 64 char length
				String mSessionId = StringUtils.getSha256( System.currentTimeMillis() + API_KEY );
				newIntent.putExtra( "output-hires-session-id", mSessionId );    

				// you want to hide the exit alert dialog shown when back is pressed
				// without saving image first
				// newIntent.putExtra( "hide-exit-unsave-confirmation", true );

				// ..and start feather
				startActivityForResult( newIntent, ACTION_REQUEST_FEATHER );

			}
		});

	}

	@Override
	public void onActivityResult( int requestCode, int resultCode, Intent data ) {
		if( resultCode == RESULT_OK ) {
			switch( requestCode ) {
			case ACTION_REQUEST_FEATHER:
				Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/DCIM/Camera/test_image.jpg");
				view.setImageBitmap(bitmap);
				break;
			}
		}
	}



}