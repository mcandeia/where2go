package activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.InviteAdapter;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.les.where2go.R;
import entity.event.Event;
import entity.event.Invitation;
import entity.user.User;

/**
 * The Class AdsFragment.
 */
public class MyInvitesFragment extends Fragment {

    /** The listview. */
    private static ListView listview;

    /** The adapter. */
    public static InviteAdapter adapter;

    /** The context. */
    public static Context context;

    /** The action bar. */
    private ActionBar actionBar;

    /** The root view. */
    private View rootView;

    private List<Invitation> mListInvites;

    /** The m search event spinner. */
    private Spinner mSearchEventSpinner;

    private Event event;
    private Invitation inv, inv2, inv3;
    private User user, user1;

    /**
     * Instantiates a new ads fragment.
     */
    public MyInvitesFragment() {
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(
                R.layout.activity_my_invite_fragment, container, false);
        mSearchEventSpinner = (Spinner) rootView
                .findViewById(R.id.searchInvitationList);
        listview = (ListView) rootView.findViewById(R.id.listInvites);
        context = rootView.getContext();
        listview.setAdapter(adapter);

        listview.setClickable(true);

        final Date y = new Date();

        user = new User("Mussum ipsum cacilds");
        user1 = new User("Mussum ipsum ");
        event = new Event("Mussum ipsum cacilds", "Mussum ipsum cacilds",
                "Mussum ipsum cacilds", "Mussum ipsum cacilds", y, y, 0,
                "Mussum ipsum cacilds", 100, true, user);
        inv = new Invitation(user1, event);
        inv2 = new Invitation(user1, event);
        inv3 = new Invitation(user1, event);
        Log.e("Teste", event.getName());
        Log.e("Teste", inv.getEvent().getName());

        searchSpinnerSetUp();
        return rootView;
    }

    private void searchSpinnerSetUp() {
        mListInvites = new ArrayList<Invitation>();
        mListInvites.add(inv);
        mListInvites.add(inv2);
        mListInvites.add(inv3);

        final List<String> status = new ArrayList<String>();
        status.add("Pendentes");
        status.add("Aceitados");
        status.add("Recudadsos");
        final ArrayAdapter<String> spinnerDataAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item, status);
        spinnerDataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchEventSpinner.setAdapter(spinnerDataAdapter);
        mSearchEventSpinner
                .setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent,
                            final View view, final int position, final long id) {
                        final String filter = parent
                                .getItemAtPosition(position).toString();
                        adapter = new InviteAdapter(context, mListInvites,
                                rootView);
                        listview.setAdapter(adapter);
                    }

                    @Override
                    public void onNothingSelected(final AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });

    }
}
