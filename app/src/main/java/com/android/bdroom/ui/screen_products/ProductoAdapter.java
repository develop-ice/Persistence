package com.android.bdroom.ui.screen_products;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.bdroom.R;
import com.android.bdroom.persistence.entity.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder> {

    private List<Producto> itemList = new ArrayList<>();

    private OnItemClicklistener listener;

    public interface OnItemClicklistener {
        void OnItemClick(Producto producto);
    }

    public void setOnItemClickListener(OnItemClicklistener listener) {
        this.listener = listener;
    }

    public void filterList(ArrayList<Producto> filteredList) {
        itemList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public ProductoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_producto, parent, false);
        return new ProductoViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ProductoViewHolder holder, int position) {
        Producto producto = itemList.get(position);
        holder.bind(producto);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    void setProductos(List<Producto> productos) {
        this.itemList = productos;
        notifyDataSetChanged();
    }

    Producto getProductoAt(int position) {
        return itemList.get(position);
    }

    class ProductoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNombre, tvCant;

        private ProductoViewHolder(View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tv_nombre);
            tvCant = itemView.findViewById(R.id.tv_cant);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(itemList.get(position));
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        private void bind(Producto producto) {
            tvNombre.setText(producto.getNombre());
            tvCant.setText("TOTAL: " + producto.getCantidad());
        }
    }
}
