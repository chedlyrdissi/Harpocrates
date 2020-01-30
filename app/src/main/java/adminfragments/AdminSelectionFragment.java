package adminfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.harpocrates.R;

public class AdminSelectionFragment extends Fragment {

    private static final String UPDATE="update";
    private static final String SELECT="select";

    private AdminFragmentInteractionListener mListener;

    /**
     * components
     */

    private Spinner selectionSpinner;
    private ArrayAdapter adapter;
    private static final String[] choices=new String[]{UPDATE,SELECT};

    private EditText columnsTextField;
    private EditText tableTextField;
    private EditText conditionTextField;

    private CheckBox conditionCheckBox;
    private CheckBox orderCheckBox;

    private RadioGroup orderRadioGroup;
    private RadioButton asc;
    private RadioButton desc;

    private Button execute;

    public AdminSelectionFragment() {
        // Required empty public constructor
    }

    public static AdminSelectionFragment newInstance() {
        AdminSelectionFragment fragment = new AdminSelectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_selection, container, false);

        selectionSpinner= view.findViewById(R.id.updateInsertSpinner);
        adapter =new ArrayAdapter(getContext(),R.layout.spinner_item,choices);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        selectionSpinner.setAdapter(adapter);

        columnsTextField=view.findViewById(R.id.selectedColumnTextField);
        tableTextField = view.findViewById(R.id.tableTextField);
        conditionTextField=view.findViewById(R.id.returnQuerryConditionTextField);

        conditionCheckBox=view.findViewById(R.id.returnQuerryConditionCheckBox);
        orderCheckBox=view.findViewById(R.id.orderCheckBox);

        orderRadioGroup=view.findViewById(R.id.orderRadioGroup);
        asc=view.findViewById(R.id.ascRadioButton);
        desc=view.findViewById(R.id.decRadioButton);

        execute=view.findViewById(R.id.returnQuerryExecutionButton);

        conditionTextField.setVisibility(View.INVISIBLE);
        orderRadioGroup.setVisibility(View.INVISIBLE);

        conditionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    conditionTextField.setVisibility(View.VISIBLE);
                }else {
                    conditionTextField.setText("");
                    conditionTextField.setVisibility(View.INVISIBLE);
                }
            }
        });

        orderCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    orderRadioGroup.setVisibility(View.VISIBLE);
                }else{
                    asc.setChecked(false);
                    desc.setChecked(false);
                    orderRadioGroup.setVisibility(View.INVISIBLE);
                }
            }
        });

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
