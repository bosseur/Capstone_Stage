package br.bosseur.beachvolleytour.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.OnItemClickListener;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.ui.fragments.BeachTournamentsFragment;
import br.bosseur.beachvolleytour.ui.fragments.MatchesFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BeachTournamentsActivity extends AppCompatActivity implements OnItemClickListener<BeachTournament> {

  public static final String BEACH_TOURNAMENT_KEY = "BEACH_TOURNAMENT_KEY";
  private static final String FRAGMENT_LIST_TAG = BeachTournamentsFragment.class.getSimpleName();
  private static final String FRAGMENT_MATCHES_TAG = MatchesFragment.class.getSimpleName();

  private FirebaseAnalytics mFirebaseAnalytics;

  @BindView(R.id.toolbar)
  Toolbar mToolbar;

  @BindView(R.id.adView)
  AdView mAdView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_beach_tournaments);
    ButterKnife.bind(this);

    mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    mToolbar.setTitle(getString(R.string.app_name));
    setSupportActionBar(mToolbar);

    MobileAds.initialize(this, getString(R.string.banner_ad_unit_id_test));
    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(getString(R.string.test_device))
        .build();
    mAdView.loadAd(adRequest);

    FragmentManager fragmentManager = getSupportFragmentManager();

    // When coming from the widget show the matches from the tournament.
    Intent intent = getIntent();
    int containerId = R.id.fragment_list;
    if(findViewById(R.id.fragment_matches) != null){
      containerId = R.id.fragment_matches;
      showTournamentsFragment(fragmentManager);
    }
    if (intent != null && intent.hasExtra(BEACH_TOURNAMENT_KEY)) {
      BeachTournament beachTournament = intent.getParcelableExtra(BEACH_TOURNAMENT_KEY);
      MatchesFragment matchesFragment = createMatchesFragment(beachTournament);
      fragmentManager.beginTransaction()
          .replace(containerId, matchesFragment, FRAGMENT_MATCHES_TAG)
          .commit();
      return;
    }

    showTournamentsFragment(fragmentManager);

  }

  @Override
  public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  @Override
  public void onClick(BeachTournament beachTournament) {
    MatchesFragment matchesFragment = createMatchesFragment(beachTournament);
    swapFragment(matchesFragment, FRAGMENT_MATCHES_TAG);
  }

  @NonNull
  public MatchesFragment createMatchesFragment(BeachTournament beachTournament) {
    MatchesFragment matchesFragment = new MatchesFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(BEACH_TOURNAMENT_KEY, beachTournament);
    matchesFragment.setArguments(bundle);
    return matchesFragment;
  }


  private void showTournamentsFragment(FragmentManager fragmentManager) {
    BeachTournamentsFragment detailsFragment = (BeachTournamentsFragment) fragmentManager.findFragmentByTag(FRAGMENT_LIST_TAG);
    if (detailsFragment == null) {
      detailsFragment = new BeachTournamentsFragment();
      fragmentManager.beginTransaction()
          .replace(R.id.fragment_list, detailsFragment, FRAGMENT_LIST_TAG)
          .commit();
    }
  }

  private void swapFragment(Fragment fragment, String fragmentTag) {
    int containerId = R.id.fragment_list;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    if(findViewById(R.id.fragment_matches) != null){
      containerId = R.id.fragment_matches;
    } else {
      transaction.addToBackStack(fragmentTag);
    }
    transaction.replace(containerId, fragment, fragmentTag)
        .commit();
  }
}
