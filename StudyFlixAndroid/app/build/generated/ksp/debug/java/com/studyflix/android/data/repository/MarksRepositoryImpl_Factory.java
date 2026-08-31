package com.studyflix.android.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.MarkDao;
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
public final class MarksRepositoryImpl_Factory implements Factory<MarksRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<MarkDao> markDaoProvider;

  public MarksRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<MarkDao> markDaoProvider) {
    this.firestoreProvider = firestoreProvider;
    this.markDaoProvider = markDaoProvider;
  }

  @Override
  public MarksRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), markDaoProvider.get());
  }

  public static MarksRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<MarkDao> markDaoProvider) {
    return new MarksRepositoryImpl_Factory(firestoreProvider, markDaoProvider);
  }

  public static MarksRepositoryImpl newInstance(FirebaseFirestore firestore, MarkDao markDao) {
    return new MarksRepositoryImpl(firestore, markDao);
  }
}
