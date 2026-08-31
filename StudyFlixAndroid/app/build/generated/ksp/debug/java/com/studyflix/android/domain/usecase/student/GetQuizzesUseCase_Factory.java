package com.studyflix.android.domain.usecase.student;

import com.studyflix.android.domain.repository.QuizRepository;
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
public final class GetQuizzesUseCase_Factory implements Factory<GetQuizzesUseCase> {
  private final Provider<QuizRepository> quizRepositoryProvider;

  public GetQuizzesUseCase_Factory(Provider<QuizRepository> quizRepositoryProvider) {
    this.quizRepositoryProvider = quizRepositoryProvider;
  }

  @Override
  public GetQuizzesUseCase get() {
    return newInstance(quizRepositoryProvider.get());
  }

  public static GetQuizzesUseCase_Factory create(Provider<QuizRepository> quizRepositoryProvider) {
    return new GetQuizzesUseCase_Factory(quizRepositoryProvider);
  }

  public static GetQuizzesUseCase newInstance(QuizRepository quizRepository) {
    return new GetQuizzesUseCase(quizRepository);
  }
}
