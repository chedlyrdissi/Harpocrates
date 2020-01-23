package piece;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harpocrates.R;

import java.util.ArrayList;
import java.util.List;

public class PieceRecyclerViewAdapter extends RecyclerView.Adapter<PieceRecyclerViewAdapter.PieceViewHolder> {

    private List<Piece> pieces;
    private Context context;

    public PieceRecyclerViewAdapter( Context context, List<Piece> pieces ){

        this.pieces=pieces;
        this.context=context;

    }

    @NonNull
    @Override
    public PieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_piece_view,parent,false);

        return new PieceViewHolder( v, pieces.get(viewType).getItems() );
    }

    @Override
    public void onBindViewHolder(@NonNull PieceViewHolder holder, int position) {
        holder.setTitleTextView( pieces.get(position).gettitle() );
        holder.setInfo( pieces.get(position).getItems() );
    }

    @Override
    public int getItemCount() {
        return pieces.size();
    }

    public class PieceViewHolder extends RecyclerView.ViewHolder{

        protected static final int DOWN=R.drawable.arrow_down;
        protected static final int UP=R.drawable.arrow_up;

        private boolean arrowdown=true;

        TextView titleTextView;
        ImageView arrowImageView;
        LinearLayout pieceInformationLayout;
        List<TextView> infoItems;
        List<Entry> items;

        public PieceViewHolder(@NonNull View itemView,List<Entry> items) {
            super(itemView);

            this.items=items;

            titleTextView=itemView.findViewById(R.id.TitleTextView);
            arrowImageView=itemView.findViewById(R.id.ArrowImageView);
            pieceInformationLayout=itemView.findViewById(R.id.pieceInformationLayout);
            infoItems=new ArrayList<>();

            arrowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performArrowImageClick();
                }
            });
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performArrowImageClick();
                }
            });

        }

        /**
         * if the piece arrow is down show the info
         * otherwise hide the shown info
         */
        public void performArrowImageClick() {

            if ( arrowdown ) {
                showInfoItems();
            } else {
                hideInfoItems();
            }
            arrowdown=!arrowdown;

        }

        public void showInfoItems() {

            arrowImageView.setImageResource(UP);
            pieceInformationLayout.setVisibility(View.VISIBLE);
            clearInfo();

            if ( items != null ) {
                for ( int i=0; i<items.size(); i++ ) {

                    infoItems.add( new TextView(context) );
                    infoItems.get(i).setText( items.get(i).getKey() + " = " + items.get(i).getValue() );
                    infoItems.get(i).setTextColor(Color.WHITE);
                    pieceInformationLayout.addView(infoItems.get(i));

                }
            }

        }

        public void hideInfoItems() {
            arrowImageView.setImageResource(DOWN);
            pieceInformationLayout.setVisibility(View.GONE);
            clearInfo();
        }

        /**
         * prepares the list of TextViews that hold the info to put in the piece
         */
        public void setInfo( List<Entry> items ){
            this.items=items;
        }

        public void clearInfo() {
            infoItems.clear();
            pieceInformationLayout.removeAllViewsInLayout();
        }

        public void updateInfoItems( List<Entry> items ) {

            this.items=items;
            showInfoItems();

        }

        public void setTitleTextView(String title){

            if ( title != null )
                titleTextView.setText(title);

        }

    }


}
