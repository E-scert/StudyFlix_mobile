package com.studyflix.android.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.studyflix.android.data.local.entity.QuestionDto;
import com.studyflix.android.data.local.entity.QuestionListConverter;
import com.studyflix.android.data.local.entity.QuizEntity;
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
public final class QuizDao_Impl implements QuizDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuizEntity> __insertionAdapterOfQuizEntity;

  private final QuestionListConverter __questionListConverter = new QuestionListConverter();

  private final SharedSQLiteStatement __preparedStmtOfClear;

  public QuizDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuizEntity = new EntityInsertionAdapter<QuizEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `quizzes` (`id`,`title`,`description`,`subject`,`grade`,`questions`,`totalMarks`,`timeLimitMinutes`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuizEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getSubject());
        statement.bindString(5, entity.getGrade());
        final String _tmp = __questionListConverter.fromList(entity.getQuestions());
        statement.bindString(6, _tmp);
        statement.bindLong(7, entity.getTotalMarks());
        statement.bindLong(8, entity.getTimeLimitMinutes());
      }
    };
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM quizzes";
        return _query;
      }
    };
  }

  @Override
  public Object upsertAll(final List<QuizEntity> quizzes,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfQuizEntity.insert(quizzes);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clear(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClear.acquire();
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
          __preparedStmtOfClear.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QuizEntity>> observeAll() {
    final String _sql = "SELECT * FROM quizzes";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quizzes"}, new Callable<List<QuizEntity>>() {
      @Override
      @NonNull
      public List<QuizEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
          final int _cursorIndexOfTotalMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMarks");
          final int _cursorIndexOfTimeLimitMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimitMinutes");
          final List<QuizEntity> _result = new ArrayList<QuizEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuizEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            final List<QuestionDto> _tmpQuestions;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfQuestions);
            _tmpQuestions = __questionListConverter.toList(_tmp);
            final int _tmpTotalMarks;
            _tmpTotalMarks = _cursor.getInt(_cursorIndexOfTotalMarks);
            final int _tmpTimeLimitMinutes;
            _tmpTimeLimitMinutes = _cursor.getInt(_cursorIndexOfTimeLimitMinutes);
            _item = new QuizEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpGrade,_tmpQuestions,_tmpTotalMarks,_tmpTimeLimitMinutes);
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
  public Object getById(final String id, final Continuation<? super QuizEntity> $completion) {
    final String _sql = "SELECT * FROM quizzes WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QuizEntity>() {
      @Override
      @Nullable
      public QuizEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfQuestions = CursorUtil.getColumnIndexOrThrow(_cursor, "questions");
          final int _cursorIndexOfTotalMarks = CursorUtil.getColumnIndexOrThrow(_cursor, "totalMarks");
          final int _cursorIndexOfTimeLimitMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "timeLimitMinutes");
          final QuizEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            final List<QuestionDto> _tmpQuestions;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfQuestions);
            _tmpQuestions = __questionListConverter.toList(_tmp);
            final int _tmpTotalMarks;
            _tmpTotalMarks = _cursor.getInt(_cursorIndexOfTotalMarks);
            final int _tmpTimeLimitMinutes;
            _tmpTimeLimitMinutes = _cursor.getInt(_cursorIndexOfTimeLimitMinutes);
            _result = new QuizEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpSubject,_tmpGrade,_tmpQuestions,_tmpTotalMarks,_tmpTimeLimitMinutes);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
