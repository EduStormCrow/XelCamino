package com.darkwoods.porelcamino;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darkwoods.porelcamino.database.Refuel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
// TODO escribir el adpater para el recycler view
//https://android.jlelse.eu/android-architecture-components-room-livedata-and-viewmodel-fca5da39e26b

public class RefuelAdapter extends RecyclerView.Adapter<RefuelAdapter.RefuelViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds refuel data and the Context
    private List<Refuel> mRefuelEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the RefuelAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public RefuelAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new RefuelViewHolder that holds the view for each task
     */
    @Override
    public RefuelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.refuel_item_layout, parent, false);

        return new RefuelViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(RefuelViewHolder holder, int position) {
        // Determine the values of the wanted data
        Refuel refuel = mRefuelEntries.get(position);
        String place = refuel.getPlace();
        String fuelType = refuel.getFuelType();
        Double litres = refuel.getLitres();

        String updatedAt = dateFormat.format(refuel.getRefuelDateTime());
        String litersType = litres + mContext.getString(R.string.Refuel_LitresPlusType) +fuelType;

        //Set values
        holder.placeView.setText(place);
        holder.dateTimeView.setText(updatedAt);
        holder.litersTypeView.setText(litersType);

//
//        // Programmatically set the text and color for the priority TextView
//        String priorityString = "" + priority; // converts int to String
//        holder.priorityView.setText(priorityString);
//
//        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
//        // Get the appropriate background color based on the priority
//        int priorityColor = getPriorityColor(priority);
//        priorityCircle.setColor(priorityColor);
    }

    /*
    TODO convertir este metodo para cuando llenemos el tanquecito de nafta en funcion de la
    cantidad de carga
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow

    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default:
                break;
        }
        return priorityColor;
    }
*/
    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mRefuelEntries == null) {
            return 0;
        }
        return mRefuelEntries.size();
    }

    public List<Refuel> getmRefuelEntries() {
        return mRefuelEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setmRefuelEntries(List<Refuel> refuelEntries) {
        mRefuelEntries = refuelEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class RefuelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        TextView placeView;
        TextView dateTimeView;
        TextView litersTypeView;

        /**
         * Constructor for the RefuelViewHolder.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public RefuelViewHolder(View itemView) {
            super(itemView);

            placeView = itemView.findViewById(R.id.refuel_place);
            dateTimeView = itemView.findViewById(R.id.refuel_date_time);
            litersTypeView = itemView.findViewById(R.id.refuel_type_liters);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mRefuelEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

}
