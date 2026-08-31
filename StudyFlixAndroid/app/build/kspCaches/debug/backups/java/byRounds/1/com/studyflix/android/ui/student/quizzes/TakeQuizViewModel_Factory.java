package com.studyflix.android.ui.student.quizzes;

import androidx.lifecycle.SavedStateHandle;
import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.repository.QuizRepository;
import com.studyflix.android.domain.usecase.student.SubmitQuizUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class TakeQuizViewModel_Factory implements Factory<TakeQuizViewModel> {
  private final Provider<QuizRepository> quizRepositoryProvider;

  private final Provider<SubmitQuizUseCase> submitQuizUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public TakeQuizViewModel_Factory(Provider<QuizRepository> quizRepositoryProvider,
      Provider<SubmitQuizUseCase> submitQuizUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.quizRepositoryProvider = quizRepositoryProvider;
    this.submitQuizUseCaseProvider = submitQuizUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public TakeQuizViewModel get() {
    return newInstance(quizRepositoryProvider.get(), submitQuizUseCaseProvider.get(), firebaseAuthProvider.get(), savedStateHandleProvider.get());
  }

  public static TakeQuizViewModel_Factory create(Provider<QuizRepository> quizRepositoryProvider,
      Provider<SubmitQuizUseCase> submitQuizUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new TakeQuizViewModel_Factory(quizRepositoryProvider, submitQuizUseCaseProvider, firebaseAuthProvider, savedStateHandleProvider);
  }

  public static TakeQuizViewModel newInstance(QuizRepository quizRepository,
      SubmitQuizUseCase submitQuizUseCase, FirebaseAuth firebaseAuth,
      SavedStateHandle savedStateHandle) {
    return new TakeQuizViewModel(quizRepository, submitQuizUseCase, firebaseAuth, savedStateHandle);
  }
}
