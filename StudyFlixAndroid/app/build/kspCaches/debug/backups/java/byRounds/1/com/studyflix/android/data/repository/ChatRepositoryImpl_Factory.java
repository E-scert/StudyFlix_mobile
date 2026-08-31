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
public final class ChatRepositoryImpl_Factory implements Factory<ChatRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  public ChatRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider) {
    this.firestoreProvider = firestoreProvider;
  }

  @Override
  public ChatRepositoryImpl get() {
    return newInstance(firestoreProvider.get());
  }

  public static ChatRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider) {
    return new ChatRepositoryImpl_Factory(firestoreProvider);
  }

  public static ChatRepositoryImpl newInstance(FirebaseFirestore firestore) {
    return new ChatRepositoryImpl(firestore);
  }
}
