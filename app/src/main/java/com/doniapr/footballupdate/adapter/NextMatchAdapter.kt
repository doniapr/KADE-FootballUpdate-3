package com.doniapr.footballupdate.adapter

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.DetailMatchActivity
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.utility.formatTo
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.utility.toDateAndHour
import com.doniapr.footballupdate.utility.toHour
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class NextMatchAdapter(private val match: List<Match>)
    : RecyclerView.Adapter<NextMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextMatchViewHolder {
        return NextMatchViewHolder(NextMatchUI().createView(AnkoContext.Companion.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = match.size

    override fun onBindViewHolder(holder: NextMatchViewHolder, position: Int) {
        holder.bindItem(match[position])

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity<DetailMatchActivity>("eventId" to match[position].eventId)
        }
    }
}

class NextMatchViewHolder(view: View): RecyclerView.ViewHolder(view){

    private val txtMatchWeek: TextView = view.find(R.id.txt_match_week)
    private val txtHomeTeam: TextView = view.find(R.id.txt_home_team)
    private val txtAwayTeam: TextView = view.find(R.id.txt_away_team)
    private val txtMatchDate: TextView = view.find(R.id.txt_match_date)
    private val txtMatchTime: TextView = view.find(R.id.txt_match_time)

    fun bindItem(match: Match){
        txtMatchWeek.text = "Round " + match.round
        txtHomeTeam.text = match.homeTeam
        txtAwayTeam.text = match.awayTeam

        if ((match.dateEvent != null || match.dateEvent != "")  && (match.time != null || match.time != "")){
            val utcDate = match.dateEvent.toString() + " " + match.time.toString()
            val wibDate = utcDate.toDateAndHour()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else if((match.dateEvent != null || match.dateEvent != "")  && (match.time == null || match.time == "")){
            val utcDate = match.dateEvent.toString()
            val wibDate = utcDate.toDate()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = "-"
        } else if ((match.dateEvent == null || match.dateEvent == "")  && (match.time != null || match.time != "")){
            val utcDate = match.time.toString()
            val wibDate = utcDate.toHour()
            txtMatchDate.text = "-"
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else {
            txtMatchDate.text = "-"
            txtMatchTime.text = "-"
        }
    }
}


class NextMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width= matchParent, height = wrapContent)
                padding = dip(8)

                cardView {
                    lparams(width= matchParent, height = wrapContent)
                    padding = dip(16)

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        textView {
                            id = R.id.txt_match_week
                            textSize = 12f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        constraintLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(8)
                            id = R.id.cv_match_info

                            textView {
                                id = R.id.txt_home_team
                                textSize = 16f
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topToTop = R.id.cv_match_info
                                startToStart = R.id.cv_match_info
                                endToStart = R.id.txt_versus
                            }

                            textView {
                                id = R.id.txt_versus
                                textSize = 14f
                                text = "VS"
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topToTop = R.id.cv_match_info
                                startToStart = R.id.cv_match_info
                                endToEnd = R.id.cv_match_info
                            }

                            textView {
                                id = R.id.txt_away_team
                                textSize = 16f
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topToTop = R.id.cv_match_info
                                startToEnd = R.id.txt_versus
                                endToEnd = R.id.cv_match_info
                            }
                        }

                        textView {
                            id = R.id.txt_match_date
                            textSize = 14f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                        textView {
                            id = R.id.txt_match_time
                            textSize = 12f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }


                    }
                }
            }
        }
    }
}