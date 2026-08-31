package com.studyflix.android.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.QuizDao;
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

  public QuizRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<QuizDao> quizDaoProvider) {
    this.firestoreProvider = firestoreProvider;
    this.quizDaoProvider = quizDaoProvider;
  }

  @Override
  public QuizRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), quizDaoProvider.get());
  }

  public static QuizRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<QuizDao> quizDaoProvider) {
    return new QuizRepositoryImpl_Factory(firestoreProvider, quizDaoProvider);
  }

  public static QuizRepositoryImpl newInstance(FirebaseFirestore firestore, QuizDao quizDao) {
    return new QuizRepositoryImpl(firestore, quizDao);
  }
}
