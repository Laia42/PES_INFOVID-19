package edu.upc.fib.pes_infovid19.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.upc.fib.pes_infovid19.R
import edu.upc.fib.pes_infovid19.domain.structures.Mensaje
import kotlinx.android.synthetic.main.tarjeta_mensaje.view.*
import java.util.*

class AdapterMensaje(val c: Context) : RecyclerView.Adapter<AdapterMensaje.Holdermensaje>() {

    private var ListaMensajes: MutableList<Mensaje> = ArrayList<Mensaje>()

    fun addMessage(m: Mensaje) {
        ListaMensajes.add(m)
        notifyItemInserted(ListaMensajes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holdermensaje {
        val v: View = LayoutInflater.from(c).inflate(R.layout.tarjeta_mensaje, parent, false)
        return Holdermensaje(v)
    }

    override fun getItemCount(): Int = ListaMensajes.size

    override fun onBindViewHolder(holder: Holdermensaje, position: Int) {
        holder.getn().text = ListaMensajes[position].nombre
        holder.getm().text = ListaMensajes[position].mensaje
        holder.geth().text = ListaMensajes[position].data
    }

    class Holdermensaje(view: View) : RecyclerView.ViewHolder(view) {

        fun getn(): TextView {
            return itemView.authorName
        }

        fun getm(): TextView {
            return itemView.textMessage
        }

        fun geth(): TextView {
            return itemView.dateMessage
        }


    }

}