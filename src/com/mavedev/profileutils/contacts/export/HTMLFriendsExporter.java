package com.mavedev.profileutils.contacts.export;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.SparseBooleanArray;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HTMLFriendsExporter extends AsyncTask<Void, Integer, String> implements FriendsExporter {


    private static final String FILENAME = "exported_contacts.txt";
    private static final String DELIMITER = ";";
    private static final String CONTACTS_FOLDER = "Contacts";
    private FriendListViewAdapter adapter;
    private SparseBooleanArray checkedItemPositions;
    private Context context;
    private boolean sendEmail;
    private ProgressDialog progressBar;

    public HTMLFriendsExporter(FriendListViewAdapter adapter, SparseBooleanArray checkedItemPositions, Context context, boolean sendEmail) {
        this.adapter = adapter;
        this.checkedItemPositions = checkedItemPositions;
        this.context = context;
        this.sendEmail = sendEmail;
        progressBar =  new ProgressDialog(context);
        initProgressBar();
    }

    @Override
    public String export() {
        String data = buildData();
        return writeFile(data);
    }

    @Override
    public void executeTask() {
        this.execute();
    }

    private String writeFile(String data) {
        FileOutputStream fos = null;
        File exportFile = null;
        try {
            exportFile = new File(context.getExternalFilesDir(CONTACTS_FOLDER), FILENAME);
            fos = new FileOutputStream(exportFile);
            fos.write(data.getBytes());
            //readFile();

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exportFile.getAbsolutePath();
    }

    private String buildData() {
        return "";
    }

    private void initProgressBar() {
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(true);
        progressBar.setMessage("Exporting ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
    }

    private void appendParameter(StringBuffer stringBuffer, String key, String value) {
        if (value != null) {
            stringBuffer.append(String.format(DELIMITER + "%s: %s", key, value));
        } else {
            stringBuffer.append(String.format(DELIMITER + "%s: %s", key, "NA"));
        }
    }



    @Override
    protected String doInBackground(Void... voids) {
        return export();
    }

    @Override
    protected void onPostExecute(String result) {
        progressBar.dismiss();
        if(sendEmail){
            sendFileAsAttachment(result);
        }else{
            new AlertDialog.Builder(context)
                    .setTitle("Export")
                    .setMessage("Friends list exported to: "+result)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
           progressBar.setProgress(values[0]);
    }

    private void sendFileAsAttachment(String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "My facebook friends");
        intent.putExtra(Intent.EXTRA_TEXT, "Facebook friends attached.");
        Uri uri = Uri.parse("file://" + filePath);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
