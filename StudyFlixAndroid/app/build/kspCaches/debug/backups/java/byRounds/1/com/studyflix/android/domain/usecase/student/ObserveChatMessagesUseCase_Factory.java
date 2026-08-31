package com.studyflix.android.domain.usecase.student;

import com.studyflix.android.domain.repository.ChatRepository;
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
public final class ObserveChatMessagesUseCase_Factory implements Factory<ObserveChatMessagesUseCase> {
  private final Provider<ChatRepository> chatRepositoryProvider;

  public ObserveChatMessagesUseCase_Factory(Provider<ChatRepository> chatRepositoryProvider) {
    this.chatRepositoryProvider = chatRepositoryProvider;
  }

  @Override
  public ObserveChatMessagesUseCase get() {
    return newInstance(chatRepositoryProvider.get());
  }

  public static ObserveChatMessagesUseCase_Factory create(
      Provider<ChatRepository> chatRepositoryProvider) {
    return new ObserveChatMessagesUseCase_Factory(chatRepositoryProvider);
  }

  public static ObserveChatMessagesUseCase newInstance(ChatRepository chatRepository) {
    return new ObserveChatMessagesUseCase(chatRepository);
  }
}
