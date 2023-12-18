import java.util.Random;

public class WaterSortGame {
  private String[] colors;
  private Bottle[] bottles;
  private int maxBottleSize;

  public WaterSortGame(String[] colors, int maxBottleSize) {
    this.colors = colors;
    this.maxBottleSize = maxBottleSize;
    this.bottles = new Bottle[colors.length + 1];
    initial();
    randomInitial();
  }

  public void initial() {
    for (int i = 0; i < colors.length + 1; i++) {
      bottles[i] = new Bottle(maxBottleSize);
    }
  }

  public void randomInitial() {
    Random random = new Random();
    for (int i = 0; i < colors.length; i++) {
      for (int j = 0; j < this.maxBottleSize; j++) {
        int randomBottle = random.nextInt(bottles.length - 1);
        if (bottles[randomBottle].isFull() || (!bottles[randomBottle].isEmpty()
            && bottles[randomBottle].getTop().getColor().equals(colors[i])
            && bottles[randomBottle].getTop().getHeight() == bottles[randomBottle].getSize() - 1)) {
          j--;
          continue;
        } else if (!bottles[randomBottle].isEmpty() && bottles[randomBottle].getTop().getColor().equals(colors[i])) {
          ColorBlock colorBlock = new ColorBlock(colors[i], bottles[randomBottle].getTop().getHeight() + 1);
          bottles[randomBottle].push(colorBlock);
        } else {
          ColorBlock colorBlock = new ColorBlock(colors[i], 1);
          bottles[randomBottle].push(colorBlock);
        }
      }
    }
    bottles[3].select();
    Bottle.displayBottles(bottles);
    Bottle.pour(5, bottles);
    Bottle.displayBottles(bottles);
  }
}
