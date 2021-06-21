package com.example.recycler.adaptador;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recycler.R;
import com.example.recycler.model.DetalleMiembroDenunciado;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DetalleMiembroAdapter extends RecyclerView.Adapter<DetalleMiembroAdapter.RecyclerHolder> {
    private List<DetalleMiembroDenunciado> items;
    private List<DetalleMiembroDenunciado> originalItems;
    private RecyclerItemClick itemClick;


    public DetalleMiembroAdapter(List<DetalleMiembroDenunciado> items, DetalleMiembroAdapter.RecyclerItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }

    @NonNull
    @Override
    public DetalleMiembroAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new DetalleMiembroAdapter.RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerHolder holder, int position) {
        final DetalleMiembroDenunciado item = items.get(position);
        holder.txtNumeroDenuncias.setText("Numero de denuncias: " +item.getNumeroPublicacionesDenunciadas());
        holder.txtNickname_denuncias.setText("Nickname: " + item.getNickname());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private TextView txtNickname_denuncias;
        private TextView txtNumeroDenuncias;

        public RecyclerHolder(@NonNull View itemView_1) {
            super(itemView_1);
            txtNickname_denuncias = itemView.findViewById(R.id.txtNickname_denunciados);
            txtNumeroDenuncias = itemView.findViewById(R.id.txtNumeroDenuncias);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(DetalleMiembroDenunciado item);
    }

}
