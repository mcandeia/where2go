
package activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;

import br.com.les.where2go.R;
import entity.event.Event;
import persistence.ParseUtil;
import utils.FieldValidation;

/**
 * The Class CreateEventAditInfoActivity.
 */
public class CreateEventAditInfoActivity extends Activity {

    /**
     * The et_event_notes.
     */
    private EditText etEventNotes;

    /**
     * The et_event_outfit.
     */
    private EditText etEventOutfit;

    /**
     * The et_event_capacity.
     */
    private EditText etEventCapacity;

    /**
     * The bt_create_event_in_aditional_information.
     */
    private Button btCreateEventInAditionalInformation;

    /**
     * The Image View Event.
     */
    private ImageView ivEvent;

    /**
     * The Select Photo.
     */
    private final int SELECT_PHOTO = 1;

    private File eventImage;

    /**
     * The validation.
     */
    private final FieldValidation validation = new FieldValidation(this);

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_adt_info);
        setStatusBarColor(findViewById(R.id.statusBarBackground),
                getResources().getColor(R.color.status_bar));

        etEventNotes = (EditText) findViewById(R.id.et_event_notes);
        etEventOutfit = (EditText) findViewById(R.id.et_event_outfit);
        etEventCapacity = (EditText) findViewById(R.id.et_event_capacity);
        ivEvent = (ImageView) findViewById(R.id.image_view_event);
        btCreateEventInAditionalInformation = (Button) findViewById(R.id.bt_create_event_in_aditional_information);

        ivEvent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });

        btCreateEventInAditionalInformation
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        final Event event = CreateEventActivity.getEvent();
                        if (validation.hasText(etEventNotes)) {
                            event.setNote(etEventNotes.getText().toString());
                        }
                        if (validation.hasText(etEventOutfit)) {
                            event.setOutfit(etEventOutfit.getText()
                                    .toString());
                        }
                        if (validation.hasText(etEventCapacity)) {
                            event.setCapacity(Integer
                                    .parseInt(etEventCapacity.getText()
                                            .toString()));
                        }

                        ParseUtil.saveEvent(event);
                        EventsListFragment.getAdapter().notifyDataSetChanged();
                        final Intent intent = new Intent(
                                getApplicationContext(), MainScreen.class);
                        intent.putExtra("eventslist", 2);
                        startActivity(intent);
                    }
                });
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ivEvent.setImageBitmap(selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        String eventId = CreateEventActivity.getEvent().getName();
                        ParseFile pf = new ParseFile(eventId + ".png", byteArray);
                        pf.save();
                        CreateEventActivity.getEvent().setPhoto(pf);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    /**
     * Sets the status bar color.
     *
     * @param statusBar the status bar
     * @param color     the color
     */
    public final void setStatusBarColor(final View statusBar, final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // status bar height
            final int actionBarHeight = getActionBarHeight();
            final int statusBarHeight = getStatusBarHeight();
            // action bar height
            statusBar.getLayoutParams().height = actionBarHeight
                    + statusBarHeight;
            statusBar.setBackgroundColor(color);
        }
    }

    /**
     * Gets the action bar height.
     *
     * @return the action bar height
     */
    public final int getActionBarHeight() {
        int actionBarHeight = 0;
        final TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * Gets the status bar height.
     *
     * @return the status bar height
     */
    public final int getStatusBarHeight() {
        int result = 0;
        final int resourceId = getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
