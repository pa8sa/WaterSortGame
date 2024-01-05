import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    clearScreen();

    System.out.println("Number Of Colors: ");
    int count = scanner.nextInt();

    String[] colors = new String[count];
    System.out.println("Colors: ");
    scanner.nextLine();
    for (int i = 0; i < count; i++) {
      colors[i] = scanner.nextLine();
    }

    System.out.println("Max Bottle Size: ");
    int maxBottleSize = scanner.nextInt();

    WaterSortGame wsg = new WaterSortGame(colors, maxBottleSize);

    String input;
    String command;
    int number;

    wsg.displayBottles();

    scanner.nextLine();

    while (true) {
      input = scanner.nextLine();
      System.out.println();
      command = input.split(" ")[0];

      if (input.split(" ").length > 1 && !command.equals("replaceColor")) {
        number = Integer.parseInt(input.split(" ")[1]);
      } else {
        number = -1;
      }

      if (command.equals("select") && number != -1) {
        wsg.select(number);
        wsg.displayBottles();
      } else if (command.equals("display")) {
        wsg.displayBottles();
      } else if (command.equals("deSelect")) {
        wsg.deSelect();
        wsg.displayBottles();
      } else if (command.equals("selectNext")) {
        wsg.selectNext();
        wsg.displayBottles();
      } else if (command.equals("selectPrevious")) {
        wsg.selectPrevious();
        wsg.displayBottles();
      } else if (command.equals("pour") && number != -1) {
        wsg.pour(number);
        wsg.displayBottles();
      } else if (command.equals("addEmptyBottle")) {
        wsg.addEmptyBottle();
        ;
        wsg.displayBottles();
      } else if (command.equals("swap") && number != -1) {
        wsg.swap(number);
        wsg.displayBottles();
      } else if (command.equals("replaceColor")) {
        wsg.replaceColor(input.split(" ")[1], input.split(" ")[2]);
        wsg.displayBottles();
      } else if (command.equals("undo")) {
        wsg.undo();
        wsg.displayBottles();
      } else if (command.equals("redo")) {
        wsg.redo();
        wsg.displayBottles();
      } else if (command.equals("end")) {
        break;
      }
      WaterSortGame.commands.display();
      WaterSortGame.redo.display();
    }
  }

  public static void clearScreen() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}