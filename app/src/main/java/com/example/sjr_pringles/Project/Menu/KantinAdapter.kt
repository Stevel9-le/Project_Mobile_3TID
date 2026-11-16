package com.example.sjr_pringles.Project.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sjr_pringles.R

class KantinAdapter(
    private val listMenu: List<KantinModel>,
    private val onItemClick: (KantinModel) -> Unit
) : RecyclerView.Adapter<KantinAdapter.KantinViewHolder>() {

    class KantinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNamaMenu)
        val tvKategori: TextView = itemView.findViewById(R.id.tvKategoriMenu)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHargaMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KantinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kantin, parent, false)
        return KantinViewHolder(view)
    }

    override fun onBindViewHolder(holder: KantinViewHolder, position: Int) {
        val item = listMenu[position]
        holder.tvNama.text = item.namaMenu
        holder.tvKategori.text = item.kategori
        holder.tvHarga.text = "Rp ${item.harga}"

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = listMenu.size
}