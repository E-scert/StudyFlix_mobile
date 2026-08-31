package com.studyflix.android.ui.student.chat;

import com.google.firebase.auth.FirebaseAuth;
import com.studyflix.android.domain.usecase.student.ObserveChatMessagesUseCase;
import com.studyflix.android.domain.usecase.student.SendChatMessageUseCase;
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
public final class ChatViewModel_Factory implements Factory<ChatViewModel> {
  private final Provider<ObserveChatMessagesUseCase> observeChatMessagesUseCaseProvider;

  private final Provider<SendChatMessageUseCase> sendChatMessageUseCaseProvider;

  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public ChatViewModel_Factory(
      Provider<ObserveChatMessagesUseCase> observeChatMessagesUseCaseProvider,
      Provider<SendChatMessageUseCase> sendChatMessageUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    this.observeChatMessagesUseCaseProvider = observeChatMessagesUseCaseProvider;
    this.sendChatMessageUseCaseProvider = sendChatMessageUseCaseProvider;
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  @Override
  public ChatViewModel get() {
    return newInstance(observeChatMessagesUseCaseProvider.get(), sendChatMessageUseCaseProvider.get(), firebaseAuthProvider.get());
  }

  public static ChatViewModel_Factory create(
      Provider<ObserveChatMessagesUseCase> observeChatMessagesUseCaseProvider,
      Provider<SendChatMessageUseCase> sendChatMessageUseCaseProvider,
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new ChatViewModel_Factory(observeChatMessagesUseCaseProvider, sendChatMessageUseCaseProvider, firebaseAuthProvider);
  }

  public static ChatViewModel newInstance(ObserveChatMessagesUseCase observeChatMessagesUseCase,
      SendChatMessageUseCase sendChatMessageUseCase, FirebaseAuth firebaseAuth) {
    return new ChatViewModel(observeChatMessagesUseCase, sendChatMessageUseCase, firebaseAuth);
  }
}
