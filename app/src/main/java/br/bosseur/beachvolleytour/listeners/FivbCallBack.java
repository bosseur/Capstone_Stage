package br.bosseur.beachvolleytour.listeners;

public interface FivbCallBack<T> {

  void success(T t);

  void error(String message);

}
