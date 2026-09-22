package com.ontest.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ontest.app.data.model.GradingReport;
import java.lang.Class;
import java.lang.Double;
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
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class GradingReportDao_Impl implements GradingReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GradingReport> __insertionAdapterOfGradingReport;

  public GradingReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGradingReport = new EntityInsertionAdapter<GradingReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `grading_reports` (`id`,`timestamp`,`gradeAPercent`,`gradeBPercent`,`ursPercent`,`rotRiskLevel`,`confidenceScore`,`locationText`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GradingReport entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        statement.bindLong(3, entity.getGradeAPercent());
        statement.bindLong(4, entity.getGradeBPercent());
        statement.bindLong(5, entity.getUrsPercent());
        statement.bindString(6, entity.getRotRiskLevel());
        statement.bindLong(7, entity.getConfidenceScore());
        statement.bindString(8, entity.getLocationText());
      }
    };
  }

  @Override
  public Object insert(final GradingReport report, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfGradingReport.insertAndReturnId(report);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<GradingReport>> getAllReports() {
    final String _sql = "SELECT * FROM grading_reports ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"grading_reports"}, new Callable<List<GradingReport>>() {
      @Override
      @NonNull
      public List<GradingReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfGradeAPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "gradeAPercent");
          final int _cursorIndexOfGradeBPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "gradeBPercent");
          final int _cursorIndexOfUrsPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "ursPercent");
          final int _cursorIndexOfRotRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "rotRiskLevel");
          final int _cursorIndexOfConfidenceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "confidenceScore");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final List<GradingReport> _result = new ArrayList<GradingReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GradingReport _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpGradeAPercent;
            _tmpGradeAPercent = _cursor.getInt(_cursorIndexOfGradeAPercent);
            final int _tmpGradeBPercent;
            _tmpGradeBPercent = _cursor.getInt(_cursorIndexOfGradeBPercent);
            final int _tmpUrsPercent;
            _tmpUrsPercent = _cursor.getInt(_cursorIndexOfUrsPercent);
            final String _tmpRotRiskLevel;
            _tmpRotRiskLevel = _cursor.getString(_cursorIndexOfRotRiskLevel);
            final int _tmpConfidenceScore;
            _tmpConfidenceScore = _cursor.getInt(_cursorIndexOfConfidenceScore);
            final String _tmpLocationText;
            _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            _item = new GradingReport(_tmpId,_tmpTimestamp,_tmpGradeAPercent,_tmpGradeBPercent,_tmpUrsPercent,_tmpRotRiskLevel,_tmpConfidenceScore,_tmpLocationText);
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
  public Flow<List<GradingReport>> getRecentReports() {
    final String _sql = "SELECT * FROM grading_reports ORDER BY timestamp DESC LIMIT 3";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"grading_reports"}, new Callable<List<GradingReport>>() {
      @Override
      @NonNull
      public List<GradingReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfGradeAPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "gradeAPercent");
          final int _cursorIndexOfGradeBPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "gradeBPercent");
          final int _cursorIndexOfUrsPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "ursPercent");
          final int _cursorIndexOfRotRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "rotRiskLevel");
          final int _cursorIndexOfConfidenceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "confidenceScore");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final List<GradingReport> _result = new ArrayList<GradingReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GradingReport _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpGradeAPercent;
            _tmpGradeAPercent = _cursor.getInt(_cursorIndexOfGradeAPercent);
            final int _tmpGradeBPercent;
            _tmpGradeBPercent = _cursor.getInt(_cursorIndexOfGradeBPercent);
            final int _tmpUrsPercent;
            _tmpUrsPercent = _cursor.getInt(_cursorIndexOfUrsPercent);
            final String _tmpRotRiskLevel;
            _tmpRotRiskLevel = _cursor.getString(_cursorIndexOfRotRiskLevel);
            final int _tmpConfidenceScore;
            _tmpConfidenceScore = _cursor.getInt(_cursorIndexOfConfidenceScore);
            final String _tmpLocationText;
            _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            _item = new GradingReport(_tmpId,_tmpTimestamp,_tmpGradeAPercent,_tmpGradeBPercent,_tmpUrsPercent,_tmpRotRiskLevel,_tmpConfidenceScore,_tmpLocationText);
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
  public Object getById(final long id, final Continuation<? super GradingReport> $completion) {
    final String _sql = "SELECT * FROM grading_reports WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<GradingReport>() {
      @Override
      @Nullable
      public GradingReport call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfGradeAPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "gradeAPercent");
          final int _cursorIndexOfGradeBPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "gradeBPercent");
          final int _cursorIndexOfUrsPercent = CursorUtil.getColumnIndexOrThrow(_cursor, "ursPercent");
          final int _cursorIndexOfRotRiskLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "rotRiskLevel");
          final int _cursorIndexOfConfidenceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "confidenceScore");
          final int _cursorIndexOfLocationText = CursorUtil.getColumnIndexOrThrow(_cursor, "locationText");
          final GradingReport _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpGradeAPercent;
            _tmpGradeAPercent = _cursor.getInt(_cursorIndexOfGradeAPercent);
            final int _tmpGradeBPercent;
            _tmpGradeBPercent = _cursor.getInt(_cursorIndexOfGradeBPercent);
            final int _tmpUrsPercent;
            _tmpUrsPercent = _cursor.getInt(_cursorIndexOfUrsPercent);
            final String _tmpRotRiskLevel;
            _tmpRotRiskLevel = _cursor.getString(_cursorIndexOfRotRiskLevel);
            final int _tmpConfidenceScore;
            _tmpConfidenceScore = _cursor.getInt(_cursorIndexOfConfidenceScore);
            final String _tmpLocationText;
            _tmpLocationText = _cursor.getString(_cursorIndexOfLocationText);
            _result = new GradingReport(_tmpId,_tmpTimestamp,_tmpGradeAPercent,_tmpGradeBPercent,_tmpUrsPercent,_tmpRotRiskLevel,_tmpConfidenceScore,_tmpLocationText);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> getCount() {
    final String _sql = "SELECT COUNT(*) FROM grading_reports";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"grading_reports"}, new Callable<Integer>() {
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
  public Flow<Double> getAverageGradeA() {
    final String _sql = "SELECT COALESCE(AVG(gradeAPercent), 0) FROM grading_reports";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"grading_reports"}, new Callable<Double>() {
      @Override
      @NonNull
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final double _tmp;
            _tmp = _cursor.getDouble(0);
            _result = _tmp;
          } else {
            _result = 0.0;
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
