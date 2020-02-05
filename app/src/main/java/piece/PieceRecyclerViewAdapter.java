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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harpocrates.R;

import java.util.ArrayList;
import java.util.List;

public class PieceRecyclerViewAdapter extends RecyclerView.Adapter<PieceRecyclerViewAdapter.ViewHolder> {

    private List<Piece> pieces;
    private Context context;

    public PieceRecyclerViewAdapter( Context context, List<Piece> pieces ){
        super();

        this.pieces=pieces;
        this.context=context;

    }

    @NonNull
    @Override
    public PieceRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_piece_view,parent,false);
        return new ViewHolder( v );
    }

    @Override
    public void onBindViewHolder(@NonNull PieceRecyclerViewAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag( pieces.get( position ) );
        holder.updateViewHolder( context );
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PieceViewDialog dialog = new PieceViewDialog( context, pieces.get( position ) );
                dialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pieces.size();
    }

    public void setPieces( List<Piece> list ) {
        this.pieces = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        protected static final int DOWN=R.drawable.arrow_down;
        protected static final int UP=R.drawable.arrow_up;

        private boolean arrowdown=true;

        TextView titleTextView;
        ImageView arrowImageView;
        LinearLayout pieceInformationLayout;
        List<TextView> infoItems;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

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

        private void performArrowImageClick() {

            if ( arrowdown ) {
                pieceInformationLayout.setVisibility(View.VISIBLE);
                arrowImageView.setImageDrawable( ContextCompat.getDrawable( context, UP ) );
            } else {
                pieceInformationLayout.setVisibility(View.GONE);
                arrowImageView.setImageDrawable( ContextCompat.getDrawable( context, DOWN ) );
            }
            arrowdown=!arrowdown;

        }

        private void clearInfo() {
            infoItems.clear();
            pieceInformationLayout.removeAllViewsInLayout();
        }

        public void updateViewHolder( Context context) {
            if ( itemView.getTag() != null ) {
                Piece piece = (Piece) itemView.getTag();
                titleTextView.setText( piece.gettitle() );
                clearInfo();

                for ( int i=0; i<piece.getItems().size(); i++ ) {
                    infoItems.add( new TextView(context) );
                    infoItems.get(i).setText( piece.get(i).toString() );
                    infoItems.get(i).setBackgroundColor( ContextCompat.getColor(context,android.R.color.transparent) );
                    infoItems.get(i).setTextColor( Color.WHITE );
                    pieceInformationLayout.addView( infoItems.get(i) );
                }

                pieceInformationLayout.setVisibility( View.GONE );
            }
        }

    }


}
