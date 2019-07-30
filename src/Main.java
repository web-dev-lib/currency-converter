import java.net.*;
import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static String USD;
    public static String EURO;

    public static void main(String[] args) throws IOException {
        init();
        readUserInput();
    }

    public static void init() throws IOException {
        String xml = runRequest();
        cutCurrency(xml);
    }

    private static void readUserInput() {
        Scanner myObj = new Scanner(System.in);
        Double amount;
        String currency;
        Double result = 0.0;

        System.out.println("-----Welcome to currency exchange-----");
        System.out.println("Enter amount and currency");
        amount = myObj.nextDouble();
        currency = myObj.nextLine().trim();

        System.out.println("You give as: " + amount + " of " + currency);

        if (amount == null || currency == null) {
            System.out.println("You write wrong input!");
        } else {
            result = calcAmount(amount, currency);
        }

        System.out.println("You get: " + result + " RUB");
    }

    private static Double calcAmount(Double amount, String currency) {
        Double result = 0.0;

        if (currency.equals("EURO")) {
            result =  amount * Double.parseDouble(EURO);
        }

        if (currency.equals("USD")) {
            result =  amount * Double.parseDouble(USD);
        }

        return result;
    }

    public static String getCurrentDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String outData = dateFormat.format(currentDate);
        return outData;
    }

    public static String runRequest() throws IOException {
        URL cbr = new URL("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + getCurrentDate());
        URLConnection cc = cbr.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(cc.getInputStream()));
        String inputLine;
        String outLine = "";

        while ((inputLine = in.readLine()) != null) {
            outLine += inputLine;
        }

        in.close();

        return outLine;
    }

    public static String cutCurrency(String xml) {
        USD = xml.substring(xml.indexOf("R01235") + 104, 1729).replace(",", ".");
        EURO = xml.substring(xml.indexOf("R01239")+ 98, 1863).replace(",", ".");

        return xml;
    }
}
