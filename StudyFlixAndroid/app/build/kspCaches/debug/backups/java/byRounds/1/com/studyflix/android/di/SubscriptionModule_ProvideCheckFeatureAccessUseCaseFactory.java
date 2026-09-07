package com.studyflix.android.di;

import com.studyflix.android.domain.subscription.SubscriptionManager;
import com.studyflix.android.domain.usecase.student.CheckFeatureAccessUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class SubscriptionModule_ProvideCheckFeatureAccessUseCaseFactory implements Factory<CheckFeatureAccessUseCase> {
  private final Provider<SubscriptionManager> subscriptionManagerProvider;

  public SubscriptionModule_ProvideCheckFeatureAccessUseCaseFactory(
      Provider<SubscriptionManager> subscriptionManagerProvider) {
    this.subscriptionManagerProvider = subscriptionManagerProvider;
  }

  @Override
  public CheckFeatureAccessUseCase get() {
    return provideCheckFeatureAccessUseCase(subscriptionManagerProvider.get());
  }

  public static SubscriptionModule_ProvideCheckFeatureAccessUseCaseFactory create(
      Provider<SubscriptionManager> subscriptionManagerProvider) {
    return new SubscriptionModule_ProvideCheckFeatureAccessUseCaseFactory(subscriptionManagerProvider);
  }

  public static CheckFeatureAccessUseCase provideCheckFeatureAccessUseCase(
      SubscriptionManager subscriptionManager) {
    return Preconditions.checkNotNullFromProvides(SubscriptionModule.INSTANCE.provideCheckFeatureAccessUseCase(subscriptionManager));
  }
}
