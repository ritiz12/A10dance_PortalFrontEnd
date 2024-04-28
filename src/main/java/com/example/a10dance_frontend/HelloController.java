package com.example.a10dance_frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;

public class HelloController {

    public Label record1;
    public Label record2;
    public Label record3;
    public Label record1Hour;
    public Label record2Hour;
    public Label record3Hour;

    @FXML
    private Button hourButton;

    @FXML
    private void initialize() {
        try {
            callApi();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        hourButton.setOnAction(event -> {
            HourController hourController = new HourController();
            hourController.showHourView();
        });
    }

    private void callApi() throws IOException, JSONException {

        URL url = new URL("http://localhost:8080/attendance/monthlyRecord");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        // Get the response code
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray monthlyRecords = jsonResponse.getJSONArray("monthlyRecord");

            if (monthlyRecords.length() > 0) {
                JSONObject firstRecord1 = monthlyRecords.getJSONObject(0);
                JSONObject firstRecord2 = monthlyRecords.getJSONObject(0);
                String month1 = firstRecord1.getString("month");
                String totalHour = firstRecord2.getString("totalMonthlyWorkingTime");
                record1.setText(month1);
                record1Hour.setText(totalHour + "h");
            }
            if (monthlyRecords.length() > 1) {
                JSONObject secondRecord1 = monthlyRecords.getJSONObject(1);
                JSONObject secondRecord2 = monthlyRecords.getJSONObject(1);
                String month2 = secondRecord1.getString("month");
                String totalHour = secondRecord2.getString("totalMonthlyWorkingTime");
                record2.setText(month2);
                record2Hour.setText(totalHour + "h");
            }
            if (monthlyRecords.length() > 2) {
                JSONObject thirdRecord = monthlyRecords.getJSONObject(2);
                JSONObject thirdRecord2 = monthlyRecords.getJSONObject(2);
                String month3 = thirdRecord.getString("month");
                String totalHour = thirdRecord.getString("totalMonthlyWorkingTime");

                record3.setText(month3);
                record3Hour.setText(totalHour + "h");
            }
        }
        else {

            System.err.println("API Request Failed: " + responseCode);
        }



        connection.disconnect();
    }
}
