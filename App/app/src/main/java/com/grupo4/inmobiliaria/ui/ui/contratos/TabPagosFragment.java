package com.grupo4.inmobiliaria.ui.ui.contratos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.service.autofill.TextValueSanitizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.grupo4.inmobiliaria.R;
import com.grupo4.inmobiliaria.modelo.Pago;

import java.util.List;

public class TabPagosFragment extends Fragment {
    private List<Pago> pagos;
    private ListView lvPagos;
    private TextView tvNoPagos;

    public TabPagosFragment(List<Pago> pagos){
        this.pagos = pagos;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab_pagos, container, false);
        inicializarVista(root);

        // Prof. Mercado: Acá hay un IF porque este Fragment NO tiene ViewModel. El listado de pagos viene por constructor, por lo que la comprobación de si está vacío tiene que estar si o si acá.
        if (pagos.isEmpty()) {
            tvNoPagos.setVisibility(View.VISIBLE);
            tvNoPagos.setText("No se realizaron pagos en este contrato.");
        } else {
            tvNoPagos.setVisibility(pagos.isEmpty() ? View.VISIBLE : View.INVISIBLE);
            ArrayAdapter<Pago> adapter = new ListaPagosAdapter(getContext(), R.layout.list_item_pago, pagos, getLayoutInflater());
            lvPagos.setAdapter(adapter);
        }

        final SwipeRefreshLayout recarga = root.findViewById(R.id.recarga);
        recarga.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("salida","Recargar lista de pagos");
                recarga.setRefreshing(false);
            }
        });

        return root;
    }

    private void inicializarVista(View root){
        tvNoPagos = root.findViewById(R.id.tvNoPagos);
        lvPagos = root.findViewById(R.id.lvPagos);
    }
}