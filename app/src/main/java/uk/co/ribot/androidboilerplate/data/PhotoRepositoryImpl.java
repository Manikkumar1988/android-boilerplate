package uk.co.ribot.androidboilerplate.data;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import uk.co.ribot.androidboilerplate.data.model.Photo;
import uk.co.ribot.androidboilerplate.data.remote.UnSplashService;

public class PhotoRepositoryImpl {
    private UnSplashService githubUserRestService;

    public PhotoRepositoryImpl(UnSplashService githubUserRestService) {
        this.githubUserRestService = githubUserRestService;
    }


    public Observable<List<Photo>> searchUsers(final String page) {
        return Observable.defer(() -> githubUserRestService.listRepos(page,"c816e8657f1a6fb14fc6399da6a2771829734026f66b29adfe95a808b436b6e6")).
                retryWhen(observable -> observable.flatMap(o -> {
                    if (o instanceof IOException) {
                        return Observable.just(null);
                    }
                    return Observable.error(o);
                }));
    }
}
