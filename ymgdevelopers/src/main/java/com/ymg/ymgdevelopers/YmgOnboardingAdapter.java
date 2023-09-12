package com.ymg.ymgdevelopers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YmgOnboardingAdapter extends RecyclerView.Adapter<YmgOnboardingAdapter.OnboardingViewHolder>{

    private List<YmgOnboardItem> onboardItems;

    public YmgOnboardingAdapter(List<YmgOnboardItem> onboardItems) {
        this.onboardItems = onboardItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnboardingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboard,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {

        holder.setOnboardingData(onboardItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{



        private TextView title;
        private TextView description;
        private ImageView imageView;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageview);
            description = itemView.findViewById(R.id.desc);
        }

        void setOnboardingData(YmgOnboardItem onboardingData){
            title.setText(onboardingData.getTitle());
            description.setText(onboardingData.getDescription());
            imageView.setImageResource(onboardingData.getImage());

            imageView.setImageTintList(null);
        }
    }
}
