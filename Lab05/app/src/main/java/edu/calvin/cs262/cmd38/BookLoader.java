package edu.calvin.cs262.cmd38;


import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    /**
     * The constructor sets the query String.
     *
     * @param context The current Context.
     * @param queryString The String to query for (part of a book title).
     */
    BookLoader(Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    /**
     * Begin loading the to find books matching the query String.
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    /**
     * Get the JSON of book info found from the query.
     *
     * @return The book info JSON.
     */
    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }
}