package com.studyflix.android.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.studyflix.android.data.local.entity.StringListConverter;
import com.studyflix.android.data.local.entity.TeacherEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class TeacherDao_Impl implements TeacherDao {
  private final RoomDatabase __db;

  private final SharedSQLiteStatement __preparedStmtOfClear;

  private final EntityUpsertionAdapter<TeacherEntity> __upsertionAdapterOfTeacherEntity;

  private final StringListConverter __stringListConverter = new StringListConverter();

  public TeacherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__preparedStmtOfClear = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM teachers";
        return _query;
      }
    };
    this.__upsertionAdapterOfTeacherEntity = new EntityUpsertionAdapter<TeacherEntity>(new EntityInsertionAdapter<TeacherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `teachers` (`uid`,`email`,`name`,`phone`,`role`,`schoolId`,`schoolName`,`schoolCode`,`grade`,`selectedGrade`,`subject`,`selectedSubject`,`grades`,`subjects`,`subscription`,`status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TeacherEntity entity) {
        statement.bindString(1, entity.getUid());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getPhone());
        statement.bindString(5, entity.getRole());
        statement.bindString(6, entity.getSchoolId());
        statement.bindString(7, entity.getSchoolName());
        statement.bindString(8, entity.getSchoolCode());
        statement.bindString(9, entity.getGrade());
        statement.bindString(10, entity.getSelectedGrade());
        statement.bindString(11, entity.getSubject());
        statement.bindString(12, entity.getSelectedSubject());
        final String _tmp = __stringListConverter.fromList(entity.getGrades());
        statement.bindString(13, _tmp);
        final String _tmp_1 = __stringListConverter.fromList(entity.getSubjects());
        statement.bindString(14, _tmp_1);
        statement.bindString(15, entity.getSubscription());
        statement.bindString(16, entity.getStatus());
      }
    }, new EntityDeletionOrUpdateAdapter<TeacherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `teachers` SET `uid` = ?,`email` = ?,`name` = ?,`phone` = ?,`role` = ?,`schoolId` = ?,`schoolName` = ?,`schoolCode` = ?,`grade` = ?,`selectedGrade` = ?,`subject` = ?,`selectedSubject` = ?,`grades` = ?,`subjects` = ?,`subscription` = ?,`status` = ? WHERE `uid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TeacherEntity entity) {
        statement.bindString(1, entity.getUid());
        statement.bindString(2, entity.getEmail());
        statement.bindString(3, entity.getName());
        statement.bindString(4, entity.getPhone());
        statement.bindString(5, entity.getRole());
        statement.bindString(6, entity.getSchoolId());
        statement.bindString(7, entity.getSchoolName());
        statement.bindString(8, entity.getSchoolCode());
        statement.bindString(9, entity.getGrade());
        statement.bindString(10, entity.getSelectedGrade());
        statement.bindString(11, entity.getSubject());
        statement.bindString(12, entity.getSelectedSubject());
        final String _tmp = __stringListConverter.fromList(entity.getGrades());
        statement.bindString(13, _tmp);
        final String _tmp_1 = __stringListConverter.fromList(entity.getSubjects());
        statement.bindString(14, _tmp_1);
        statement.bindString(15, entity.getSubscription());
        statement.bindString(16, entity.getStatus());
        statement.bindString(17, entity.getUid());
      }
    });
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
  public Object upsert(final TeacherEntity teacher, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfTeacherEntity.upsert(teacher);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<TeacherEntity> observe(final String uid) {
    final String _sql = "SELECT * FROM teachers WHERE uid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uid);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"teachers"}, new Callable<TeacherEntity>() {
      @Override
      @Nullable
      public TeacherEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfSchoolId = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolId");
          final int _cursorIndexOfSchoolName = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolName");
          final int _cursorIndexOfSchoolCode = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolCode");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfSelectedGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedGrade");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfSelectedSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedSubject");
          final int _cursorIndexOfGrades = CursorUtil.getColumnIndexOrThrow(_cursor, "grades");
          final int _cursorIndexOfSubjects = CursorUtil.getColumnIndexOrThrow(_cursor, "subjects");
          final int _cursorIndexOfSubscription = CursorUtil.getColumnIndexOrThrow(_cursor, "subscription");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final TeacherEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUid;
            _tmpUid = _cursor.getString(_cursorIndexOfUid);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpSchoolId;
            _tmpSchoolId = _cursor.getString(_cursorIndexOfSchoolId);
            final String _tmpSchoolName;
            _tmpSchoolName = _cursor.getString(_cursorIndexOfSchoolName);
            final String _tmpSchoolCode;
            _tmpSchoolCode = _cursor.getString(_cursorIndexOfSchoolCode);
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            final String _tmpSelectedGrade;
            _tmpSelectedGrade = _cursor.getString(_cursorIndexOfSelectedGrade);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final String _tmpSelectedSubject;
            _tmpSelectedSubject = _cursor.getString(_cursorIndexOfSelectedSubject);
            final List<String> _tmpGrades;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGrades);
            _tmpGrades = __stringListConverter.toList(_tmp);
            final List<String> _tmpSubjects;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSubjects);
            _tmpSubjects = __stringListConverter.toList(_tmp_1);
            final String _tmpSubscription;
            _tmpSubscription = _cursor.getString(_cursorIndexOfSubscription);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _result = new TeacherEntity(_tmpUid,_tmpEmail,_tmpName,_tmpPhone,_tmpRole,_tmpSchoolId,_tmpSchoolName,_tmpSchoolCode,_tmpGrade,_tmpSelectedGrade,_tmpSubject,_tmpSelectedSubject,_tmpGrades,_tmpSubjects,_tmpSubscription,_tmpStatus);
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

  @Override
  public Object get(final String uid, final Continuation<? super TeacherEntity> $completion) {
    final String _sql = "SELECT * FROM teachers WHERE uid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, uid);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TeacherEntity>() {
      @Override
      @Nullable
      public TeacherEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
          final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
          final int _cursorIndexOfSchoolId = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolId");
          final int _cursorIndexOfSchoolName = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolName");
          final int _cursorIndexOfSchoolCode = CursorUtil.getColumnIndexOrThrow(_cursor, "schoolCode");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfSelectedGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedGrade");
          final int _cursorIndexOfSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "subject");
          final int _cursorIndexOfSelectedSubject = CursorUtil.getColumnIndexOrThrow(_cursor, "selectedSubject");
          final int _cursorIndexOfGrades = CursorUtil.getColumnIndexOrThrow(_cursor, "grades");
          final int _cursorIndexOfSubjects = CursorUtil.getColumnIndexOrThrow(_cursor, "subjects");
          final int _cursorIndexOfSubscription = CursorUtil.getColumnIndexOrThrow(_cursor, "subscription");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final TeacherEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUid;
            _tmpUid = _cursor.getString(_cursorIndexOfUid);
            final String _tmpEmail;
            _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhone;
            _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
            final String _tmpRole;
            _tmpRole = _cursor.getString(_cursorIndexOfRole);
            final String _tmpSchoolId;
            _tmpSchoolId = _cursor.getString(_cursorIndexOfSchoolId);
            final String _tmpSchoolName;
            _tmpSchoolName = _cursor.getString(_cursorIndexOfSchoolName);
            final String _tmpSchoolCode;
            _tmpSchoolCode = _cursor.getString(_cursorIndexOfSchoolCode);
            final String _tmpGrade;
            _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            final String _tmpSelectedGrade;
            _tmpSelectedGrade = _cursor.getString(_cursorIndexOfSelectedGrade);
            final String _tmpSubject;
            _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
            final String _tmpSelectedSubject;
            _tmpSelectedSubject = _cursor.getString(_cursorIndexOfSelectedSubject);
            final List<String> _tmpGrades;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfGrades);
            _tmpGrades = __stringListConverter.toList(_tmp);
            final List<String> _tmpSubjects;
            final String _tmp_1;
            _tmp_1 = _cursor.getString(_cursorIndexOfSubjects);
            _tmpSubjects = __stringListConverter.toList(_tmp_1);
            final String _tmpSubscription;
            _tmpSubscription = _cursor.getString(_cursorIndexOfSubscription);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            _result = new TeacherEntity(_tmpUid,_tmpEmail,_tmpName,_tmpPhone,_tmpRole,_tmpSchoolId,_tmpSchoolName,_tmpSchoolCode,_tmpGrade,_tmpSelectedGrade,_tmpSubject,_tmpSelectedSubject,_tmpGrades,_tmpSubjects,_tmpSubscription,_tmpStatus);
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
