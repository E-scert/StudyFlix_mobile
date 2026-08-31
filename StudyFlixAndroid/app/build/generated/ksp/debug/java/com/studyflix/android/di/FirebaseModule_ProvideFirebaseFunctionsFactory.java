package com.studyflix.android.di;

import com.google.firebase.functions.FirebaseFunctions;
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
public final class FirebaseModule_ProvideFirebaseFunctionsFactory implements Factory<FirebaseFunctions> {
  @Override
  public FirebaseFunctions get() {
    return provideFirebaseFunctions();
  }

  public static FirebaseModule_ProvideFirebaseFunctionsFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseFunctions provideFirebaseFunctions() {
    return Preconditions.checkNotNullFromProvides(FirebaseModule.INSTANCE.provideFirebaseFunctions());
  }

  private static final class InstanceHolder {
    private static final FirebaseModule_ProvideFirebaseFunctionsFactory INSTANCE = new FirebaseModule_ProvideFirebaseFunctionsFactory();
  }
}
