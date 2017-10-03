package de.meetme;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

/**
 * @author greg
 * @since 6/21/13
 *
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */
public class EventListAdapter extends FirebaseListAdapter<Event> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String mUsername;

    public EventListAdapter(Query ref, Activity activity, int layout) {
        super(ref, Event.class, layout, activity);
    }

    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view A view instance corresponding to the layout we passed to the constructor.
     * @param event An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Event event) {
        // Map a Chat object to an entry in our listview
        String name = event.getEventname();
        TextView authorText = (TextView) view.findViewById(R.id.eventname);
        authorText.setText(name);

        ((TextView) view.findViewById(R.id.eventdatum)).setText(event.getDatum());
        ((TextView) view.findViewById(R.id.eventort)).setText(event.getOrt());
    }
}
