package com.studyflix.android.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.TeacherDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class TeacherRepositoryImpl_Factory implements Factory<TeacherRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<TeacherDao> teacherDaoProvider;

  public TeacherRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<TeacherDao> teacherDaoProvider) {
    this.firestoreProvider = firestoreProvider;
    this.teacherDaoProvider = teacherDaoProvider;
  }

  @Override
  public TeacherRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), teacherDaoProvider.get());
  }

  public static TeacherRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<TeacherDao> teacherDaoProvider) {
    return new TeacherRepositoryImpl_Factory(firestoreProvider, teacherDaoProvider);
  }

  public static TeacherRepositoryImpl newInstance(FirebaseFirestore firestore,
      TeacherDao teacherDao) {
    return new TeacherRepositoryImpl(firestore, teacherDao);
  }
}
