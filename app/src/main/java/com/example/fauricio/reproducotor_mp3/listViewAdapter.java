package com.example.fauricio.reproducotor_mp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fauricio on 12/03/18.
 */

public class listViewAdapter extends BaseAdapter{
    private ArrayList<Item> arrayItems;
    private Context context;
    private LayoutInflater layoutInflater;

    public listViewAdapter(ArrayList<Item> arrayItems, Context context) {
        this.arrayItems = arrayItems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayItems.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View vistaItem = layoutInflater.inflate(R.layout.item_list, viewGroup, false);
        ImageView iv_imagen = (ImageView) vistaItem.findViewById(R.id.iv_imagen);
        TextView tv_titulo = (TextView) vistaItem.findViewById(R.id.tv_titulo);
        TextView tv_contenido = (TextView) vistaItem.findViewById(R.id.tv_contenido);
        iv_imagen.setImageResource(arrayItems.get(i).getImage());
        tv_titulo.setText(arrayItems.get(i).getTitulo());
        tv_contenido.setText(arrayItems.get(i).getContenido());

        return vistaItem;
    }
}
