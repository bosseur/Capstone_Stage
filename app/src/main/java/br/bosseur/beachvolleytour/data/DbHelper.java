/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.bosseur.beachvolleytour.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.bosseur.beachvolleytour.data.contracts.BeachMatchContract;
import br.bosseur.beachvolleytour.data.contracts.BeachRoundContract;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;

/**
 * Manages a local database for fivb data.
 */
public class DbHelper extends SQLiteOpenHelper {

  /*
   * This is the name of our database. Database names should be descriptive and end with the
   * .db extension.
   */
  public static final String DATABASE_NAME = "fivb.db";


  private static final int DATABASE_VERSION = 2;

  public DbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * Called when the database is created for the first time. This is where the creation of
   * tables and the initial population of the tables should happen.
   *
   * @param sqLiteDatabase The database.
   */
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {

    /*
     * After we've spelled out our SQLite table creation statement above, we actually execute
     * that SQL with the execSQL method of our SQLite database object.
     */
    sqLiteDatabase.execSQL(TournamentsContract.CREATE_TABLE_SQL);
    sqLiteDatabase.execSQL(BeachRoundContract.CREATE_TABLE_SQL);
    sqLiteDatabase.execSQL(BeachMatchContract.CREATE_TABLE_SQL);
  }

  /**
   * This database is only a cache for online data, so its upgrade policy is simply to discard
   * the data and call through to onCreate to recreate the table. Note that this only fires if
   * you change the version number for your database (in our case, DATABASE_VERSION). It does NOT
   * depend on the version number for your application found in your app/build.gradle file. If
   * you want to update the schema without wiping data, commenting out the current body of this
   * method should be your top priority before modifying this method.
   *
   * @param sqLiteDatabase Database that is being upgraded
   * @param oldVersion     The old database version
   * @param newVersion     The new database version
   */
  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TournamentsContract.TournamentsEntry.TABLE_NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BeachRoundContract.BeachRoundsEntry.TABLE_NAME);
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + BeachMatchContract.BeachMatchEntry.TABLE_NAME);
    onCreate(sqLiteDatabase);
  }
}