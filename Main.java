public class Main {
  public static void main(String[] args) {
    clearScreen();
    WaterSortGame wsg = new WaterSortGame(new String[] { "blue", "red", "green", "yellow" }, 4);
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