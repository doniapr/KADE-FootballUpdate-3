package com.doniapr.footballupdate.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.DetailLeagueActivity
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.League
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class ListLeagueAdapter (private val league: List<League>)
    : RecyclerView.Adapter<LeagueViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(LeagueUI().createView(AnkoContext.Companion.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = league.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(league[position])

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity<DetailLeagueActivity>(
                "leagueId" to league[position].leagueId,
                "leagueName" to league[position].leagueName)
        }
    }
}

class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val leagueName: TextView = view.find(R.id.txt_league_name)
    private val leagueBadge: ImageView = view.find(R.id.img_league_badge)

    fun bindItem(league: League){
        Picasso.get().load(league.leagueBagde).fit().into(leagueBadge)
        leagueName.text = league.leagueName
    }
}

class LeagueUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width= matchParent, height = wrapContent)
                padding = dip(8)

                cardView {
                    lparams(width=matchParent, height = wrapContent)
                    padding = dip(16)

                    linearLayout {
                        lparams(width =matchParent, height = wrapContent)
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL

                        imageView {
                            id = R.id.img_league_badge
                        }.lparams {
                            width = dip(50)
                            height = dip(50)
                            margin = dip(16)
                        }

                        textView {
                            id = R.id.txt_league_name
                            textSize = 16f
                        }.lparams {
                            margin = dip(16)
                        }
                    }
                }
            }
        }
    }
}
