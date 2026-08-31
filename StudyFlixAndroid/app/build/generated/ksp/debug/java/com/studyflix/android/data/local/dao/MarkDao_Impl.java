package com.studyflix.android.data.local.dao;

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
import com.studyflix.android.data.local.entity.MarkEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class MarkDao_Impl implements MarkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MarkEntity> __insertionAdapterOfMarkEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearForStudent;

  public MarkDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMarkEntity = new EntityInsertionAdapter<MarkEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `marks` (`id`,`studentId`,`name`,`dateIso`,`score`,`total`,`percentage`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MarkEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getStudentId());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getDateIso());
        statement.bindLong(5, entity.getScore());
        statement.bindLong(6, entity.getTotal());
        statement.bindLong(7, entity.getPercentage());
      }
    };
    this.__preparedStmtOfClearForStudent = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM marks WHERE studentId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsertAll(final List<MarkEntity> marks,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMarkEntity.insert(marks);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearForStudent(final String studentId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearForStudent.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, studentId);
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
          __preparedStmtOfClearForStudent.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<MarkEntity>> observeForStudent(final String studentId) {
    final String _sql = "SELECT * FROM marks WHERE studentId = ? ORDER BY dateIso DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, studentId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"marks"}, new Callable<List<MarkEntity>>() {
      @Override
      @NonNull
      public List<MarkEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDateIso = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIso");
          final int _cursorIndexOfScore = CursorUtil.getColumnIndexOrThrow(_cursor, "score");
          final int _cursorIndexOfTotal = CursorUtil.getColumnIndexOrThrow(_cursor, "total");
          final int _cursorIndexOfPercentage = CursorUtil.getColumnIndexOrThrow(_cursor, "percentage");
          final List<MarkEntity> _result = new ArrayList<MarkEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MarkEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpStudentId;
            _tmpStudentId = _cursor.getString(_cursorIndexOfStudentId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDateIso;
            _tmpDateIso = _cursor.getString(_cursorIndexOfDateIso);
            final int _tmpScore;
            _tmpScore = _cursor.getInt(_cursorIndexOfScore);
            final int _tmpTotal;
            _tmpTotal = _cursor.getInt(_cursorIndexOfTotal);
            final int _tmpPercentage;
            _tmpPercentage = _cursor.getInt(_cursorIndexOfPercentage);
            _item = new MarkEntity(_tmpId,_tmpStudentId,_tmpName,_tmpDateIso,_tmpScore,_tmpTotal,_tmpPercentage);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
