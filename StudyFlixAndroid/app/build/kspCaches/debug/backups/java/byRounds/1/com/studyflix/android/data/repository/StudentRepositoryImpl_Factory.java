package com.studyflix.android.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.StudentDao;
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
public final class StudentRepositoryImpl_Factory implements Factory<StudentRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<StudentDao> studentDaoProvider;

  public StudentRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<StudentDao> studentDaoProvider) {
    this.firestoreProvider = firestoreProvider;
    this.studentDaoProvider = studentDaoProvider;
  }

  @Override
  public StudentRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), studentDaoProvider.get());
  }

  public static StudentRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<StudentDao> studentDaoProvider) {
    return new StudentRepositoryImpl_Factory(firestoreProvider, studentDaoProvider);
  }

  public static StudentRepositoryImpl newInstance(FirebaseFirestore firestore,
      StudentDao studentDao) {
    return new StudentRepositoryImpl(firestore, studentDao);
  }
}
