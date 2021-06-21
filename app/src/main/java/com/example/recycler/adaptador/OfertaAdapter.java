package com.example.recycler.adaptador;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.recycler.R;
import com.example.recycler.model.Oferta;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OfertaAdapter extends Adapter<OfertaAdapter.RecyclerHolder> {
    private List<Oferta> items;
    private List<Oferta> originalItems;
    private RecyclerItemClick itemClick;
    private Context conetext;

    public void setConetext(Context conetext){
        this.conetext = conetext;
    }

    public OfertaAdapter(List<Oferta> items, RecyclerItemClick itemClick) {
        this.items = items;
        this.itemClick = itemClick;
        this.originalItems = new ArrayList<>();
        originalItems.addAll(items);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oferta_view, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerHolder holder, final int position) {
        final Oferta item = items.get(position);
        Picasso.with(conetext).load(item.getURLFoto()).networkPolicy(NetworkPolicy.NO_CACHE)
                .memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imgItem);
        holder.imgItem.setImageResource(item.getImgResource());
        holder.tvTitulo.setText(item.getTitulo());
        holder.tvDescripcion.setText(item.getDescripcion());
        holder.tvPuntuacion.setText("Puntuación:" + item.getPuntuacion());
        holder.tvPrecio.setText("$" + item.getPrecio());
        holder.tvFechaFin.setText("Acaba: " + item.getFechaFin());




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.itemClick(item);
            }
        });

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("itemDetail", item);
                holder.itemView.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filter(final String strSearch) {
        if (strSearch.length() == 0) {
            items.clear();
            items.addAll(originalItems);
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                items.clear();
                List<Oferta> collect = originalItems.stream()
                        .filter(i -> i.getTitulo().toLowerCase().contains(strSearch))
                        .collect(Collectors.toList());

                items.addAll(collect);
            }
            else {
                items.clear();
                for (Oferta i : originalItems) {
                    if (i.getTitulo().toLowerCase().contains(strSearch)) {
                        items.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }


    public class RecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvTitulo;
        private TextView tvDescripcion;
        private  TextView tvPuntuacion;
        private TextView tvPrecio;
        private  TextView tvFechaFin;

        public RecyclerHolder(@NonNull View itemView_1) {
            super(itemView_1);

            imgItem = itemView.findViewById(R.id.imgItem);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion_codigo);
            tvPuntuacion = itemView.findViewById(R.id.tvPuntuacion_codigo);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
        }
    }

    public interface RecyclerItemClick {
        void itemClick(Oferta item);
    }
}