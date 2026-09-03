package com.studyflix.android.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
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
public final class PastPaperRepositoryImpl_Factory implements Factory<PastPaperRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public PastPaperRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public PastPaperRepositoryImpl get() {
    return newInstance(firestoreProvider.get());
  }

  public static PastPaperRepositoryImpl_Factory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new PastPaperRepositoryImpl_Factory(firestoreProvider);
  }

  public static PastPaperRepositoryImpl newInstance(FirebaseFirestore firestore) {
    return new PastPaperRepositoryImpl(firestore);
  }
}
