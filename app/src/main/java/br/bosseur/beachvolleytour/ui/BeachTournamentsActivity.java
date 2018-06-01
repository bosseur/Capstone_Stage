package br.bosseur.beachvolleytour.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.OnItemClickListener;
import br.bosseur.beachvolleytour.model.BeachTournament;

public class BeachTournamentsActivity extends AppCompatActivity implements OnItemClickListener<BeachTournament> {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beach_tournaments);
  }

  @Override
  public void onClick(BeachTournament beachTournament) {
    Toast.makeText(this, beachTournament.getName(), Toast.LENGTH_LONG).show();
  }
}
