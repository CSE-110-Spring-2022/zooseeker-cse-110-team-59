package com.example.zooseeker_cse_110_team_59;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class RouteSummaryAdapter extends RecyclerView.Adapter<RouteSummaryAdapter.RoutePointHolder> {
    private List<RoutePoint> list = Collections.emptyList();

    public void setRoutePoints(List<RoutePoint> newRoutePoints){
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

    class RoutePointHolder extends RecyclerView.ViewHolder {
        private final TextView nameStreet, cumDistance, placeName;

        private RoutePoint routePoint;

        public RoutePointHolder(@NonNull View itemView) {
            super(itemView);
            nameStreet = itemView.findViewById(R.id.street_name);
            cumDistance = itemView.findViewById(R.id.cum_distance);
            placeName = itemView.findViewById(R.id.place_name);
        }

        public RoutePoint getRoutePoint() {
            return routePoint;
        }

        public void setRoutePoint(RoutePoint routepoint){
            this.routePoint = routepoint;
            nameStreet.setText(routepoint.streetName);
            cumDistance.setText(routepoint.cumDistance + "");
            placeName.setText(routepoint.exhibitName);
        }
    }
}
