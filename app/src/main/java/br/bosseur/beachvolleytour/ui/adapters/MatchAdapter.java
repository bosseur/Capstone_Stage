package br.bosseur.beachvolleytour.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.model.TournamentMatch;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchAdapterViewHolder> {
  private ArrayList<TournamentMatch> mTournamentMatches;

  public MatchAdapter(ArrayList<TournamentMatch> matches) {
    mTournamentMatches = matches;
  }


  @NonNull
  @Override
  public MatchAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.layout_match, parent, false);
    return new MatchAdapterViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull MatchAdapterViewHolder holder, int position) {
    TournamentMatch match = mTournamentMatches.get(position);
    Context context = holder.itemView.getContext();

    if( position % 2 != 0) {
      holder.mLayout.setBackgroundColor(context.getResources().getColor(R.color.primaryColor));
    }

    holder.teamA.setText(match.getTeamA());
    holder.pointsSet1A.setText(match.getPointsTeamASet1());
    holder.pointsSet2A.setText(match.getPointsTeamASet2());
    holder.pointsSet3A.setText(match.getPointsTeamASet3());

    holder.teamB.setText(match.getTeamB());
    holder.pointsSet1B.setText(match.getPointsTeamBSet1());
    holder.pointsSet2B.setText(match.getPointsTeamBSet2());
    holder.pointsSet3B.setText(match.getPointsTeamBSet3());

  }

  @Override
  public int getItemCount() {
    return mTournamentMatches != null ? mTournamentMatches.size() : 0;
  }

  public class MatchAdapterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.match_layout)
    ConstraintLayout mLayout;
    @BindView(R.id.teamA)
    TextView teamA;
    @BindView(R.id.pointsTeamASet1)
    TextView pointsSet1A;
    @BindView(R.id.pointsTeamASet2)
    TextView pointsSet2A;
    @BindView(R.id.pointsTeamASet3)
    TextView pointsSet3A;
    @BindView(R.id.teamB)
    TextView teamB;
    @BindView(R.id.pointTeamBSet1)
    TextView pointsSet1B;
    @BindView(R.id.pointTeamBSet2)
    TextView pointsSet2B;
    @BindView(R.id.pointsTeamBSet3)
    TextView pointsSet3B;

    public MatchAdapterViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
