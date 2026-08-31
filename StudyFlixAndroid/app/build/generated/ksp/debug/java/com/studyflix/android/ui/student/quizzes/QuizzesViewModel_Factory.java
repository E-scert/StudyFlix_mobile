package com.studyflix.android.ui.student.quizzes;

import com.studyflix.android.domain.usecase.student.GetQuizzesUseCase;
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
public final class QuizzesViewModel_Factory implements Factory<QuizzesViewModel> {
  private final Provider<GetQuizzesUseCase> getQuizzesUseCaseProvider;

  public QuizzesViewModel_Factory(Provider<GetQuizzesUseCase> getQuizzesUseCaseProvider) {
    this.getQuizzesUseCaseProvider = getQuizzesUseCaseProvider;
  }

  @Override
  public QuizzesViewModel get() {
    return newInstance(getQuizzesUseCaseProvider.get());
  }

  public static QuizzesViewModel_Factory create(
      Provider<GetQuizzesUseCase> getQuizzesUseCaseProvider) {
    return new QuizzesViewModel_Factory(getQuizzesUseCaseProvider);
  }

  public static QuizzesViewModel newInstance(GetQuizzesUseCase getQuizzesUseCase) {
    return new QuizzesViewModel(getQuizzesUseCase);
  }
}
