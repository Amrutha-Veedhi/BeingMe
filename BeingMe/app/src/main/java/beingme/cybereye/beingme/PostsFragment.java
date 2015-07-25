package beingme.cybereye.beingme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Samyuktha-Tech on 25-Jul-15.
 */
public class PostsFragment extends Fragment {
    enum PostType{
        SUCCESS, HAPPY
    }
    private PostType postType;
    List<Post> list;
    Button button;
    String nText;
    ListView listView;
    EditText textedit;
    ArrayAdapter<Post> arrayAdapter;
    SharedPreferences sharedpreferences;


    public static PostsFragment newInstance(PostsFragment.PostType postType){
        Bundle bundle = new Bundle();
        PostsFragment postsFragment = new PostsFragment();
        bundle.putSerializable("postType", postType);
        postsFragment.setArguments(bundle);
        return postsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postType = (PostType)getArguments().get("postType");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_post, null, false);
        //Populate List
        initSharedPrefs();
        listView = (ListView) root.findViewById(android.R.id.list);
        list = getItems();
        arrayAdapter = new ArrayAdapter<Post>(getActivity().getApplicationContext(),R.layout.custom_simple_list_item1,list);
        listView.setAdapter(arrayAdapter);

        //For adding posts
        textedit = (EditText) root.findViewById(R.id.newtext);
        button = (Button) root.findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(textedit.getText().toString());
            }
        });

        return root;
    }

    private void initSharedPrefs(){
        switch (postType) {
            case SUCCESS:
                sharedpreferences = getActivity().getSharedPreferences("Success", Context.MODE_PRIVATE);
                break;
            case HAPPY:
                sharedpreferences = getActivity().getSharedPreferences("Happy", Context.MODE_PRIVATE);
                break;
        }
    }

    private List<Post> getItems(){
        list = new ArrayList<Post>();
        Map<String, ? > hashMap = sharedpreferences.getAll();
        for (Map.Entry<String,?> entry : hashMap.entrySet()){
            list.add(new Post(entry.getValue().toString(), entry.getKey()));
        }

        Collections.sort(list, new Comparator<Post>() {
            @Override
            public int compare(Post lhs, Post rhs) {
                return lhs.date.compareTo(rhs.date);
            }
        });

        return list;
    }

    private void addItem(String item ){
        String todayDate = getTodayDateString();
        //String existingTodayPost = sharedpreferences.getString(todayDate, "");
        hideKeyboard();
        Post todayPost = null;
        for( Post post: list){
            if(post.date.equals(todayDate)){
                todayPost = post;
                break;
            }
        }

        String appendedString;

        if (todayPost == null) {
            Log.d("PostsFragment","todayPost null "+item);
            list.add(new Post(item, todayDate));
            appendedString = item;
        } else{
            Log.d("PostsFragment","todayPost not null "+todayPost.post);
            todayPost.post = todayPost.post + "\n" + item;
            appendedString = todayPost.post;
        }

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(todayDate, appendedString);
        editor.apply();


        arrayAdapter.notifyDataSetChanged();
        textedit.setText("");
        //listView.smoothScrollToPosition(listView.getCount() - 1);
    }

    private void hideKeyboard(){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){

        }
    }

    private String getTodayDateString(){
        Date date = Calendar.getInstance().getTime();

        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(date);
    }
}
