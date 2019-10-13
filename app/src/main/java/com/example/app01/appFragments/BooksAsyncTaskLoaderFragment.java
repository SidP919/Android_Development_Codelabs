package com.example.app01.appFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app01.BookLoader;
import com.example.app01.NetworkUtils;
import com.example.app01.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class BooksAsyncTaskLoaderFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    private Button mSearchBookButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_books_async_task_loader, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBookInput = (EditText) view.findViewById(R.id.BookAsyncTaskLoader_id_editText01);
        mTitleText = (TextView) view.findViewById(R.id.BookAsyncTaskLoader_id_titleTV);
        mAuthorText = (TextView) view.findViewById(R.id.BookAsyncTaskLoader_id_authorTV);

        mSearchBookButton = view.findViewById(R.id.BookAsyncTaskLoader_id_searchBook);
        mSearchBookButton.setOnClickListener(this);
        /*
        If the loader exists, initialize it. You only want to reassociate the loader to the
        activity if a query has already been executed.
         In the initial state of the app,
         no data is loaded,
         so there is no data to preserve. See Below code:-
         */
        if (getActivity().getSupportLoaderManager().getLoader(0) != null) {
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new BookLoader(getContext(), queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {

            //Go here to see the JSON tree:
            // https://www.googleapis.com/books/v1/volumes?q=Romeo&maxResults=10&printType=books
            //to understand why are we using specific JSON objects in a specific manner.

            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            //Required variables:
            int i = 0;
            String title = null;
            String authors = null;

            //Iterate through the itemsArray array,
            // checking each book for title and author information.
            // With each loop, test to see if both an author and
            // a title are found, and if so, exit the loop.
            // This way, only entries with both a title and author will be displayed.
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Move to the next item.
                i++;
            }

            // If both are found, display the result.
            if (title != null && authors != null) {
                mTitleText.setText(title);
                mAuthorText.setText(authors);
            } else {
                // If none are found, update the UI to
                // show failed results.
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }

        } catch (JSONException e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.BookAsyncTaskLoader_id_searchBook) {
            String queryString = mBookInput.getText().toString();

            //HidetheKeyboard
            InputMethodManager inputManager = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }

            if (queryString.length() == 0) {
                Toast.makeText(getContext(), "Enter a keyword to search a book.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (NetworkUtils.isNetworkConnected(getActivity().getApplicationContext())) {
                //new FetchBook(mTitleText, mAuthorText).execute(queryString); ----- Now we will do this
                //using AsyncTaskLoader as explained below:-
                //replaced the call to execute the FetchBook task with a call to restartLoader().
                // Pass in the query string that you got from the EditText in the loader's Bundle object.
                Bundle queryBundle = new Bundle();
                queryBundle.putString("queryString", queryString);
                getActivity().getSupportLoaderManager().restartLoader(0, queryBundle, this);
                //About RESTART LOADER:----------->
            /*
            The restartLoader() method is defined by the LoaderManager,
             which manages all the loaders used in an activity or fragment.
             Each activity has exactly one LoaderManager instance that is responsible for the
             lifecycle of the Loaders that the activity manages.

            The restartLoader() method takes three arguments:------->

            A loader id, which is useful if you implement more than one loader in your activity.

            An arguments Bundle for any data that the loader needs.

            The instance of LoaderCallbacks that you implemented in your activity. If you want the loader to
            deliver the results to the current Activity, specify "this" as the third argument.
             */

                mTitleText.setText("");
                mAuthorText.setText(R.string.BooksAsyncTaskLoader_string_loading);
            } else {
                Toast.makeText(getContext(), "Please check your network connection", Toast.LENGTH_LONG).show();
            }
        }
    }
}
