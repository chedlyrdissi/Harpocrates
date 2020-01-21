package piece;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PieceRecyclerViewAdapter extends RecyclerView.Adapter<PieceRecyclerViewAdapter.PieceViewHolder> {

    private List<Piece> pieces;

    @NonNull
    @Override
    public PieceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PieceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return pieces.size();
    }

    public class PieceViewHolder extends RecyclerView.ViewHolder{

        protected static final String DOWN="@android:drawable/arrow_down_float";
        protected static final String UP="@android:drawable/arrow_up_float";

        private boolean arrowdown=false;

        TextView contextTextView;
        ImageView arrowImageView;
        LinearLayout pieceContextLayout;
        TextView[] items;

        public PieceViewHolder(@NonNull View itemView) {
            super(itemView);
        }



    }


}
