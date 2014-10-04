package works.blake.sandbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class PushPullActionBarActivity extends Activity implements AbsListView.OnScrollListener {

    int previous = 0;
    int mark = 0;

    private String[] demoItems = {
            "Item", "Item", "Item", "Item", "Item", "Item", "Item", "Item",
            "Item", "Item", "Item", "Item", "Item", "Item", "Item", "Item",
            "Item", "Item", "Item", "Item", "Item", "Item", "Item", "Item",
            "Item", "Item", "Item", "Item", "Item", "Item", "Item", "Item"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_pull_action_bar);

        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, demoItems);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
    }

    /**
     * WARNING: Use at your own risk. android.R.id.action_bar_container is private,
     * is private, and thus subject to change without warning.
     */
    private View getActionBarContainer() {
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return findViewById(resId);
    }

    /** OnScrollListener Methods **/

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) { }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        View c = view.getChildAt(0);
        if(c != null) {
            int position = -1 * (-c.getTop() + view.getFirstVisiblePosition() * c.getHeight());

            View actionBarContainer = getActionBarContainer();

            if (previous > position) {
                onScrollDown(position, actionBarContainer);
            } else if (previous < position) {
                onScrollUp(position, actionBarContainer);
            }

            previous = position;
        }
    }

    private void onScrollDown(int position, View view) {
        float diff = position - mark;
        if(view.getTranslationY() > -view.getHeight()) {
            view.setTranslationY(Math.max(-view.getHeight(), diff));
        } else {
            mark = position + view.getHeight();
        }
    }

    private void onScrollUp(int position, View view) {
        float diff = position - mark;
        if(view.getTranslationY() < 0) {
            view.setTranslationY(Math.min(0, diff));
        } else {
            mark = position;
        }
    }

}
