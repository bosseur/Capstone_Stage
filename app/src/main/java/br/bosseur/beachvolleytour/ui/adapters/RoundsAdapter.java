package br.bosseur.beachvolleytour.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.model.BeachRound;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoundsAdapter extends RecyclerView.Adapter<RoundsAdapter.RoundsAdapterViewHolder> {
  private final List<BeachRound> mRounds;

  public RoundsAdapter(List<BeachRound> rounds) {
    mRounds = rounds;
  }

  @NonNull
  @Override
  public RoundsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.layout_rounds, parent, false);
    return new RoundsAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RoundsAdapterViewHolder holder, int position) {
    BeachRound round = mRounds.get(position);

    holder.mRoundNameTextView.setText(round.getName());

    holder.mMachtList.setAdapter(new MatchAdapter(round.getMatches()));
    holder.mMachtList.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.VERTICAL, false));
  }

  @Override
  public int getItemCount() {
    return mRounds == null ? 0 : mRounds.size();
  }

  public static class RoundsAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.round_name)
    TextView mRoundNameTextView;

    @BindView(R.id.match_list)
    RecyclerView mMachtList;

    public RoundsAdapterViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

   }

}
