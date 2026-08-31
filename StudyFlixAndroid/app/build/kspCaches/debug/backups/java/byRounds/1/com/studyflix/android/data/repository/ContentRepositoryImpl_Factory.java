package com.studyflix.android.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.VideoDao;
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
public final class ContentRepositoryImpl_Factory implements Factory<ContentRepositoryImpl> {
  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<VideoDao> videoDaoProvider;

  public ContentRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<VideoDao> videoDaoProvider) {
    this.firestoreProvider = firestoreProvider;
    this.videoDaoProvider = videoDaoProvider;
  }

  @Override
  public ContentRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), videoDaoProvider.get());
  }

  public static ContentRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<VideoDao> videoDaoProvider) {
    return new ContentRepositoryImpl_Factory(firestoreProvider, videoDaoProvider);
  }

  public static ContentRepositoryImpl newInstance(FirebaseFirestore firestore, VideoDao videoDao) {
    return new ContentRepositoryImpl(firestore, videoDao);
  }
}
