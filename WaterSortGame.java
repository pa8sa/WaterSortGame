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
    for (int i = 0; i < colors.length; i++) {
      bottles[i] = new Bottle(maxBottleSize);
    }
  }

  public void randomInitial() {
    Random random = new Random();
    for (int i = 0; i < colors.length; i++) {
      for (int j = 0; j < this.maxBottleSize; j++) {
        int randomBottle = random.nextInt(bottles.length - 1);
        if (bottles[randomBottle].isFull() || (!bottles[randomBottle].isEmpty() && bottles[randomBottle].getTop().getHeight() == bottles[randomBottle].getSize() - 1)) {
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
    display(bottles);
  }

  public static void display(Bottle[] bottles) {
    Bottle[] temp1 = new Bottle[bottles.length - 1];
    Bottle[] temp2 = new Bottle[bottles.length - 1];
    for (int i = 0; i < bottles.length - 1; i++) {
      temp1[i] = new Bottle(bottles[0].getSize());
      temp2[i] = new Bottle(bottles[0].getSize());
    }
    for (int i = 0; i < bottles.length - 1; i++) {
      for (int j = 0; j < bottles[i].getSize(); j++) {
        temp1[i].push(bottles[i].pop());
      }
    }
    for (int i = 0; i < bottles.length - 1; i++) {
      for (int j = 0; j < bottles[i].getSize(); j++) {
        ColorBlock CB = temp1[i].pop();
        bottles[i].push(CB);
        temp2[i].push(CB);
      }
    }
    for (int i = 0; i < temp2[0].getSize(); i++) {
      for (int j = 0; j < temp2.length; j++) {
        System.out.print(temp2[j].pop().getBlock() + "\t");
      }
      System.out.println();
    }
  }
}
