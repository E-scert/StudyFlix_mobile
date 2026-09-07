package com.studyflix.android.ui.student.videos;

import com.studyflix.android.domain.usecase.student.CheckFeatureAccessUseCase;
import com.studyflix.android.domain.usecase.student.GetCurrentStudentUseCase;
import com.studyflix.android.domain.usecase.student.IncrementPaperUsageUseCase;
import com.studyflix.android.domain.usecase.student.IncrementQuizUsageUseCase;
import com.studyflix.android.domain.usecase.student.IncrementVideoUsageUseCase;
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
public final class SubscriptionViewModel_Factory implements Factory<SubscriptionViewModel> {
  private final Provider<GetCurrentStudentUseCase> getCurrentStudentUseCaseProvider;

  private final Provider<CheckFeatureAccessUseCase> checkFeatureAccessUseCaseProvider;

  private final Provider<IncrementVideoUsageUseCase> incrementVideoUsageUseCaseProvider;

  private final Provider<IncrementPaperUsageUseCase> incrementPaperUsageUseCaseProvider;

  private final Provider<IncrementQuizUsageUseCase> incrementQuizUsageUseCaseProvider;

  public SubscriptionViewModel_Factory(
      Provider<GetCurrentStudentUseCase> getCurrentStudentUseCaseProvider,
      Provider<CheckFeatureAccessUseCase> checkFeatureAccessUseCaseProvider,
      Provider<IncrementVideoUsageUseCase> incrementVideoUsageUseCaseProvider,
      Provider<IncrementPaperUsageUseCase> incrementPaperUsageUseCaseProvider,
      Provider<IncrementQuizUsageUseCase> incrementQuizUsageUseCaseProvider) {
    this.getCurrentStudentUseCaseProvider = getCurrentStudentUseCaseProvider;
    this.checkFeatureAccessUseCaseProvider = checkFeatureAccessUseCaseProvider;
    this.incrementVideoUsageUseCaseProvider = incrementVideoUsageUseCaseProvider;
    this.incrementPaperUsageUseCaseProvider = incrementPaperUsageUseCaseProvider;
    this.incrementQuizUsageUseCaseProvider = incrementQuizUsageUseCaseProvider;
  }

  @Override
  public SubscriptionViewModel get() {
    return newInstance(getCurrentStudentUseCaseProvider.get(), checkFeatureAccessUseCaseProvider.get(), incrementVideoUsageUseCaseProvider.get(), incrementPaperUsageUseCaseProvider.get(), incrementQuizUsageUseCaseProvider.get());
  }

  public static SubscriptionViewModel_Factory create(
      Provider<GetCurrentStudentUseCase> getCurrentStudentUseCaseProvider,
      Provider<CheckFeatureAccessUseCase> checkFeatureAccessUseCaseProvider,
      Provider<IncrementVideoUsageUseCase> incrementVideoUsageUseCaseProvider,
      Provider<IncrementPaperUsageUseCase> incrementPaperUsageUseCaseProvider,
      Provider<IncrementQuizUsageUseCase> incrementQuizUsageUseCaseProvider) {
    return new SubscriptionViewModel_Factory(getCurrentStudentUseCaseProvider, checkFeatureAccessUseCaseProvider, incrementVideoUsageUseCaseProvider, incrementPaperUsageUseCaseProvider, incrementQuizUsageUseCaseProvider);
  }

  public static SubscriptionViewModel newInstance(GetCurrentStudentUseCase getCurrentStudentUseCase,
      CheckFeatureAccessUseCase checkFeatureAccessUseCase,
      IncrementVideoUsageUseCase incrementVideoUsageUseCase,
      IncrementPaperUsageUseCase incrementPaperUsageUseCase,
      IncrementQuizUsageUseCase incrementQuizUsageUseCase) {
    return new SubscriptionViewModel(getCurrentStudentUseCase, checkFeatureAccessUseCase, incrementVideoUsageUseCase, incrementPaperUsageUseCase, incrementQuizUsageUseCase);
  }
}
