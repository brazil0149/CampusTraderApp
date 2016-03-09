package com.example.olabiyi.tradeu;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * class that creates a recycerview of cardview for the tradeing app
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";
    Context context;
    private ArrayList<RecyclerData> myList;
    OnItemClickListener mItemClickListener;
    private static final int SECTION_TYPE = 0;
 //   private ArrayList<int> mDatasetTypes;
    private ArrayList<Integer> mDatasetTypes = new ArrayList<Integer>();
 //   private int[] mDatasetTypes; //view types
    public static final int BUY= 0;
    public static final int SELL = 1;
    public static final int TRADE = 2;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }


    public class BuyViewHolder extends ViewHolder implements View.OnClickListener{
        RecyclerData mLog;
        RecyclerView recycler_view;
        private final TextView etTitleTextView;
        private final TextView Map;
        private final TextView email;
        private final TextView name;
        private final TextView Location;
        private final TextView option;
        private final TextView etDescriptionTextView;
        private LinearLayout mainLayout;
        public ImageView crossImage;

        public BuyViewHolder(final View v) {
            super(v);
            etTitleTextView = (TextView) v.findViewById(R.id.list_title);
            Location = (TextView) v.findViewById(R.id.Location);
            Map = (TextView) v.findViewById(R.id.Map);
            email = (TextView) v.findViewById(R.id.email);
            name = (TextView) v.findViewById(R.id.Fname);
            option = (TextView) v.findViewById(R.id.option);
            etDescriptionTextView = (TextView) v.findViewById(R.id.list_desc);
            mainLayout = (LinearLayout) v.findViewById(R.id.top_layout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Position:"+Integer.toString(getPosition()),Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public class SellViewHolder extends ViewHolder implements View.OnClickListener{
        RecyclerData mLog;
        RecyclerView recycler_view;
        private final TextView etTitleTextView;
        private final TextView Map;
        private final TextView email;
        private final TextView name;
        private final TextView Location;
        private final TextView option;
        private final TextView etDescriptionTextView;
        private RelativeLayout mainLayout;
        public ImageView crossImage;

        public SellViewHolder(final View v) {
            super(v);
            etTitleTextView = (TextView) v.findViewById(R.id.list_title);
            Location = (TextView) v.findViewById(R.id.Location);
            Map = (TextView) v.findViewById(R.id.Map);
            email = (TextView) v.findViewById(R.id.email);
            name = (TextView) v.findViewById(R.id.Fname);
            option = (TextView) v.findViewById(R.id.option);
            etDescriptionTextView = (TextView) v.findViewById(R.id.list_desc);
            mainLayout = (RelativeLayout) v.findViewById(R.id.top_layout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Position:"+Integer.toString(getPosition()),Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public class TradeViewHolder extends ViewHolder implements View.OnClickListener{
        RecyclerData mLog;
        RecyclerView recycler_view;
        private final TextView etTitleTextView;
        private final TextView Map;
        private final TextView email;
        private final TextView name;
        private final TextView Location;
        private final TextView option;
        private final TextView etDescriptionTextView;
        private RelativeLayout mainLayout;
        public ImageView crossImage;

        public TradeViewHolder(final View v) {
            super(v);
            etTitleTextView = (TextView) v.findViewById(R.id.list_title);
            Location = (TextView) v.findViewById(R.id.Location);
            option = (TextView) v.findViewById(R.id.option);
            Map = (TextView) v.findViewById(R.id.Map);
            email = (TextView) v.findViewById(R.id.email);
            name = (TextView) v.findViewById(R.id.Fname);
            etDescriptionTextView = (TextView) v.findViewById(R.id.list_desc);
            mainLayout = (RelativeLayout) v.findViewById(R.id.top_layout);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(),"Position:"+Integer.toString(getPosition()),Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }


    public RecyclerAdapter(ArrayList<RecyclerData> myList, ArrayList<Integer> dataSetTypes) {
        this.myList = myList;
        mDatasetTypes = dataSetTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;
        if (viewType == BUY) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list, viewGroup, false);
            return new BuyViewHolder(v);

        } else if (viewType == SELL) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list1, viewGroup, false);
            return new SellViewHolder(v);

        } else  {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_list2, viewGroup, false);
            return new TradeViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (viewHolder.getItemViewType() == BUY) {
            BuyViewHolder holder = (BuyViewHolder) viewHolder;
            Log.d("onBindViewHoler ", myList.size() + "");
            holder.etTitleTextView.setText(myList.get(position).getTitle());
            holder.etDescriptionTextView.setText(myList.get(position).getDescription());
            holder.Location.setText(myList.get(position).getLocation());
            holder.option.setText(myList.get(position).getoption());
            holder.email.setText(myList.get(position).getemail());
            holder.name.setText(myList.get(position).getname());
     /**       holder.Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity map = new MainActivity();
                    map.OpenMap();


                }
            });
      */
        }
        else if (viewHolder.getItemViewType() == SELL) {
            SellViewHolder holder = (SellViewHolder) viewHolder;
            Log.d("onBindViewHoler ", myList.size() + "");
            holder.etTitleTextView.setText(myList.get(position).getTitle());
            holder.etDescriptionTextView.setText(myList.get(position).getDescription());
            holder.Location.setText(myList.get(position).getLocation());
       /**     holder.Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity map = new MainActivity();
                    map.OpenMap();
                }
            });
        */
        }
        else {
            TradeViewHolder holder = (TradeViewHolder) viewHolder;
            Log.d("onBindViewHoler ", myList.size() + "");
            holder.etTitleTextView.setText(myList.get(position).getTitle());
            holder.etDescriptionTextView.setText(myList.get(position).getDescription());
            holder.Location.setText(myList.get(position).getLocation());
      /**      holder.Map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity map = new MainActivity();
                    map.OpenMap();
                //    OpenMap();
                }
            });
       */
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public void notifyData(ArrayList<RecyclerData> myList){
        Log.d("notifyData ",myList.size()+"");
        this.myList=myList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(true)//if ob.issetStatus() true if user types and image if user selects an image from the directory. but getting nullPointerException here.
        {
            return 0;
        }
        else if (true)
        {
            return 1;
        }
        else {return 2;}
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public void setonItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private void OpenMap() {
        AlertDialog.Builder alert0 = new AlertDialog.Builder(context);
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View MapView = li.inflate(R.layout.activity_map, null);

        alert0.setTitle("Map");
        alert0.setView(MapView);

        alert0.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        Toast.makeText(context,
                                "Ok Clicked", Toast.LENGTH_SHORT).show();
                    }
                });
        // create an alert dialog
        AlertDialog alert2 = alert0.create();
        alert2.show();
    }
}
