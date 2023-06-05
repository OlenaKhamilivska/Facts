package com.example.numbersapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numbersapi.db.Numbers;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.CustomViewHolder> {
    @NonNull
    private List <Numbers> numbers;
    private Context context;

    public CustomAdapter(List<Numbers> numbers, Context context) {
        this.numbers = numbers;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent,
                false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, @SuppressLint
            ("RecyclerView") int position) {
        String sFacts = numbers.get(position).getFacts();
        holder.tv_custom.setText(sFacts);
        holder.tv_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFragment fragment = MyFragment.newInstance(numbers.get(position).getNumber(),
                        numbers.get(position).getFacts());
                FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit);
                fragmentTransaction.add(R.id.frameLayoutHolder, fragment,"tag");
                fragmentTransaction.addToBackStack("tag");
                fragmentTransaction.commit();
                Log.d("myIDTAG", "onClick: " +numbers.get(position).getFacts());
            }
        });
        Log.d("myTAG", "onBindViewHolder: " + position);
    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView tv_custom;
        CustomViewHolder (View itemView) {
            super(itemView);
            mView = itemView;
            tv_custom = mView.findViewById(R.id.tv_custom);
        }
    }
}

