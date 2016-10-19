package uk.co.ribot.androidboilerplate.ui.dashboard;

import java.util.List;

import uk.co.ribot.androidboilerplate.data.model.Ribot;
import uk.co.ribot.androidboilerplate.ui.base.MvpView;

public interface GalleryMvpView extends MvpView{
    void showWallpaper(List<Ribot> ribots);

    void showEmpty();

    void showError();
}
