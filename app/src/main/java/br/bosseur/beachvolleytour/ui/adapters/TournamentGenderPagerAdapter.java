package br.bosseur.beachvolleytour.ui.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.bosseur.beachvolleytour.ui.fragments.RoundsFragment;

public class TournamentGenderPagerAdapter extends FragmentPagerAdapter {
  private final List<RoundsFragment> mFragmentList = new ArrayList<>();
  private final List<String> mFragmentTitleList = new ArrayList<>();

  public TournamentGenderPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    return mFragmentList.get(position);
  }
  public void addFragment(RoundsFragment roundsFragment, String title) {
    mFragmentList.add(roundsFragment);
    mFragmentTitleList.add(title);
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return mFragmentList.size();
  }

  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return mFragmentTitleList.get(position);
  }

  @Override
  public void setPrimaryItem(ViewGroup container, int position, Object object) {
    super.setPrimaryItem(container, position, object);
    mFragmentList.get(position);
  }

  public void reset() {
    mFragmentList.clear();
    mFragmentTitleList.clear();
    notifyDataSetChanged();
  }
}
