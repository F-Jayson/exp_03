package com.mobleapp.exp_03;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private final List<Animal> animals;

    public AnimalAdapter(List<Animal> animals) {
        this.animals = animals;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_card_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animals.get(position);
        holder.name.setText(animal.name);
        holder.image.setImageResource(animal.imageResId);
        holder.image.setContentDescription(animal.name);
    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public class AnimalViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageView image;

        AnimalViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.animal_name);
            image = itemView.findViewById(R.id.animal_image);
            itemView.setOnClickListener(v -> {
                // Read the current position after recycling or insertion, not a cached bind position.
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Toast.makeText(v.getContext(), animals.get(position).name,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
