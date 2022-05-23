package com.example.zooseeker_cse_110_team_59;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RouteSummaryAdapter extends RecyclerView.Adapter<RouteSummaryAdapter.RoutePointHolder> {
    List<RoutePoint> list;
    class RoutePointHolder extends RecyclerView.ViewHolder{
        private final TextView namestreet, cumdistance, animalname ;
        private RoutePoint routepoint;
        public RoutePointHolder(@NonNull View itemView) {
            super(itemView);
            namestreet = itemView.findViewById(R.id.current_streetname);
            cumdistance = itemView.findViewById(R.id.current_distance);
            animalname = itemView.findViewById(R.id.current_exhibit);
        }
        public RoutePoint getRoutePoint(){return routepoint;}
        public void setRoutePoint(RoutePoint routepoint){
            this.routepoint = routepoint;
            namestreet.setText(routepoint.streetname);
            cumdistance.setText(routepoint.cumdistance + "");
            animalname.setText(routepoint.exhibitName);
        }
    }
    public void setRoutePoint(List<RoutePoint> newRoutePoints){
        this.list.clear();
        this.list = newRoutePoints;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RoutePointHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.route_summary_items, parent, false);
        return new RoutePointHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutePointHolder holder, int position) {
        holder.setRoutePoint(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
