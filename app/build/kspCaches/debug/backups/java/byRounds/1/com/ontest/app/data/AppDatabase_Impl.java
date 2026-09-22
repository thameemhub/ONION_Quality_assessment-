package com.ontest.app.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.ontest.app.data.dao.GradingReportDao;
import com.ontest.app.data.dao.GradingReportDao_Impl;
import com.ontest.app.data.dao.WasteListingDao;
import com.ontest.app.data.dao.WasteListingDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile GradingReportDao _gradingReportDao;

  private volatile WasteListingDao _wasteListingDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `grading_reports` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `gradeAPercent` INTEGER NOT NULL, `gradeBPercent` INTEGER NOT NULL, `ursPercent` INTEGER NOT NULL, `rotRiskLevel` TEXT NOT NULL, `confidenceScore` INTEGER NOT NULL, `locationText` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `waste_listings` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `sourceReportId` INTEGER, `buyerName` TEXT NOT NULL, `buyerType` TEXT NOT NULL, `quantityKg` INTEGER NOT NULL, `distanceKm` REAL NOT NULL, `status` TEXT NOT NULL, `isUserListing` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'a6abe4ad0a14e6005ddb800f6736307e')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `grading_reports`");
        db.execSQL("DROP TABLE IF EXISTS `waste_listings`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsGradingReports = new HashMap<String, TableInfo.Column>(8);
        _columnsGradingReports.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("gradeAPercent", new TableInfo.Column("gradeAPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("gradeBPercent", new TableInfo.Column("gradeBPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("ursPercent", new TableInfo.Column("ursPercent", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("rotRiskLevel", new TableInfo.Column("rotRiskLevel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("confidenceScore", new TableInfo.Column("confidenceScore", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGradingReports.put("locationText", new TableInfo.Column("locationText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGradingReports = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGradingReports = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGradingReports = new TableInfo("grading_reports", _columnsGradingReports, _foreignKeysGradingReports, _indicesGradingReports);
        final TableInfo _existingGradingReports = TableInfo.read(db, "grading_reports");
        if (!_infoGradingReports.equals(_existingGradingReports)) {
          return new RoomOpenHelper.ValidationResult(false, "grading_reports(com.ontest.app.data.model.GradingReport).\n"
                  + " Expected:\n" + _infoGradingReports + "\n"
                  + " Found:\n" + _existingGradingReports);
        }
        final HashMap<String, TableInfo.Column> _columnsWasteListings = new HashMap<String, TableInfo.Column>(9);
        _columnsWasteListings.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("sourceReportId", new TableInfo.Column("sourceReportId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("buyerName", new TableInfo.Column("buyerName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("buyerType", new TableInfo.Column("buyerType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("quantityKg", new TableInfo.Column("quantityKg", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("distanceKm", new TableInfo.Column("distanceKm", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("isUserListing", new TableInfo.Column("isUserListing", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWasteListings.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWasteListings = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWasteListings = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWasteListings = new TableInfo("waste_listings", _columnsWasteListings, _foreignKeysWasteListings, _indicesWasteListings);
        final TableInfo _existingWasteListings = TableInfo.read(db, "waste_listings");
        if (!_infoWasteListings.equals(_existingWasteListings)) {
          return new RoomOpenHelper.ValidationResult(false, "waste_listings(com.ontest.app.data.model.WasteListing).\n"
                  + " Expected:\n" + _infoWasteListings + "\n"
                  + " Found:\n" + _existingWasteListings);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "a6abe4ad0a14e6005ddb800f6736307e", "fbc1dfa7d37082d92633c6ed27bcd55b");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "grading_reports","waste_listings");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `grading_reports`");
      _db.execSQL("DELETE FROM `waste_listings`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(GradingReportDao.class, GradingReportDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WasteListingDao.class, WasteListingDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public GradingReportDao gradingReportDao() {
    if (_gradingReportDao != null) {
      return _gradingReportDao;
    } else {
      synchronized(this) {
        if(_gradingReportDao == null) {
          _gradingReportDao = new GradingReportDao_Impl(this);
        }
        return _gradingReportDao;
      }
    }
  }

  @Override
  public WasteListingDao wasteListingDao() {
    if (_wasteListingDao != null) {
      return _wasteListingDao;
    } else {
      synchronized(this) {
        if(_wasteListingDao == null) {
          _wasteListingDao = new WasteListingDao_Impl(this);
        }
        return _wasteListingDao;
      }
    }
  }
}
