package adminfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.harpocrates.R;
import com.example.harpocrates.StashDataBase;
import com.example.harpocrates.account.AccountDM;

public class AdminAccFragment extends Fragment {

    private AdminFragmentInteractionListener mListener;

    protected ListView listView;
    protected ArrayAdapter adapter;

    public AdminAccFragment() {
        // Required empty public constructor
    }

    public static AdminAccFragment newInstance() {
        AdminAccFragment fragment = new AdminAccFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_acc, container, false);
        listView = view.findViewById( R.id.adminAccList );
        adapter = new ArrayAdapter( getContext(), R.layout.user_simple_item, StashDataBase.getInstance(getContext()).getAccountList() );
        listView.setAdapter( adapter );
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AdminFragmentInteractionListener) {
            mListener = (AdminFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
