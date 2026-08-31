package com.studyflix.android.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.studyflix.android.data.local.entity.StringListConverter;
import com.studyflix.android.data.local.entity.StudentEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudentEntity> __insertionAdapterOfStudentEntity;

  private final StringListConverter __stringListConverter = new StringListConverter();

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudentEntity = new EntityInsertionAdapter<StudentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `students` (`uid`,`email`,`name`,`subscription`,`trialEnds`,`grade`,`school`,`schoolId`,`status`,`completedQuizzes`,`createdAtMillis`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudentEntity entity) {
        statement.bindString(1, entity.getUid());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getSubscription());
        statement.bindString(5, entity.getTrialEnds());
        statement.bindString(6, entity.getGrade());
        statement.bindString(7, entity.getSchool());
        statement.bindString(8, entity.getSchoolId());
        statement.bindString(9, entity.getStatus());
        final String _tmp = __stringListConverter.fromList(entity.getCompletedQuizzes());
        statement.bindString(10, _tmp);
        if (entity.getCreatedAtMillis() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getCreatedAtMillis());
        }
      }
    };
  }

  @Override
  public Object upsert(final StudentEntity student, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStudentEntity.insert(student);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<StudentEntity> observe(final String uid) {
    final String _sql = "SELECT * FROM students WHERE uid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uid);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"students"}, new Callable<StudentEntity>() {
      @Override
      @Nullable
      public StudentEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSubscription = CursorUtil.getColumnIndexOrThrow(_cursor, "subscription");
          final int _cursorIndexOfTrialEnds = CursorUtil.getColumnIndexOrThrow(_cursor, "trialEnds");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfSchool = CursorUtil.getColumnIndexOrThrow(_cursor, "school");
          final int _cursorIndexOfSchoolId = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolId");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCompletedQuizzes = CursorUtil.getColumnIndexOrThrow(_cursor, "completedQuizzes");
          final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMillis");
          final StudentEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUid;
            _tmpUid = _cursor.getString(_cursorIndexOfUid);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpSubscription;
            _tmpSubscription = _cursor.getString(_cursorIndexOfSubscription);
            final String _tmpTrialEnds;
            _tmpTrialEnds = _cursor.getString(_cursorIndexOfTrialEnds);
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            final String _tmpSchool;
            _tmpSchool = _cursor.getString(_cursorIndexOfSchool);
            final String _tmpSchoolId;
            _tmpSchoolId = _cursor.getString(_cursorIndexOfSchoolId);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final List<String> _tmpCompletedQuizzes;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfCompletedQuizzes);
            _tmpCompletedQuizzes = __stringListConverter.toList(_tmp);
            final Long _tmpCreatedAtMillis;
            if (_cursor.isNull(_cursorIndexOfCreatedAtMillis)) {
              _tmpCreatedAtMillis = null;
            } else {
              _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
            }
            _result = new StudentEntity(_tmpUid,_tmpEmail,_tmpName,_tmpSubscription,_tmpTrialEnds,_tmpGrade,_tmpSchool,_tmpSchoolId,_tmpStatus,_tmpCompletedQuizzes,_tmpCreatedAtMillis);
          } else {
            _result = null;
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
