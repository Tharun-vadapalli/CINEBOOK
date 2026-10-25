import java.util.*;

import java.io.*;



public class Cinebook {



  static Scanner sc = new Scanner(System.in);



  // Store movies using ArrayList
  static ArrayList<String> movies = new ArrayList<>();



  // Store selected seats using LinkedList

  static LinkedList<String> selectedSeats = new LinkedList<>();



  // Store booked seats using HashMap

  static HashMap<String, Boolean> bookedSeats = new HashMap<>();



  static String selectedMovie;

  static String selectedTheater;

  static String selectedTiming;



  static final int TICKET_PRICE = 250;

  static final int CONVENIENCE_FEE = 60;



  public static final String FILE_NAME = "users.txt";



  public static void main(String[] args) {



     initializeMovies();
     initializeSeats();

System.out.println("==============================================");
    System.out.println("    CINEBOOK MOVIE TICKET BOOKING");


System.out.println("==============================================");



    System.out.println("\nWelcome to CINEBOOK!");
    System.out.println("Movie Ticket Booking Application");



    // STEP 1 - Login

    if (!login()) {

        System.out.println("\nThank you for using CINEBOOK!");

        sc.close();

        return;

    }



    // STEP 2 - Display Movies

    displayMovies();



    // STEP 3 - Select Movie

    selectMovie();



    // STEP 4 - Select Theater & Timing

    selectTheaterAndTiming();



    // STEP 5 - Display Available Seats

    displaySeats();



    // STEP 6 - Select Seats

    selectSeats();



    // Check whether seats were selected

    if (selectedSeats.isEmpty()) {

        System.out.println("\nNo seats selected.");

        System.out.println("Booking Cancelled.");

        sc.close();

        return;

    }



    // STEP 7 - Booking Details

    displayBookingDetails();



    // STEP 8 - Payment

    if (payment()) {



        // STEP 9 - Confirmation

        confirmation();



    } else {

        System.out.println("\nPayment Failed!");

        System.out.println("Booking Cancelled.");

    }



    sc.close();

}



// =========================================================

// INITIALIZE MOVIES

// =========================================================



public static void initializeMovies() {



    movies.clear();



    movies.add("Jalsa");

    movies.add("RRR");

    movies.add("Pushpa 2");

    movies.add("Jawan");

    movies.add("Panja");

    movies.add("Leo");

    movies.add("Spider Man Brand New Day");

    movies.add("Spirit");

    movies.add("Odyssey");

    movies.add("Obsession");

}



// =========================================================

// INITIALIZE SEATS

// =========================================================



public static void initializeSeats() {



    bookedSeats.clear();



    for (char row = 'A'; row <= 'D'; row++) {



      for (int i = 1; i <= 15; i++) {

              String seat = row + String.valueOf(i);



              // false means available

              bookedSeats.put(seat, false);

          }

      }



      // Already booked seats

      bookedSeats.put("A2", true);

      bookedSeats.put("A5", true);

      bookedSeats.put("B1", true);

      bookedSeats.put("B4", true);
      bookedSeats.put("C3", true);

      bookedSeats.put("C6", true);

      bookedSeats.put("D2", true);

      bookedSeats.put("D7", true);

  }



  // =========================================================

  // STEP 1 - LOGIN SYSTEM

  // =========================================================



  public static boolean login() {



      while (true) {



          System.out.println("\n================ SYSTEM MENU ================");

      System.out.println("1. Register");

      System.out.println("2. Login");

      System.out.println("3. Exit");


System.out.println("==============================================");



      System.out.print("Choose an option: ");

      String choice = sc.nextLine().trim();



      switch (choice) {



        case "1":

           registerUser();

           break;



        case "2":

           if (loginUser()) {

                return true;

           }

           break;



        case "3":

           System.out.println("\nExiting application.");

           return false;



        default:

           System.out.println(

                "Invalid choice. Please select 1, 2, or 3."

           );

        }

    }

}



// =========================================================

// REGISTER USER

// =========================================================



private static void registerUser() {



    System.out.println("\n------------- REGISTER -------------");



    System.out.print("Enter new username: ");
    String username = sc.nextLine().trim();



    System.out.print("Enter new password: ");

    String password = sc.nextLine().trim();



    if (username.isEmpty() || password.isEmpty()) {



        System.out.println(

             "Error: Username and password cannot be empty."

        );



        return;

    }



    try (BufferedWriter writer =
             new BufferedWriter(new FileWriter(FILE_NAME, true))) {

        writer.write(username + "," + password);

        writer.newLine();



        System.out.println(

             "Registration successful!"

        );



        System.out.println(

             "Credentials saved to file."

        );



    } catch (IOException e) {



        System.out.println(

             "Error writing to file: " + e.getMessage()

        );

    }

}



// =========================================================

// LOGIN USER

// =========================================================



private static boolean loginUser() {



    System.out.println("\n------------- LOGIN MODULE -------------");



    System.out.print("Username: ");

String enteredUser = sc.nextLine().trim();



System.out.print("Password: ");
String enteredPass = sc.nextLine().trim();



boolean isValid =

   validateCredentials(enteredUser, enteredPass);



if (isValid) {



   System.out.println(

        "\n[SUCCESS] Login successful!"

   );



   System.out.println(

        "Proceeding to movie booking..."

   );



   return true;



} else {



   System.out.println(

        "\n[ERROR] Invalid username or password."

   );



   System.out.println(

        "Access denied."

   );

        return false;

    }
}



// =========================================================

// VALIDATE LOGIN CREDENTIALS

// =========================================================



private static boolean validateCredentials(

        String username,

        String password) {



    File file = new File(FILE_NAME);



    if (!file.exists()) {

        System.out.println(

             "No registered users found."
        );



        return false;

    }



    try (BufferedReader reader =

             new BufferedReader(new FileReader(file))) {



        String line;



        while ((line = reader.readLine()) != null) {

             String[] parts = line.split(",", 2);



             if (parts.length == 2) {



                 String storedUser = parts[0].trim();

                 String storedPass = parts[1].trim();



                 if (storedUser.equals(username)

                       && storedPass.equals(password)) {



                     return true;

                 }
             }

        }



    } catch (IOException e) {



        System.out.println(

             "Error reading file: " + e.getMessage()

        );

    }



    return false;

}



// =========================================================

// STEP 2 - DISPLAY MOVIES
// =========================================================

static void displayMovies() {



    System.out.println("\n------------- NOW SHOWING -------------");



    for (int i = 0; i < movies.size(); i++) {



         System.out.println(
              (i + 1) + ". " + movies.get(i)

         );

    }

}



// =========================================================

// STEP 3 - SELECT MOVIE

// =========================================================



static void selectMovie() {



    System.out.println(

         "\n------------- SELECT MOVIE -------------"

    );



    System.out.print("Enter Movie Number: ");



    int choice = readInt();



    if (choice >= 1 && choice <= movies.size()) {

         selectedMovie = movies.get(choice - 1);



    } else {



         System.out.println(

              "Invalid movie choice!"

         );



         selectedMovie = movies.get(0);



         System.out.println(

              "Default movie selected: " + selectedMovie

         );
    }



    System.out.println(

         "Selected Movie: " + selectedMovie

    );
}



// =========================================================

// STEP 4 - THEATER AND TIMING

// =========================================================



static void selectTheaterAndTiming() {



    System.out.println(

         "\n------------- SELECT THEATER -------------"
    );

System.out.println("1. Blaji Cinema");

System.out.println("2. Sri Satyanarayana");
System.out.println("3. S.V.c");

System.out.println("4. V-Max");



System.out.print("Enter Theater Number: ");



int theaterChoice = readInt();



switch (theaterChoice) {



  case 1:

     selectedTheater = "Blaji Cinema";

     break;



  case 2:

     selectedTheater = "Sri Satyanarayana";
     break;



  case 3:

     selectedTheater = "S.V.c";

     break;



  default:

     selectedTheater = "V-Max";



     System.out.println(
       "Invalid choice. PVR Cinemas selected."

       );

}



System.out.println(

     "\n------------- SELECT TIMING -------------"

);



System.out.println("1. 11:00 AM");
System.out.println("2. 02:00 PM");

System.out.println("3. 06:30 PM");

System.out.println("4. 09:00 PM");

System.out.println("5. 10:40 PM");



System.out.print("Enter Timing Number: ");



int timingChoice = readInt();



switch (timingChoice) {



     case 1:

       selectedTiming = "11:00 AM";

       break;



     case 2:

       selectedTiming = "02:00 PM";

       break;



     case 3:
       selectedTiming = "06:30 PM";

           break;



         case 4:
           selectedTiming = "09:00 PM";

           break;



         case 5:

           selectedTiming = "10:40 PM";
           break;



         default:

           selectedTiming = "04:00 PM";



           System.out.println(

                "Invalid choice. 04:00 PM selected."

           );

    }



    System.out.println(

         "\nTheater : " + selectedTheater

    );



    System.out.println(
         "Timing : " + selectedTiming

    );

}



// =========================================================
// STEP 5 - DISPLAY SEATS

// =========================================================



static void displaySeats() {



  System.out.println(

       "\n============= SEAT LAYOUT ============="

  );



  System.out.println("                   SCREEN");

  System.out.println("-----------------------------------------");



  for (char row = 'A'; row <= 'D'; row++) {



       for (int i = 1; i <= 15; i++) {



         String seat =

             row + String.valueOf(i);



         if (bookedSeats.get(seat)) {



             System.out.print("[XX] ");



         } else {



             System.out.printf(

                  "[%s] ",

                  seat

             );
         }

     }



     System.out.println();
}



int available = 0;



for (boolean status : bookedSeats.values()) {



     if (!status) {

         available++;

     }

}



System.out.println(

     "-----------------------------------------"

);



System.out.println(

     "Total Seats        : " + bookedSeats.size()

);



System.out.println(
     "Available Seats : " + available

);



System.out.println(

     "XX = Already Booked"

);

}



// =========================================================
// STEP 6 - SELECT SEATS

// =========================================================



static void selectSeats() {



    System.out.println(

         "\n------------- SELECT SEATS -------------"

    );



    System.out.print(
         "How many seats do you want? "

    );



    int numberOfSeats = readInt();



    if (numberOfSeats <= 0) {



         System.out.println(

              "Invalid number of seats."

         );



         return;

    }



    int availableSeats = 0;

for (boolean status : bookedSeats.values()) {



    if (!status) {

         availableSeats++;

    }

}



if (numberOfSeats > availableSeats) {



    System.out.println(

         "Only " + availableSeats

         + " seats are available."

    );



    return;

}



for (int i = 1; i <= numberOfSeats; i++) {



    System.out.print(

         "Enter Seat " + i + ": "

    );



    String seat =

         sc.nextLine().trim().toUpperCase();



    if (!bookedSeats.containsKey(seat)) {



         System.out.println(

         "Invalid seat number!"

    );



    i--;

    continue;

}



if (bookedSeats.get(seat)) {



    System.out.println(

         "Seat " + seat

         + " is already booked!"

    );



    i--;

    continue;

}



if (selectedSeats.contains(seat)) {



    System.out.println(

         "Seat already selected!"

    );



    i--;

    continue;

}



selectedSeats.add(seat);

         System.out.println(

              "Seat " + seat
              + " selected successfully."

         );

    }



    System.out.println(
         "\nSelected Seats: " + selectedSeats

    );

}



// =========================================================
// STEP 7 - BOOKING DETAILS

// =========================================================



static void displayBookingDetails() {



    int tickets = selectedSeats.size();



    int ticketAmount =

         tickets * TICKET_PRICE;



    int totalAmount =

         ticketAmount + CONVENIENCE_FEE;



    System.out.println(

         "\n=============================================="
    );

System.out.println(

     "          BOOKING DETAILS"

);



System.out.println(

     "=============================================="

);



System.out.println(

     "Movie        : " + selectedMovie

);



System.out.println(

     "Theater      : " + selectedTheater

);



System.out.println(
     "Timing       : " + selectedTiming

);



System.out.println(

     "Seats        : " + selectedSeats
);



System.out.println(

     "Tickets      : " + tickets

);

    System.out.println(

         "Price/Ticket      : Rs." + TICKET_PRICE

    );



    System.out.println(

         "Ticket Amount : Rs." + ticketAmount

    );



    System.out.println(

         "Convenience Fee : Rs." + CONVENIENCE_FEE

    );



    System.out.println(
         "----------------------------------------------"

    );



    System.out.println(

         "Total Amount        : Rs." + totalAmount
    );



    System.out.println(

         "=============================================="

    );

}



// =========================================================

// STEP 8 - PAYMENT

// =========================================================

static boolean payment() {



  System.out.println(
       "\n------------- PAYMENT OPTIONS -------------"

  );



  System.out.println(

       "1. Credit / Debit Card"
  );



  System.out.println(

       "2. UPI / PhonePe / GPay"

  );



  System.out.println(

       "3. Net Banking"

  );



  System.out.println(

       "4. Cash on Delivery"

  );



  System.out.print(
       "Select Payment Method: "

  );



  int paymentChoice = readInt();



  String paymentMethod;

switch (paymentChoice) {



  case 1:

    paymentMethod =

         "Credit / Debit Card";

    break;



  case 2:

    paymentMethod =

         "UPI / PhonePe / GPay";

    break;



  case 3:

    paymentMethod =

         "Net Banking";

    break;



  case 4:

    paymentMethod =

         "Cash on Delivery";

    break;



  default:



    System.out.println(

         "Invalid payment option!"

    );

           return false;

    }



    System.out.println(

         "\nPayment Method: " + paymentMethod

    );



    System.out.println(
         "Processing Payment..."

    );



    // Simulating successful payment

    System.out.println(

         "Payment Successful!"

    );



    return true;

}



// =========================================================

// STEP 9 - CONFIRMATION

// =========================================================



static void confirmation() {



    // Mark selected seats as booked

    for (String seat : selectedSeats) {



         bookedSeats.put(seat, true);

}



// Generate booking ID
int randomNumber =

     100000 + new Random().nextInt(900000);



String bookingId =

     "CB" + randomNumber;



int totalAmount =

     (selectedSeats.size() * TICKET_PRICE)

     + CONVENIENCE_FEE;



System.out.println(

     "\n=============================================="

);



System.out.println(
     "        BOOKING CONFIRMED!"

);



System.out.println(

     "=============================================="
);



System.out.println(

     "Movie     : " + selectedMovie

);

System.out.println(

     "Theater       : " + selectedTheater

);



System.out.println(

     "Timing       : " + selectedTiming

);



System.out.println(

     "Seats        : " + selectedSeats

);



System.out.println(
     "Booking ID : " + bookingId

);



System.out.println(

     "Total Amount: Rs." + totalAmount
);



System.out.println(

     "----------------------------------------------"

);



System.out.println(

     "Thank you for booking with CINEBOOK!"

);



System.out.println(

         "Enjoy the Movie!"

    );



    System.out.println(

         "=============================================="

    );

}



// =========================================================

// SAFE INTEGER INPUT

// =========================================================



static int readInt() {



    while (true) {



         String input = sc.nextLine().trim();



         try {



             return Integer.parseInt(input);



         } catch (NumberFormatException e) {



             System.out.print(

                  "Please enter a valid number: "

             );

         }
    }

    }

}
