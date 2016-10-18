package uk.co.ribot.androidboilerplate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.junit.runners.model.InitializationError;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.observers.TestSubscriber;
import uk.co.ribot.androidboilerplate.data.PhotoRepositoryImpl;
import uk.co.ribot.androidboilerplate.data.model.Photo;
import uk.co.ribot.androidboilerplate.data.model.User;
import uk.co.ribot.androidboilerplate.data.remote.UnSplashService;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PhotoRepositoryImplTest  {
    @Mock
    UnSplashService githubUserRestService;

    private PhotoRepositoryImpl userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userRepository = new PhotoRepositoryImpl(githubUserRestService);
    }

    @Test
    public void searchUsers_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        when(githubUserRestService.listRepos(anyString(),anyString())).thenReturn(Observable.just(githubUserList()));

        //When
        TestSubscriber<List<Photo>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<List<Photo>> onNextEvents = subscriber.getOnNextEvents();
        List<Photo> users = onNextEvents.get(0);
        Assert.assertEquals("1", users.get(0).getId());
        Assert.assertEquals("2", users.get(1).getId());

        verify(githubUserRestService).listRepos(anyString(),anyString());

    }

    @Test
    public void searchUsers_OtherHttpError_SearchTerminatedWithError() {
        //Given
        when(githubUserRestService.listRepos(anyString(),anyString())).thenReturn(get403ForbiddenError());

        //When
        TestSubscriber<List<Photo>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(HttpException.class);

        verify(githubUserRestService).listRepos(anyString(),anyString());

    }

    @Test
    public void searchUsers_IOExcepti_SearchTerminatedWithNoError() {
        //Given
        when(githubUserRestService.listRepos(anyString(),anyString())).thenReturn(getIOExceptionError(), Observable.just(githubUserList()));

        //When
        TestSubscriber<List<Photo>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        verify(githubUserRestService, times(2)).listRepos(anyString(),anyString());

    }

    @Test
    public void searchUsers_IOExcepti_SearchTerminatedWithError() {
        //Given
        when(githubUserRestService.listRepos(anyString(),anyString())).thenReturn(getIOExceptionError());

        //When
        TestSubscriber<List<Photo>> subscriber = new TestSubscriber<>();
        userRepository.searchUsers("1").subscribe(subscriber);

        //Then
        subscriber.awaitTerminalEvent();
        subscriber.assertError(IOException.class);

        verify(githubUserRestService, never()).listRepos(anyString(),anyString());
    }

    private Observable getIOExceptionError() {
        return Observable.error(new IOException());
    }


    private Observable<List<Photo>> get403ForbiddenError() {
        return Observable.error(new HttpException(
                Response.error(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))));

    }

    private List<Photo> githubUserList() {
        Photo user = new Photo();
        user.setId("1");

        Photo user2 = new Photo();
        user2.setId("2");

        List<Photo> githubUsers = new ArrayList<>();
        githubUsers.add(user);
        githubUsers.add(user2);

        return githubUsers;
    }


}
