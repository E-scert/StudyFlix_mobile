package com.studyflix.android.di;

import com.studyflix.android.domain.subscription.SubscriptionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class SubscriptionModule_ProvideSubscriptionManagerFactory implements Factory<SubscriptionManager> {
  @Override
  public SubscriptionManager get() {
    return provideSubscriptionManager();
  }

  public static SubscriptionModule_ProvideSubscriptionManagerFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SubscriptionManager provideSubscriptionManager() {
    return Preconditions.checkNotNullFromProvides(SubscriptionModule.INSTANCE.provideSubscriptionManager());
  }

  private static final class InstanceHolder {
    private static final SubscriptionModule_ProvideSubscriptionManagerFactory INSTANCE = new SubscriptionModule_ProvideSubscriptionManagerFactory();
  }
}
