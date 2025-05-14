import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_URL = "https://open.er-api.com/v6/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter base currency code (e.g., USD): ");
        String baseCurrency = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter target currency code (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();
        scanner.close();

        try {
            String requestUrl = API_URL + baseCurrency;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            // Find the target currency manually
            String search = "\"" + targetCurrency + "\":";
            int index = responseBody.indexOf(search);

            if (index != -1) {
                int start = index + search.length();
                int end = responseBody.indexOf(",", start);
                if (end == -1) {
                    end = responseBody.indexOf("}", start);
                }
                String rateStr = responseBody.substring(start, end).trim();
                double rate = Double.parseDouble(rateStr);

                double convertedAmount = amount * rate;
                System.out.printf("\n%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
            } else {
                System.out.println("Currency not found: " + targetCurrency);
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing exchange rate.");
        }
    }
}
