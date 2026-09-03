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
public final class AssignmentRepositoryImpl_Factory implements Factory<AssignmentRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public AssignmentRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public AssignmentRepositoryImpl get() {
    return newInstance(firestoreProvider.get());
  }

  public static AssignmentRepositoryImpl_Factory create(
      Provider<FirebaseFirestore> firestoreProvider) {
    return new AssignmentRepositoryImpl_Factory(firestoreProvider);
  }

  public static AssignmentRepositoryImpl newInstance(FirebaseFirestore firestore) {
    return new AssignmentRepositoryImpl(firestore);
  }
}
