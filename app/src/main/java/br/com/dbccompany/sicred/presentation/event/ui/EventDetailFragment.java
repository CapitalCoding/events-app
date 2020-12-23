githubpackage br.com.dbccompany.sicred.presentation.event.ui;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.function.Consumer;

import br.com.dbccompany.sicred.R;
import br.com.dbccompany.sicred.databinding.EventDetailFragmentBinding;
import br.com.dbccompany.sicred.domain.model.CheckIn;
import br.com.dbccompany.sicred.domain.model.Event;
import br.com.dbccompany.sicred.utils.PresentationUtils;
import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class EventDetailFragment extends Fragment {

    private EventDetailViewModel mViewModel;
    private EventDetailFragmentBinding eventDetailFragmentBinding;
    private Callback callback;

    public static EventDetailFragment newInstance(String eventId) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        if(eventId != null && !eventId.isEmpty()) {
            Bundle args = new Bundle();
            args.putString("event_id", eventId);
            eventDetailFragment.setArguments(args);
        }
        return eventDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(EventDetailViewModel.class);
        eventDetailFragmentBinding = EventDetailFragmentBinding.inflate(inflater, container, false  );
        eventDetailFragmentBinding.setViewModel(mViewModel);
        callback = event -> {

        };
        Bundle bundle = getArguments();
        if(bundle != null){
            int eventId = Integer.valueOf(bundle.getString("event_id"));
            mViewModel.setEventId(eventId);
        }
        subscribeUi();
        return eventDetailFragmentBinding.getRoot();
    }

    private void subscribeUi() {
        eventDetailFragmentBinding.setLifecycleOwner(getViewLifecycleOwner());
        mViewModel.eventMutableLiveData.observe(getViewLifecycleOwner(), event -> {
            Toast.makeText(getContext(), "Got data;", Toast.LENGTH_SHORT).show();
            eventDetailFragmentBinding.notifyPropertyChanged(BR.event);
        });
        eventDetailFragmentBinding.toolbar.setOnMenuItemClickListener(item -> {
            Log.d(TAG, "subscribeUi: "+item.getItemId());
            switch(item.getItemId()){
                case R.id.action_share:
                    Event event = mViewModel.eventMutableLiveData.getValue();
                    startActivity(PresentationUtils.shareIntent(event.getDescription()));
                    return true;
                case R.id.action_back:
                    getActivity().onBackPressed();
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private static final String TAG = "EventDetailFragment";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: "+item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    public interface Callback {
        void checkIn(Event event);
    }
}