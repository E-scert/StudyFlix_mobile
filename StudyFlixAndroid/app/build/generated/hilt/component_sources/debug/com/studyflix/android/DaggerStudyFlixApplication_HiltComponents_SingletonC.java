package com.studyflix.android;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.studyflix.android.data.local.StudyFlixDatabase;
import com.studyflix.android.data.local.dao.MarkDao;
import com.studyflix.android.data.local.dao.QuizDao;
import com.studyflix.android.data.local.dao.StudentDao;
import com.studyflix.android.data.local.dao.VideoDao;
import com.studyflix.android.data.repository.AssignmentRepositoryImpl;
import com.studyflix.android.data.repository.AuthRepositoryImpl;
import com.studyflix.android.data.repository.ChatRepositoryImpl;
import com.studyflix.android.data.repository.ContentRepositoryImpl;
import com.studyflix.android.data.repository.MarksRepositoryImpl;
import com.studyflix.android.data.repository.NotesRepositoryImpl;
import com.studyflix.android.data.repository.PastPaperRepositoryImpl;
import com.studyflix.android.data.repository.QuizRepositoryImpl;
import com.studyflix.android.data.repository.StudentRepositoryImpl;
import com.studyflix.android.di.DatabaseModule_ProvideDatabaseFactory;
import com.studyflix.android.di.DatabaseModule_ProvideMarkDaoFactory;
import com.studyflix.android.di.DatabaseModule_ProvideQuizDaoFactory;
import com.studyflix.android.di.DatabaseModule_ProvideStudentDaoFactory;
import com.studyflix.android.di.DatabaseModule_ProvideVideoDaoFactory;
import com.studyflix.android.di.FirebaseModule_ProvideFirebaseAuthFactory;
import com.studyflix.android.di.FirebaseModule_ProvideFirestoreFactory;
import com.studyflix.android.domain.usecase.auth.SignInUseCase;
import com.studyflix.android.domain.usecase.auth.SignOutUseCase;
import com.studyflix.android.domain.usecase.auth.SignUpStudentUseCase;
import com.studyflix.android.domain.usecase.student.GetMarksUseCase;
import com.studyflix.android.domain.usecase.student.GetQuizzesUseCase;
import com.studyflix.android.domain.usecase.student.GetStudentProfileUseCase;
import com.studyflix.android.domain.usecase.student.GetVideosUseCase;
import com.studyflix.android.domain.usecase.student.ObserveChatMessagesUseCase;
import com.studyflix.android.domain.usecase.student.SendChatMessageUseCase;
import com.studyflix.android.domain.usecase.student.SubmitQuizUseCase;
import com.studyflix.android.ui.auth.LoginViewModel;
import com.studyflix.android.ui.auth.LoginViewModel_HiltModules;
import com.studyflix.android.ui.auth.SignUpViewModel;
import com.studyflix.android.ui.auth.SignUpViewModel_HiltModules;
import com.studyflix.android.ui.student.assignments.AssignmentDetailsViewModel;
import com.studyflix.android.ui.student.assignments.AssignmentDetailsViewModel_HiltModules;
import com.studyflix.android.ui.student.assignments.AssignmentsViewModel;
import com.studyflix.android.ui.student.assignments.AssignmentsViewModel_HiltModules;
import com.studyflix.android.ui.student.chat.ChatViewModel;
import com.studyflix.android.ui.student.chat.ChatViewModel_HiltModules;
import com.studyflix.android.ui.student.home.StudentHomeViewModel;
import com.studyflix.android.ui.student.home.StudentHomeViewModel_HiltModules;
import com.studyflix.android.ui.student.marks.MarksViewModel;
import com.studyflix.android.ui.student.marks.MarksViewModel_HiltModules;
import com.studyflix.android.ui.student.notes.NotesViewModel;
import com.studyflix.android.ui.student.notes.NotesViewModel_HiltModules;
import com.studyflix.android.ui.student.pastpapers.PastPapersViewModel;
import com.studyflix.android.ui.student.pastpapers.PastPapersViewModel_HiltModules;
import com.studyflix.android.ui.student.quizzes.QuizzesViewModel;
import com.studyflix.android.ui.student.quizzes.QuizzesViewModel_HiltModules;
import com.studyflix.android.ui.student.quizzes.TakeQuizViewModel;
import com.studyflix.android.ui.student.quizzes.TakeQuizViewModel_HiltModules;
import com.studyflix.android.ui.student.videos.VideosViewModel;
import com.studyflix.android.ui.student.videos.VideosViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerStudyFlixApplication_HiltComponents_SingletonC {
  private DaggerStudyFlixApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public StudyFlixApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements StudyFlixApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements StudyFlixApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements StudyFlixApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements StudyFlixApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements StudyFlixApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements StudyFlixApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements StudyFlixApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public StudyFlixApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends StudyFlixApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends StudyFlixApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends StudyFlixApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends StudyFlixApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(ImmutableMap.<String, Boolean>builderWithExpectedSize(12).put(LazyClassKeyProvider.com_studyflix_android_ui_student_assignments_AssignmentDetailsViewModel, AssignmentDetailsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_assignments_AssignmentsViewModel, AssignmentsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_chat_ChatViewModel, ChatViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_auth_LoginViewModel, LoginViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_marks_MarksViewModel, MarksViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_notes_NotesViewModel, NotesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_pastpapers_PastPapersViewModel, PastPapersViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_quizzes_QuizzesViewModel, QuizzesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_auth_SignUpViewModel, SignUpViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_home_StudentHomeViewModel, StudentHomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_quizzes_TakeQuizViewModel, TakeQuizViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_studyflix_android_ui_student_videos_VideosViewModel, VideosViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_studyflix_android_ui_auth_SignUpViewModel = "com.studyflix.android.ui.auth.SignUpViewModel";

      static String com_studyflix_android_ui_student_marks_MarksViewModel = "com.studyflix.android.ui.student.marks.MarksViewModel";

      static String com_studyflix_android_ui_student_assignments_AssignmentDetailsViewModel = "com.studyflix.android.ui.student.assignments.AssignmentDetailsViewModel";

      static String com_studyflix_android_ui_auth_LoginViewModel = "com.studyflix.android.ui.auth.LoginViewModel";

      static String com_studyflix_android_ui_student_pastpapers_PastPapersViewModel = "com.studyflix.android.ui.student.pastpapers.PastPapersViewModel";

      static String com_studyflix_android_ui_student_chat_ChatViewModel = "com.studyflix.android.ui.student.chat.ChatViewModel";

      static String com_studyflix_android_ui_student_quizzes_QuizzesViewModel = "com.studyflix.android.ui.student.quizzes.QuizzesViewModel";

      static String com_studyflix_android_ui_student_home_StudentHomeViewModel = "com.studyflix.android.ui.student.home.StudentHomeViewModel";

      static String com_studyflix_android_ui_student_videos_VideosViewModel = "com.studyflix.android.ui.student.videos.VideosViewModel";

      static String com_studyflix_android_ui_student_assignments_AssignmentsViewModel = "com.studyflix.android.ui.student.assignments.AssignmentsViewModel";

      static String com_studyflix_android_ui_student_notes_NotesViewModel = "com.studyflix.android.ui.student.notes.NotesViewModel";

      static String com_studyflix_android_ui_student_quizzes_TakeQuizViewModel = "com.studyflix.android.ui.student.quizzes.TakeQuizViewModel";

      @KeepFieldType
      SignUpViewModel com_studyflix_android_ui_auth_SignUpViewModel2;

      @KeepFieldType
      MarksViewModel com_studyflix_android_ui_student_marks_MarksViewModel2;

      @KeepFieldType
      AssignmentDetailsViewModel com_studyflix_android_ui_student_assignments_AssignmentDetailsViewModel2;

      @KeepFieldType
      LoginViewModel com_studyflix_android_ui_auth_LoginViewModel2;

      @KeepFieldType
      PastPapersViewModel com_studyflix_android_ui_student_pastpapers_PastPapersViewModel2;

      @KeepFieldType
      ChatViewModel com_studyflix_android_ui_student_chat_ChatViewModel2;

      @KeepFieldType
      QuizzesViewModel com_studyflix_android_ui_student_quizzes_QuizzesViewModel2;

      @KeepFieldType
      StudentHomeViewModel com_studyflix_android_ui_student_home_StudentHomeViewModel2;

      @KeepFieldType
      VideosViewModel com_studyflix_android_ui_student_videos_VideosViewModel2;

      @KeepFieldType
      AssignmentsViewModel com_studyflix_android_ui_student_assignments_AssignmentsViewModel2;

      @KeepFieldType
      NotesViewModel com_studyflix_android_ui_student_notes_NotesViewModel2;

      @KeepFieldType
      TakeQuizViewModel com_studyflix_android_ui_student_quizzes_TakeQuizViewModel2;
    }
  }

  private static final class ViewModelCImpl extends StudyFlixApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AssignmentDetailsViewModel> assignmentDetailsViewModelProvider;

    private Provider<AssignmentsViewModel> assignmentsViewModelProvider;

    private Provider<ChatViewModel> chatViewModelProvider;

    private Provider<LoginViewModel> loginViewModelProvider;

    private Provider<MarksViewModel> marksViewModelProvider;

    private Provider<NotesViewModel> notesViewModelProvider;

    private Provider<PastPapersViewModel> pastPapersViewModelProvider;

    private Provider<QuizzesViewModel> quizzesViewModelProvider;

    private Provider<SignUpViewModel> signUpViewModelProvider;

    private Provider<StudentHomeViewModel> studentHomeViewModelProvider;

    private Provider<TakeQuizViewModel> takeQuizViewModelProvider;

    private Provider<VideosViewModel> videosViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private ObserveChatMessagesUseCase observeChatMessagesUseCase() {
      return new ObserveChatMessagesUseCase(singletonCImpl.chatRepositoryImplProvider.get());
    }

    private SendChatMessageUseCase sendChatMessageUseCase() {
      return new SendChatMessageUseCase(singletonCImpl.chatRepositoryImplProvider.get());
    }

    private SignInUseCase signInUseCase() {
      return new SignInUseCase(singletonCImpl.authRepositoryImplProvider.get());
    }

    private GetMarksUseCase getMarksUseCase() {
      return new GetMarksUseCase(singletonCImpl.marksRepositoryImplProvider.get());
    }

    private GetQuizzesUseCase getQuizzesUseCase() {
      return new GetQuizzesUseCase(singletonCImpl.quizRepositoryImplProvider.get());
    }

    private SignUpStudentUseCase signUpStudentUseCase() {
      return new SignUpStudentUseCase(singletonCImpl.authRepositoryImplProvider.get());
    }

    private GetStudentProfileUseCase getStudentProfileUseCase() {
      return new GetStudentProfileUseCase(singletonCImpl.studentRepositoryImplProvider.get());
    }

    private SignOutUseCase signOutUseCase() {
      return new SignOutUseCase(singletonCImpl.authRepositoryImplProvider.get());
    }

    private SubmitQuizUseCase submitQuizUseCase() {
      return new SubmitQuizUseCase(singletonCImpl.quizRepositoryImplProvider.get());
    }

    private GetVideosUseCase getVideosUseCase() {
      return new GetVideosUseCase(singletonCImpl.contentRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.assignmentDetailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.assignmentsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.chatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.loginViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.marksViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.notesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.pastPapersViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.quizzesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.signUpViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.studentHomeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.takeQuizViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.videosViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(ImmutableMap.<String, javax.inject.Provider<ViewModel>>builderWithExpectedSize(12).put(LazyClassKeyProvider.com_studyflix_android_ui_student_assignments_AssignmentDetailsViewModel, ((Provider) assignmentDetailsViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_assignments_AssignmentsViewModel, ((Provider) assignmentsViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_chat_ChatViewModel, ((Provider) chatViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_auth_LoginViewModel, ((Provider) loginViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_marks_MarksViewModel, ((Provider) marksViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_notes_NotesViewModel, ((Provider) notesViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_pastpapers_PastPapersViewModel, ((Provider) pastPapersViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_quizzes_QuizzesViewModel, ((Provider) quizzesViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_auth_SignUpViewModel, ((Provider) signUpViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_home_StudentHomeViewModel, ((Provider) studentHomeViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_quizzes_TakeQuizViewModel, ((Provider) takeQuizViewModelProvider)).put(LazyClassKeyProvider.com_studyflix_android_ui_student_videos_VideosViewModel, ((Provider) videosViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return ImmutableMap.<Class<?>, Object>of();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_studyflix_android_ui_student_marks_MarksViewModel = "com.studyflix.android.ui.student.marks.MarksViewModel";

      static String com_studyflix_android_ui_student_home_StudentHomeViewModel = "com.studyflix.android.ui.student.home.StudentHomeViewModel";

      static String com_studyflix_android_ui_auth_SignUpViewModel = "com.studyflix.android.ui.auth.SignUpViewModel";

      static String com_studyflix_android_ui_student_assignments_AssignmentsViewModel = "com.studyflix.android.ui.student.assignments.AssignmentsViewModel";

      static String com_studyflix_android_ui_student_quizzes_QuizzesViewModel = "com.studyflix.android.ui.student.quizzes.QuizzesViewModel";

      static String com_studyflix_android_ui_student_notes_NotesViewModel = "com.studyflix.android.ui.student.notes.NotesViewModel";

      static String com_studyflix_android_ui_auth_LoginViewModel = "com.studyflix.android.ui.auth.LoginViewModel";

      static String com_studyflix_android_ui_student_quizzes_TakeQuizViewModel = "com.studyflix.android.ui.student.quizzes.TakeQuizViewModel";

      static String com_studyflix_android_ui_student_assignments_AssignmentDetailsViewModel = "com.studyflix.android.ui.student.assignments.AssignmentDetailsViewModel";

      static String com_studyflix_android_ui_student_pastpapers_PastPapersViewModel = "com.studyflix.android.ui.student.pastpapers.PastPapersViewModel";

      static String com_studyflix_android_ui_student_videos_VideosViewModel = "com.studyflix.android.ui.student.videos.VideosViewModel";

      static String com_studyflix_android_ui_student_chat_ChatViewModel = "com.studyflix.android.ui.student.chat.ChatViewModel";

      @KeepFieldType
      MarksViewModel com_studyflix_android_ui_student_marks_MarksViewModel2;

      @KeepFieldType
      StudentHomeViewModel com_studyflix_android_ui_student_home_StudentHomeViewModel2;

      @KeepFieldType
      SignUpViewModel com_studyflix_android_ui_auth_SignUpViewModel2;

      @KeepFieldType
      AssignmentsViewModel com_studyflix_android_ui_student_assignments_AssignmentsViewModel2;

      @KeepFieldType
      QuizzesViewModel com_studyflix_android_ui_student_quizzes_QuizzesViewModel2;

      @KeepFieldType
      NotesViewModel com_studyflix_android_ui_student_notes_NotesViewModel2;

      @KeepFieldType
      LoginViewModel com_studyflix_android_ui_auth_LoginViewModel2;

      @KeepFieldType
      TakeQuizViewModel com_studyflix_android_ui_student_quizzes_TakeQuizViewModel2;

      @KeepFieldType
      AssignmentDetailsViewModel com_studyflix_android_ui_student_assignments_AssignmentDetailsViewModel2;

      @KeepFieldType
      PastPapersViewModel com_studyflix_android_ui_student_pastpapers_PastPapersViewModel2;

      @KeepFieldType
      VideosViewModel com_studyflix_android_ui_student_videos_VideosViewModel2;

      @KeepFieldType
      ChatViewModel com_studyflix_android_ui_student_chat_ChatViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.studyflix.android.ui.student.assignments.AssignmentDetailsViewModel 
          return (T) new AssignmentDetailsViewModel(singletonCImpl.assignmentRepositoryImplProvider.get());

          case 1: // com.studyflix.android.ui.student.assignments.AssignmentsViewModel 
          return (T) new AssignmentsViewModel(singletonCImpl.assignmentRepositoryImplProvider.get());

          case 2: // com.studyflix.android.ui.student.chat.ChatViewModel 
          return (T) new ChatViewModel(viewModelCImpl.observeChatMessagesUseCase(), viewModelCImpl.sendChatMessageUseCase(), singletonCImpl.provideFirebaseAuthProvider.get());

          case 3: // com.studyflix.android.ui.auth.LoginViewModel 
          return (T) new LoginViewModel(viewModelCImpl.signInUseCase());

          case 4: // com.studyflix.android.ui.student.marks.MarksViewModel 
          return (T) new MarksViewModel(viewModelCImpl.getMarksUseCase(), singletonCImpl.provideFirebaseAuthProvider.get());

          case 5: // com.studyflix.android.ui.student.notes.NotesViewModel 
          return (T) new NotesViewModel(singletonCImpl.notesRepositoryImplProvider.get());

          case 6: // com.studyflix.android.ui.student.pastpapers.PastPapersViewModel 
          return (T) new PastPapersViewModel(singletonCImpl.pastPaperRepositoryImplProvider.get());

          case 7: // com.studyflix.android.ui.student.quizzes.QuizzesViewModel 
          return (T) new QuizzesViewModel(viewModelCImpl.getQuizzesUseCase());

          case 8: // com.studyflix.android.ui.auth.SignUpViewModel 
          return (T) new SignUpViewModel(viewModelCImpl.signUpStudentUseCase());

          case 9: // com.studyflix.android.ui.student.home.StudentHomeViewModel 
          return (T) new StudentHomeViewModel(viewModelCImpl.getStudentProfileUseCase(), viewModelCImpl.signOutUseCase(), singletonCImpl.provideFirebaseAuthProvider.get());

          case 10: // com.studyflix.android.ui.student.quizzes.TakeQuizViewModel 
          return (T) new TakeQuizViewModel(singletonCImpl.quizRepositoryImplProvider.get(), viewModelCImpl.submitQuizUseCase(), singletonCImpl.provideFirebaseAuthProvider.get(), viewModelCImpl.savedStateHandle);

          case 11: // com.studyflix.android.ui.student.videos.VideosViewModel 
          return (T) new VideosViewModel(viewModelCImpl.getVideosUseCase());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends StudyFlixApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends StudyFlixApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends StudyFlixApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<FirebaseFirestore> provideFirestoreProvider;

    private Provider<StudyFlixDatabase> provideDatabaseProvider;

    private Provider<StudentRepositoryImpl> studentRepositoryImplProvider;

    private Provider<FirebaseAuth> provideFirebaseAuthProvider;

    private Provider<AssignmentRepositoryImpl> assignmentRepositoryImplProvider;

    private Provider<ChatRepositoryImpl> chatRepositoryImplProvider;

    private Provider<AuthRepositoryImpl> authRepositoryImplProvider;

    private Provider<MarksRepositoryImpl> marksRepositoryImplProvider;

    private Provider<NotesRepositoryImpl> notesRepositoryImplProvider;

    private Provider<PastPaperRepositoryImpl> pastPaperRepositoryImplProvider;

    private Provider<QuizRepositoryImpl> quizRepositoryImplProvider;

    private Provider<ContentRepositoryImpl> contentRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private StudentDao studentDao() {
      return DatabaseModule_ProvideStudentDaoFactory.provideStudentDao(provideDatabaseProvider.get());
    }

    private MarkDao markDao() {
      return DatabaseModule_ProvideMarkDaoFactory.provideMarkDao(provideDatabaseProvider.get());
    }

    private QuizDao quizDao() {
      return DatabaseModule_ProvideQuizDaoFactory.provideQuizDao(provideDatabaseProvider.get());
    }

    private VideoDao videoDao() {
      return DatabaseModule_ProvideVideoDaoFactory.provideVideoDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideFirestoreProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseFirestore>(singletonCImpl, 1));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<StudyFlixDatabase>(singletonCImpl, 3));
      this.studentRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<StudentRepositoryImpl>(singletonCImpl, 2));
      this.provideFirebaseAuthProvider = DoubleCheck.provider(new SwitchingProvider<FirebaseAuth>(singletonCImpl, 4));
      this.assignmentRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AssignmentRepositoryImpl>(singletonCImpl, 0));
      this.chatRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ChatRepositoryImpl>(singletonCImpl, 5));
      this.authRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AuthRepositoryImpl>(singletonCImpl, 6));
      this.marksRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<MarksRepositoryImpl>(singletonCImpl, 7));
      this.notesRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<NotesRepositoryImpl>(singletonCImpl, 8));
      this.pastPaperRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<PastPaperRepositoryImpl>(singletonCImpl, 9));
      this.quizRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<QuizRepositoryImpl>(singletonCImpl, 10));
      this.contentRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ContentRepositoryImpl>(singletonCImpl, 11));
    }

    @Override
    public void injectStudyFlixApplication(StudyFlixApplication studyFlixApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.studyflix.android.data.repository.AssignmentRepositoryImpl 
          return (T) new AssignmentRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.studentRepositoryImplProvider.get(), singletonCImpl.provideFirebaseAuthProvider.get());

          case 1: // com.google.firebase.firestore.FirebaseFirestore 
          return (T) FirebaseModule_ProvideFirestoreFactory.provideFirestore();

          case 2: // com.studyflix.android.data.repository.StudentRepositoryImpl 
          return (T) new StudentRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.studentDao());

          case 3: // com.studyflix.android.data.local.StudyFlixDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // com.google.firebase.auth.FirebaseAuth 
          return (T) FirebaseModule_ProvideFirebaseAuthFactory.provideFirebaseAuth();

          case 5: // com.studyflix.android.data.repository.ChatRepositoryImpl 
          return (T) new ChatRepositoryImpl(singletonCImpl.provideFirestoreProvider.get());

          case 6: // com.studyflix.android.data.repository.AuthRepositoryImpl 
          return (T) new AuthRepositoryImpl(singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.provideFirestoreProvider.get());

          case 7: // com.studyflix.android.data.repository.MarksRepositoryImpl 
          return (T) new MarksRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.markDao());

          case 8: // com.studyflix.android.data.repository.NotesRepositoryImpl 
          return (T) new NotesRepositoryImpl(singletonCImpl.provideFirestoreProvider.get());

          case 9: // com.studyflix.android.data.repository.PastPaperRepositoryImpl 
          return (T) new PastPaperRepositoryImpl(singletonCImpl.provideFirestoreProvider.get());

          case 10: // com.studyflix.android.data.repository.QuizRepositoryImpl 
          return (T) new QuizRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.quizDao(), singletonCImpl.provideFirebaseAuthProvider.get(), singletonCImpl.studentRepositoryImplProvider.get());

          case 11: // com.studyflix.android.data.repository.ContentRepositoryImpl 
          return (T) new ContentRepositoryImpl(singletonCImpl.provideFirestoreProvider.get(), singletonCImpl.videoDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
