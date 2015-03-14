
package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import br.com.les.where2go.R;

import com.parse.GetDataCallback;
import com.parse.ParseException;

import entity.event.Event;
import persistence.ParseUtil;
import utils.FieldValidation;

/**
 * The Class EditEventAditInfoActivity.
 */
public class EditEventAditInfoActivity extends Activity {
    /** The et_event_notes. */
    private EditText etEventNotes;

    /** The et_event_outfit. */
    private EditText etEventOutfit;

    /** The et_event_capacity. */
    private EditText etEventCapacity;

    /** The bt_create_event_in_aditional_information. */
    private Button btCreateEventInAditionalInformation;

    private Button btCancel;

    private ImageView ivEventPhoto;

    private Event event;

    /** The validation. */
    private final FieldValidation validation = new FieldValidation(this);

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_adt_info);
        setStatusBarColor(findViewById(R.id.statusBarBackground),
                getResources().getColor(R.color.status_bar));

        event = EditEventActivity.getEvent();

        etEventNotes = (EditText) findViewById(R.id.et_event_notes);
        etEventOutfit = (EditText) findViewById(R.id.et_event_outfit);
        etEventCapacity = (EditText) findViewById(R.id.et_event_capacity);
        ivEventPhoto = (ImageView) findViewById(R.id.iv_event_photo);
        btCreateEventInAditionalInformation = (Button) findViewById(R.id.bt_create_event_in_aditional_information);
        btCancel = (Button) findViewById(R.id.bt_cancel);

        etEventNotes.setText(event.getNote());
        etEventOutfit.setText(event.getOutfit());
        etEventCapacity.setText(event.getCapacity() + "");

        if (event.getImageEvent() == null && event.getPhoto() != null) {
            event.getPhoto().getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    final Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,
                            bytes.length);
                    ivEventPhoto.setImageBitmap(bmp);
                    event.setImageEvent(bmp);
                }
            });
        } else if (event.getImageEvent() != null) {
            final Bitmap bitmap = event.getImageEvent();
            ivEventPhoto.setImageBitmap(bitmap);
        }

        ivEventPhoto.setImageBitmap(event.getImageEvent());

        btCreateEventInAditionalInformation
                .setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(final View v) {

                        final Event event = CreateEventActivity.getEvent();
                        if (validation.hasText(etEventNotes)) {
                            event.setNote(etEventNotes.getText().toString());
                        }
                        if (validation.hasText(etEventOutfit)) {
                            event.setOutfit(etEventOutfit.getText().toString());
                        }
                        if (validation.hasText(etEventCapacity)) {
                            event.setCapacity(Integer.parseInt(etEventCapacity
                                    .getText().toString()));
                        }
                        ParseUtil.saveEvent(event);
                        EventsListFragment.getAdapter().notifyDataSetChanged();
                        final Intent intent = new Intent(
                                getApplicationContext(), MainScreen.class);
                        intent.putExtra("eventslist", 2);
                        startActivity(intent);

                    }
                });

        btCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    /**
     * Sets the status bar color.
     * 
     * @param statusBar the status bar
     * @param color the color
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
