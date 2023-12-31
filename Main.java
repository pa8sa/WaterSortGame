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

    System.out.println("easy or hard ?");
    scanner.nextLine();
    String EZHD = scanner.nextLine();

    // scanner.nextLine();
    System.out.println();

    String input;
    String command;
    int number;

    wsg.displayBottles();

    // scanner.nextLine();

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
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("display")) {
        wsg.displayBottles();
      } else if (command.equals("deSelect")) {
        wsg.deSelect();
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("selectNext")) {
        wsg.selectNext();
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("selectPrevious")) {
        wsg.selectPrevious();
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("pour") && number != -1) {
        wsg.pour(number);
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("addEmptyBottle")) {
        wsg.addEmptyBottle();
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("swap") && number != -1) {
        wsg.swap(number);
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("replaceColor")) {
        wsg.replaceColor(input.split(" ")[1], input.split(" ")[2]);
        WaterSortGame.redo.setEmpty();
        wsg.displayBottles();
      } else if (command.equals("undo")) {
        if (EZHD.equals("hard") && WaterSortGame.undoPerm > 4) {
          System.out.println("not allowed");
        } else {
          wsg.undo();
          wsg.displayBottles();
          WaterSortGame.undoPerm++;
        }
      } else if (command.equals("redo")) {
        if (EZHD.equals("hard") && WaterSortGame.undoPerm > 4) {
          System.out.println("not allowed");
        } else {
          wsg.redo();
          wsg.displayBottles();
          WaterSortGame.undoPerm++;
        }
      } else if (command.equals("play")) {
        wsg.play();
        wsg.displayBottles();
      } else if (command.equals("end")) {
        break;
      }
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