package com.example.harpocrates;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harpocrates.PieceFragment.OnListFragmentInteractionListener;

import java.util.List;

import piece.Piece;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Piece} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PieceRecyclerViewAdapter extends RecyclerView.Adapter<PieceRecyclerViewAdapter.ViewHolder> {
    //list of pieces
    private List<Piece> mValues;
    //activity
    private final OnListFragmentInteractionListener mListener;

    public PieceRecyclerViewAdapter(List<Piece> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sample_piece_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.piece = mValues.get(position);
        //setting the values for the texts
        holder.contextTextView.setText(mValues.get(position).getContext());
        //holder.infoTextView.setText(mValues.get(position).getInfoString());

        holder.frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.piece);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View frame;
        public final TextView contextTextView;
        //public final TextView infoTextView;
        public Piece piece;

        public ViewHolder(View view) {
            super(view);
            frame=view;
            contextTextView = view.findViewById(R.id.ContextTextView);
            //infoTextView = view.findViewById(R.id.InfoTextView);
        }

        @Override
        public String toString() {
             return "";
        }
    }
}
