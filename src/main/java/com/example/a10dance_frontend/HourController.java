package com.example.a10dance_frontend;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HourController {

    public Button logInBtn;
    public Button logOutBtn;
    public Label date1;
    public Label total1;
    public Label InTime1;
    public Label outTime1;
    public Label date2;
    public Label total2;
    public Label in2;
    public Label out2;
    public Label date3;
    public Label total3;
    public Label in3;
    public Label out3;
    public Label date4;
    public Label total4;
    public Label in4;
    public Label out4;
    @FXML
    private Button homeButton;

    @FXML
    private void initialize() {
try
{
    callPreviousDataLoadApi();
}
catch (IOException | JSONException e) {
            e.printStackTrace();
            // Handle API call error
        }

        homeButton.setOnAction(event -> {
            // Handle home button action
        });

        logInBtn.setOnAction(event->
        {
            try {
               callLogInApi();
                logInBtn.setDisable(true);
                logOutBtn.setDisable(false);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        logOutBtn.setOnAction(event->
        {
            try
            {
                callLogOutApi();
                logInBtn.setDisable(false);
                logOutBtn.setDisable(true);
                callPreviousDataLoadApi();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }









    private void callPreviousDataLoadApi() throws IOException, JSONException {
        URL url = new URL("http://localhost:8080/attendance/previousRecord");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        StringBuilder response = null;
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response);
            reader.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray attendanceRecords = jsonResponse.getJSONArray("attendanceRecords");

            for (int i = 0; i < attendanceRecords.length(); i++) {
                JSONObject record = attendanceRecords.getJSONObject(i);
                String loginDate = record.getString("loginDate");
                String loginTime = record.getString("loginTime");
                String logOutTime = record.getString("logOutTime");
                String workingTime = record.getString("workingTime");

                // Set data into labels based on the index i
                switch (i) {
                    case 0:
                        date1.setText(loginDate);
                        InTime1.setText(loginTime);
                        outTime1.setText(logOutTime);
                        total1.setText(workingTime + "h");
                        break;
                    case 1:
                        date2.setText(loginDate);
                        in2.setText(loginTime );
                        out2.setText(logOutTime );
                        total2.setText(workingTime + "h");
                        break;
                    case 2:
                        date3.setText(loginDate);
                        in3.setText(loginTime);
                        out3.setText(logOutTime);
                        total3.setText(workingTime + "h");
                        break;
                    case 3:
                        date4.setText(loginDate);
                        in4.setText(loginTime);
                        out4.setText(logOutTime);
                        total4.setText(workingTime + "h");
                        break;

                    default:
                        break;
                }
            }
        } else {
            // Handle error response

        }
    }


    private void callLogOutApi() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String currentTime = dateFormat.format(new Date());
        String jsonData = "{ \"currentLogOutTime\": \"" + currentTime + "\" }";

        URL url = new URL("http://localhost:8080/attendance/logout");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);
        connection.getOutputStream().write(jsonData.getBytes());

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response);
            reader.close();

        } else {

        }

        connection.disconnect();
    }

    private String callLogInApi() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String currentTime = dateFormat.format(new Date());
        String jsonData = "{ \"currentLogInTime\": \"" + currentTime + "\" }";

        URL url = new URL("http://localhost:8080/attendance/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);
        connection.getOutputStream().write(jsonData.getBytes());

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println(response);
            reader.close();

        } else {

        }

        connection.disconnect();
        return currentTime;
    }

    public void showHourView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HourPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Hour View");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
