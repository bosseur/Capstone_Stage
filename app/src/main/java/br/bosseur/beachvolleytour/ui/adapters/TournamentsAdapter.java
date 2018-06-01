package br.bosseur.beachvolleytour.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.OnItemClickListener;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.utils.DateUtil;
import butterknife.BindView;
import butterknife.ButterKnife;


public class TournamentsAdapter extends RecyclerView.Adapter<TournamentsAdapter.TournamentsAdapterViewHolder> {
  private final OnItemClickListener<BeachTournament> mClickListener;
  private List<BeachTournament> mTournaments;

  public TournamentsAdapter(OnItemClickListener<BeachTournament> clickListener) {
    mClickListener = clickListener;
  }

  @NonNull
  @Override
  public TournamentsAdapter.TournamentsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.layout_tournaments, parent, false);

    return new TournamentsAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TournamentsAdapter.TournamentsAdapterViewHolder holder, int position) {
    BeachTournament tournament = mTournaments.get(position);

    Context context = holder.itemView.getContext();
    String startDate = DateUtil.format(tournament.getStartDate(), context.getString(R.string.date_format));
    String endDate = DateUtil.format(tournament.getEndDate(), context.getString(R.string.date_format));

    holder.mNameTextView.setText(tournament.getName());
    holder.mTitleTextView.setText(tournament.getTile());
    holder.mNameTextView.setText(tournament.getName());
    holder.mStartDateTextView.setText(startDate);
    holder.mEndDateTextView.setText(endDate);

    Picasso.with(context)
        .load(context.getString(R.string.url_country_flag, tournament.getCountry()))
        .into(holder.mCountryFlag);

  }

  @Override
  public int getItemCount() {
    return mTournaments == null ? 0 : mTournaments.size();
  }

  public void setTournaments(List<BeachTournament> tournaments) {
    mTournaments = tournaments;
    notifyDataSetChanged();
  }

  public class TournamentsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.title)
    TextView mTitleTextView;

    @BindView(R.id.name)
    TextView mNameTextView;

    @BindView(R.id.from_date)
    TextView mStartDateTextView;

    @BindView(R.id.to_date)
    TextView mEndDateTextView;

    @BindView(R.id.country)
    ImageView mCountryFlag;

    public TournamentsAdapterViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      int position = getAdapterPosition();
      mClickListener.onClick(mTournaments.get(position));
    }
  }
}
