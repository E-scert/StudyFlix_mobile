package com.studyflix.android.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.QuizDao;
import com.studyflix.android.domain.repository.StudentRepository;
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
public final class QuizRepositoryImpl_Factory implements Factory<QuizRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<QuizDao> quizDaoProvider;

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<StudentRepository> studentRepositoryProvider;

  public QuizRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<QuizDao> quizDaoProvider, Provider<FirebaseAuth> authProvider,
      Provider<StudentRepository> studentRepositoryProvider) {
    this.firestoreProvider = firestoreProvider;
    this.quizDaoProvider = quizDaoProvider;
    this.authProvider = authProvider;
    this.studentRepositoryProvider = studentRepositoryProvider;
  }

  @Override
  public QuizRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), quizDaoProvider.get(), authProvider.get(), studentRepositoryProvider.get());
  }

  public static QuizRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<QuizDao> quizDaoProvider, Provider<FirebaseAuth> authProvider,
      Provider<StudentRepository> studentRepositoryProvider) {
    return new QuizRepositoryImpl_Factory(firestoreProvider, quizDaoProvider, authProvider, studentRepositoryProvider);
  }

  public static QuizRepositoryImpl newInstance(FirebaseFirestore firestore, QuizDao quizDao,
      FirebaseAuth auth, StudentRepository studentRepository) {
    return new QuizRepositoryImpl(firestore, quizDao, auth, studentRepository);
  }
}
