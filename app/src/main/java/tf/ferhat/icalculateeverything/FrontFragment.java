package tf.ferhat.icalculateeverything;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import tf.ferhat.icalculateeverything.ToDoEntryDB.ToDoEntry;
import tf.ferhat.icalculateeverything.ToDoEntryDB.ToDoEntryViewModel;

import static android.app.Activity.RESULT_OK;

public class FrontFragment extends Fragment{
    private ToDoEntryViewModel mToDoEntryViewModel;
    public static final int NEW_TODOENTRY_ACTIVITY_REQUEST_CODE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_front, container, false);

        final FragmentActivity fragmentActivity = getActivity();

                fragmentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        final RecyclerView recyclerView =  view.findViewById(R.id.recyclerview);
                        final ToDoEntryAdapter adapter = new ToDoEntryAdapter(fragmentActivity);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(fragmentActivity));

                        mToDoEntryViewModel = ViewModelProviders.of(fragmentActivity).get(ToDoEntryViewModel.class);
                        mToDoEntryViewModel.getAll().observe(fragmentActivity, new Observer<List<ToDoEntry>>() {
                            @Override
                            public void onChanged(@Nullable final List<ToDoEntry> toDoEntries) {
                                // Update the cached copy of the entries in the adapter.
                                adapter.setToDoEntries(toDoEntries);
                            }
                        });

                        Button addButton = view.findViewById(R.id.buttonAddEntry);
                        addButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent (fragmentActivity, NewToDoEntryActivity.class);
                                startActivityForResult(intent, NEW_TODOENTRY_ACTIVITY_REQUEST_CODE);
                            }
                        });
                    }
                });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_TODOENTRY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            mToDoEntryViewModel.insert(new ToDoEntry(data.getStringExtra("title"), data.getStringExtra("desc"), data.getStringExtra("date"), data.getStringExtra("base64picturethumbnail"), data.getStringExtra("base64picturefull"), data.getStringExtra("cat"), data.getStringExtra("add")));
            Toast.makeText(getContext(), "Entry added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Entry not saved", Toast.LENGTH_SHORT).show();
        }
    }

}
