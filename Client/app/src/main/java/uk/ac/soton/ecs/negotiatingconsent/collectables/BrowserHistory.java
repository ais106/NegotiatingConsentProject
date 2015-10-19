package uk.ac.soton.ecs.negotiatingconsent.collectables;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Browser;
import android.util.Log;

import com.google.gson.Gson;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.helpers.AppCoreData;
import uk.ac.soton.ecs.negotiatingconsent.helpers.dbDataHandler;

/**
 * Created by anna on 15/07/15.
 */
public class BrowserHistory implements Collectables {


    Context context = AppCoreData.Context();
    ContentResolver cr = AppCoreData.ContentResolver();
    public Url urlObj = null; // Create Url object;
    final ArrayList<Url> urlsArray;
    public dbDataHandler dataBrowser = new dbDataHandler(context);
    public DataTable dataTable = new DataTable();

    //Data that is collected
    String url = "";
    String title="";
    String visits="";
    String lastVisit = "";
    byte[] browserIcon;

    public BrowserHistory() {
        urlsArray = new ArrayList<>();

    }



    public ArrayList<Url> getHistory (int url_limit) {

        int number = 0; //default limit

        String order=Browser.BookmarkColumns.DATE+" DESC";
        String[] proj = new String[]{Browser.BookmarkColumns.TITLE,
                Browser.BookmarkColumns.URL,
                Browser.BookmarkColumns.VISITS,
                Browser.BookmarkColumns.DATE,
                Browser.BookmarkColumns.FAVICON};
        //String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
        Cursor mCur = cr.query(Browser.BOOKMARKS_URI, proj, null, null, order);
        mCur.moveToFirst();

        if (mCur.moveToFirst() && mCur.getCount() > 0)

        {

            while (!mCur.isAfterLast() && number < url_limit) {

                title = mCur.getString(mCur.getColumnIndex(proj[0])); // Title of the web

                url = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.URL)); //Website Address

                url = getBaseUrl(url);

                visits = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.VISITS)); // Number of visits for the url

                String milliseconds = mCur.getString(mCur.getColumnIndex(Browser.BookmarkColumns.DATE));

                long dateLong = Long.parseLong(milliseconds);

                Date date = new Date(dateLong);

                lastVisit = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").format(date);

                browserIcon = mCur.getBlob(mCur.getColumnIndex(Browser.BookmarkColumns.FAVICON));

                urlObj = new Url(title, url, visits, lastVisit, browserIcon); // Creating a new object

                System.out.println(urlObj);

                urlsArray.add(urlObj); //Add data to the array

                mCur.moveToNext();

                number++;
            }
            mCur.close();

        }

        return urlsArray;

    }

    @Override
    public void saveDataLocally() {

        List<Url> browserHistoryList = getHistory(5);

        for(int i=0; i < browserHistoryList.size(); i++)

        {

            Gson gson = new Gson();
            String browser = gson.toJson(browserHistoryList.get(i));
            Log.d("Browser Save Data",browser);

            dataTable.setPermission("browser history");
            dataTable.setDataContent(browser);
            dataBrowser.addData(dataTable);
            dataBrowser.close();


        }

    }

    public boolean dataExist() {
        String[] proj = new String[]{Browser.BookmarkColumns.TITLE, Browser.BookmarkColumns.URL, Browser.BookmarkColumns.VISITS, Browser.BookmarkColumns.DATE};
        String sel = Browser.BookmarkColumns.BOOKMARK + " = 0"; // 0 = history, 1 = bookmark
        Cursor mCur = cr.query(Browser.BOOKMARKS_URI, proj, sel, null, null);
        if (mCur != null) {
            mCur.moveToFirst();

            if (mCur.moveToFirst() && mCur.getCount() > 0)

            {
                mCur.close();
                return true;
            }
            mCur.close();
            return false;

        }
        return false;
    }




    public List<Url> readDataFromLocalDb() {

        Context context = AppCoreData.Context();

        dbDataHandler dbData = new dbDataHandler(context);

        List<DataTable> dataTable = dbData.getAllBrowser();

        List<Url> dataList = new ArrayList<>();

        for (DataTable dl : dataTable) {

            Gson gson = new Gson();
            String jsonString = dl.getDataContent();

            //convert the json string back to object
            Url obj = gson.fromJson(jsonString, Url.class);
            dataList.add(obj);

            System.out.println(obj);

        }
        return dataList;

    }

/*
    @Override
    public List<String> showData()

    {
        List<Url> browserList = readDataFromLocalDb();
        List<String> list = new ArrayList<>();

        for(int i=0; i < browserList.size(); i++)

        {
            String urlTitle = browserList.get(i).getTitle();
            String url = browserList.get(i).getUrl();
            String visits = browserList.get(i).getVisits();
            String date = browserList.get(i).getLastVisit();

            String browserAll =
                    urlTitle +"\n"
                            + url + "\n"
                            + "Number of visits: " + visits
                            + "\n"+ "Date: \n" + date;


            Log.d("Browser History", browserAll);
            list.add(browserAll);


        }
        return list;

    }
    */

    public static String getBaseUrl(String urlString)
    {

        if(urlString == null)
        {
            return null;
        }

        try
        {
            //URL url = new URL(urlString);
            String[] separated = urlString.split("/");
            String urlAddress = "";
            int counter = separated.length;
            for(int i = 2; i<counter;i++) {
                String str = separated[i];

                if (str.length()>15 && str.contains("?")){
                    //String str = separated[i].replaceAll("[^a-z.]", ""); // to remove all non-alphabetic char characters
                    String str2 = str.substring(0,10);
                    urlAddress = urlAddress + str2+".../" ; // = http
                }
                else {
                    urlAddress = urlAddress + str + "/"; // = http

                }
            }
            return urlAddress;

        }
        catch (Exception e)
        {
            return null;
        }
    }
}
