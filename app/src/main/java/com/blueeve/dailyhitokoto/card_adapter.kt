// CardAdapter.kt
package com.blueeve.dailyhitokoto

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import kotlin.random.Random

class CardAdapter(
    private val items: MutableList<Pair<String, String>>,
    private val colorChangeListener: ColorChangeListener
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    companion object {
        private const val TAG = "CardAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val (hitokoto, fromWho) = items[position]
        holder.bind(hitokoto, fromWho)
    }

    override fun getItemCount(): Int = items.size

    fun addItem(hitokoto: String, fromWho: String) {
        items.add(Pair(hitokoto, fromWho))
        notifyItemInserted(items.size - 1)
        Log.d(TAG, "Added card with text: $hitokoto")
        Log.d(TAG, "Current cards: ${items.joinToString()}")
    }

    fun removeItem(position: Int) {
        if (position in items.indices) {
            val (removedText, _) = items.removeAt(position)
            notifyItemRemoved(position)
            Log.d(TAG, "Removed card with text: $removedText at position: $position")
            Log.d(TAG, "Current cards: ${items.joinToString()}")
        } else {
            Log.w(TAG, "Attempted to remove card at invalid position: $position")
        }
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val hitokotoTextView: TextView = itemView.findViewById(R.id.Hitokotovalue)
        private val fromWhoTextView: TextView = itemView.findViewById(R.id.from_fromwho)

        fun bind(hitokoto: String, fromWho: String) {
            hitokotoTextView.text = hitokoto
            fromWhoTextView.text = fromWho

            // Set random colors
            val cardBackgroundColor = getRandomColor(200, 255)
            val textColor = getRandomColor(50, 100)
            cardView.setCardBackgroundColor(cardBackgroundColor)
            hitokotoTextView.setTextColor(textColor)
            fromWhoTextView.setTextColor(textColor)

            // Notify listener to update other views
            colorChangeListener.onColorChange(cardBackgroundColor, textColor)
        }

        private fun getRandomColor(minColorValue: Int, maxColorValue: Int): Int {
            val r = Random.nextInt(minColorValue, maxColorValue + 1)
            val g = Random.nextInt(minColorValue, maxColorValue + 1)
            val b = Random.nextInt(minColorValue, maxColorValue + 1)
            return Color.rgb(r, g, b)
        }
    }

    interface ColorChangeListener {
        fun onColorChange(cardBackgroundColor: Int, textColor: Int)
    }
}
