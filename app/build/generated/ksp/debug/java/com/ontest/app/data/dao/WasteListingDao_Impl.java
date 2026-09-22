package com.ontest.app.data.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ontest.app.data.model.WasteListing;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WasteListingDao_Impl implements WasteListingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WasteListing> __insertionAdapterOfWasteListing;

  private final SharedSQLiteStatement __preparedStmtOfUpdateStatus;

  public WasteListingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWasteListing = new EntityInsertionAdapter<WasteListing>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `waste_listings` (`id`,`sourceReportId`,`buyerName`,`buyerType`,`quantityKg`,`distanceKm`,`status`,`isUserListing`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WasteListing entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getSourceReportId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getSourceReportId());
        }
        statement.bindString(3, entity.getBuyerName());
        statement.bindString(4, entity.getBuyerType());
        statement.bindLong(5, entity.getQuantityKg());
        statement.bindDouble(6, entity.getDistanceKm());
        statement.bindString(7, entity.getStatus());
        final int _tmp = entity.isUserListing() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfUpdateStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE waste_listings SET status = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final WasteListing listing, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWasteListing.insertAndReturnId(listing);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<WasteListing> listings,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWasteListing.insert(listings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateStatus(final long id, final String status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<WasteListing>> getAllListings() {
    final String _sql = "SELECT * FROM waste_listings ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"waste_listings"}, new Callable<List<WasteListing>>() {
      @Override
      @NonNull
      public List<WasteListing> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSourceReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceReportId");
          final int _cursorIndexOfBuyerName = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerName");
          final int _cursorIndexOfBuyerType = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerType");
          final int _cursorIndexOfQuantityKg = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityKg");
          final int _cursorIndexOfDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceKm");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsUserListing = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserListing");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<WasteListing> _result = new ArrayList<WasteListing>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WasteListing _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpSourceReportId;
            if (_cursor.isNull(_cursorIndexOfSourceReportId)) {
              _tmpSourceReportId = null;
            } else {
              _tmpSourceReportId = _cursor.getLong(_cursorIndexOfSourceReportId);
            }
            final String _tmpBuyerName;
            _tmpBuyerName = _cursor.getString(_cursorIndexOfBuyerName);
            final String _tmpBuyerType;
            _tmpBuyerType = _cursor.getString(_cursorIndexOfBuyerType);
            final int _tmpQuantityKg;
            _tmpQuantityKg = _cursor.getInt(_cursorIndexOfQuantityKg);
            final float _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getFloat(_cursorIndexOfDistanceKm);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpIsUserListing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserListing);
            _tmpIsUserListing = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new WasteListing(_tmpId,_tmpSourceReportId,_tmpBuyerName,_tmpBuyerType,_tmpQuantityKg,_tmpDistanceKm,_tmpStatus,_tmpIsUserListing,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<WasteListing>> getByType(final String type) {
    final String _sql = "SELECT * FROM waste_listings WHERE buyerType = ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"waste_listings"}, new Callable<List<WasteListing>>() {
      @Override
      @NonNull
      public List<WasteListing> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSourceReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceReportId");
          final int _cursorIndexOfBuyerName = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerName");
          final int _cursorIndexOfBuyerType = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerType");
          final int _cursorIndexOfQuantityKg = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityKg");
          final int _cursorIndexOfDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceKm");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsUserListing = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserListing");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<WasteListing> _result = new ArrayList<WasteListing>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WasteListing _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpSourceReportId;
            if (_cursor.isNull(_cursorIndexOfSourceReportId)) {
              _tmpSourceReportId = null;
            } else {
              _tmpSourceReportId = _cursor.getLong(_cursorIndexOfSourceReportId);
            }
            final String _tmpBuyerName;
            _tmpBuyerName = _cursor.getString(_cursorIndexOfBuyerName);
            final String _tmpBuyerType;
            _tmpBuyerType = _cursor.getString(_cursorIndexOfBuyerType);
            final int _tmpQuantityKg;
            _tmpQuantityKg = _cursor.getInt(_cursorIndexOfQuantityKg);
            final float _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getFloat(_cursorIndexOfDistanceKm);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpIsUserListing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserListing);
            _tmpIsUserListing = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new WasteListing(_tmpId,_tmpSourceReportId,_tmpBuyerName,_tmpBuyerType,_tmpQuantityKg,_tmpDistanceKm,_tmpStatus,_tmpIsUserListing,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<WasteListing>> getUserListings() {
    final String _sql = "SELECT * FROM waste_listings WHERE isUserListing = 1 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"waste_listings"}, new Callable<List<WasteListing>>() {
      @Override
      @NonNull
      public List<WasteListing> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSourceReportId = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceReportId");
          final int _cursorIndexOfBuyerName = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerName");
          final int _cursorIndexOfBuyerType = CursorUtil.getColumnIndexOrThrow(_cursor, "buyerType");
          final int _cursorIndexOfQuantityKg = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityKg");
          final int _cursorIndexOfDistanceKm = CursorUtil.getColumnIndexOrThrow(_cursor, "distanceKm");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfIsUserListing = CursorUtil.getColumnIndexOrThrow(_cursor, "isUserListing");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<WasteListing> _result = new ArrayList<WasteListing>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final WasteListing _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpSourceReportId;
            if (_cursor.isNull(_cursorIndexOfSourceReportId)) {
              _tmpSourceReportId = null;
            } else {
              _tmpSourceReportId = _cursor.getLong(_cursorIndexOfSourceReportId);
            }
            final String _tmpBuyerName;
            _tmpBuyerName = _cursor.getString(_cursorIndexOfBuyerName);
            final String _tmpBuyerType;
            _tmpBuyerType = _cursor.getString(_cursorIndexOfBuyerType);
            final int _tmpQuantityKg;
            _tmpQuantityKg = _cursor.getInt(_cursorIndexOfQuantityKg);
            final float _tmpDistanceKm;
            _tmpDistanceKm = _cursor.getFloat(_cursorIndexOfDistanceKm);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final boolean _tmpIsUserListing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUserListing);
            _tmpIsUserListing = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new WasteListing(_tmpId,_tmpSourceReportId,_tmpBuyerName,_tmpBuyerType,_tmpQuantityKg,_tmpDistanceKm,_tmpStatus,_tmpIsUserListing,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getUserListingCount() {
    final String _sql = "SELECT COUNT(*) FROM waste_listings WHERE isUserListing = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"waste_listings"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getTotalCount() {
    final String _sql = "SELECT COUNT(*) FROM waste_listings";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"waste_listings"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
