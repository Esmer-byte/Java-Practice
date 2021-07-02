import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class second {

    private static HttpURLConnection connection;

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader reader;
        String name = sc.nextLine();
        String line;
        StringBuffer responseContent = new StringBuffer();
        URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + name + "&apikey=HIOOYZ15LELZLW0A");
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        System.out.println(connection.getResponseMessage());
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if (status < 300 && status > 199) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            responseContent.append(" [");
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }
            responseContent.append("]");//my Json file did not had them, it is a bit of a more complex json file, the json array needs to receive a string that has
            // "[" and "]" at the beginning and the end of the string in order to be created successfully, so I had to add them manually
            parse(responseContent.toString());
            reader.close();
        }
    }

    public static void parse(String responseBody) {
        JSONArray prices = new JSONArray(responseBody);
        JSONObject meta = (JSONObject) prices.get(0);
        JSONObject meta1 = (JSONObject) meta.get("Time Series (Daily)");
        JSONObject meta2 = (JSONObject) meta1.get("2021-07-01");
        String price = meta2.getString("1. open");
        System.out.println(price);

    }
}


