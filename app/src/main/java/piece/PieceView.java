package piece;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.harpocrates.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class PieceView extends View {

    protected static final String DOWN="@android:drawable/arrow_down_float";
    protected static final String UP="@android:drawable/arrow_up_float";

    private Piece piece;

    private boolean arrowdown=false;

    private LinearLayout mainlayout;
    private TextView contextTextView;
    private ImageView image;

    private ArrayList<TextView> infoviews=infoviews=new ArrayList<>();

    public PieceView(Context context) {
        super(context);
        init();
    }

    public PieceView(Context context, Piece piece){
        super(context);
        this.piece=piece;
        init();
    }

    private void init() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sample_piece_view, null);
        //get the subviews
        contextTextView = findViewById(R.id.ContextTextView);
        image = findViewById(R.id.ArrowImageView);
        mainlayout= findViewById(R.id.pieceMainLayout);
/*
        image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrowdown){
                    updateInfo();
                }else {
                    clearInfo();
                }
            }
        });*/

        setBackgroundColor(Color.WHITE);

    }

    private void clearInfo() {
        if (infoviews==null) infoviews=new ArrayList<>();

        for(TextView textView:infoviews){
            textView.setVisibility(GONE);
        }
    }

    private void updateInfo(){

        TextView view;

        if(infoviews==null) infoviews=new ArrayList<>(piece.getInfoMap().size());

        for(String key:piece.getInfoMap().keySet()){
            view=new TextView(getContext());
            infoviews.add(view);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    200,60
            );
            params.setMargins(50,0,0,0);
            view.setLayoutParams(params);
            view.setText(key+"="+piece.getInfoMap().get(key));
            mainlayout.addView(view);
        }

    }

}
