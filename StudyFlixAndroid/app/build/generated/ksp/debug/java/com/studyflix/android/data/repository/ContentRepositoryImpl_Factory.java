package com.studyflix.android.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.dao.VideoDao;
import com.studyflix.android.domain.repository.StudentRepository;
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

  private final Provider<FirebaseAuth> authProvider;

  private final Provider<StudentRepository> studentRepositoryProvider;

  public ContentRepositoryImpl_Factory(Provider<FirebaseFirestore> firestoreProvider,
      Provider<VideoDao> videoDaoProvider, Provider<FirebaseAuth> authProvider,
      Provider<StudentRepository> studentRepositoryProvider) {
    this.firestoreProvider = firestoreProvider;
    this.videoDaoProvider = videoDaoProvider;
    this.authProvider = authProvider;
    this.studentRepositoryProvider = studentRepositoryProvider;
  }

  @Override
  public ContentRepositoryImpl get() {
    return newInstance(firestoreProvider.get(), videoDaoProvider.get(), authProvider.get(), studentRepositoryProvider.get());
  }

  public static ContentRepositoryImpl_Factory create(Provider<FirebaseFirestore> firestoreProvider,
      Provider<VideoDao> videoDaoProvider, Provider<FirebaseAuth> authProvider,
      Provider<StudentRepository> studentRepositoryProvider) {
    return new ContentRepositoryImpl_Factory(firestoreProvider, videoDaoProvider, authProvider, studentRepositoryProvider);
  }

  public static ContentRepositoryImpl newInstance(FirebaseFirestore firestore, VideoDao videoDao,
      FirebaseAuth auth, StudentRepository studentRepository) {
    return new ContentRepositoryImpl(firestore, videoDao, auth, studentRepository);
  }
}
