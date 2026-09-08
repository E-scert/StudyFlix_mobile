package com.studyflix.android.ui.teacher.chat;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.repository.TeacherRepository;
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
public final class TeacherConversationViewModel_Factory implements Factory<TeacherConversationViewModel> {
  private final Provider<TeacherRepository> repositoryProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public TeacherConversationViewModel_Factory(Provider<TeacherRepository> repositoryProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.repositoryProvider = repositoryProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public TeacherConversationViewModel get() {
    return newInstance(repositoryProvider.get(), firebaseAuthProvider.get());
  }

  public static TeacherConversationViewModel_Factory create(
      Provider<TeacherRepository> repositoryProvider, Provider<FirebaseAuth> firebaseAuthProvider) {
    return new TeacherConversationViewModel_Factory(repositoryProvider, firebaseAuthProvider);
  }

  public static TeacherConversationViewModel newInstance(TeacherRepository repository,
      FirebaseAuth firebaseAuth) {
    return new TeacherConversationViewModel(repository, firebaseAuth);
  }
}
