import java.util.Random;

public class WaterSortGame {
  private static int perm = 0;
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
  }

  public void displayBottles() {
    Bottle[] temp1 = new Bottle[bottles.length];
    Bottle[] temp2 = new Bottle[bottles.length];
    for (int i = 0; i < bottles.length; i++) {
      temp1[i] = new Bottle(bottles[0].getSize());
      temp2[i] = new Bottle(bottles[0].getSize());
    }
    for (int i = 0; i < bottles.length; i++) {
      int limit = bottles[i].getColorBlocksCount();
      for (int j = 0; j < limit; j++) {
        temp1[i].push(bottles[i].pop());
      }
    }
    for (int i = 0; i < bottles.length; i++) {
      int limit = temp1[i].getColorBlocksCount();
      for (int j = 0; j < limit; j++) {
        ColorBlock CB = temp1[i].pop();
        bottles[i].push(CB);
        temp2[i].push(CB);
      }
    }
    for (int i = 0; i < temp2[0].getSize(); i++) {
      for (int j = 0; j < temp2.length; j++) {
        if (j == colors.length + 1 && i < maxBottleSize / 2) {
          System.out.print("\t");
        } else if (temp2[j].getSize() - temp2[j].getColorBlocksCount() - i > 0) {
          System.out.print(ColorBlock.getEmptyBlock() + "\t");
        } else {
          System.out.print(temp2[j].pop().getBlock() + "\t");
        }
      }
      System.out.println();
    }
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].getSelect()) {
        System.out.println("╚═╝");
        break;
      } else {
        System.out.print("\t");
      }
    }
    System.out.println();
  }

  public void selectNext() {
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].getSelect()) {
        for (int j = (i + 1) % bottles.length; j != i; j = (j + 1) % bottles.length) {
          if ((!bottles[j].isComplete() && !bottles[j].isEmpty()) || bottles[j].getSize() != maxBottleSize) {
            bottles[i].deSelect();
            bottles[j].select();
            return;
          }
        }
      }
    }
  }

  public void selectPrevious() {
    for (int i = bottles.length - 1; i > -1; i--) {
      if (bottles[i].getSelect()) {
        for (int j = (i - 1 + bottles.length) % bottles.length; j != i; j = (j - 1 + bottles.length) % bottles.length) {
          if (!bottles[j].isComplete() && !bottles[j].isEmpty() && bottles[j].getSize() == maxBottleSize) {
            bottles[i].deSelect();
            bottles[j].select();
            return;
          }
        }
      }
    }
  }

  public void select(int bottleNumber) {
    Bottle selectedBottle = Bottle.getSelectedBottle(bottles);
    if (selectedBottle != null) {
      selectedBottle.deSelect();
    }
    bottles[bottleNumber - 1].select();
  }

  public void deSelect() {
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].getSelect()) {
        bottles[i].deSelect();
        return;
      }
    }
  }

  public boolean pour(int bottleNumber) {
    if (bottles[bottleNumber - 1].isFull()) {
      return false;
    }
    Bottle selectedBottle = Bottle.getSelectedBottle(bottles);
    if (selectedBottle.isEmpty()) {
      return false;
    }
    if (bottles[bottleNumber - 1].isEmpty() && bottles[bottleNumber - 1].getSize() != maxBottleSize
        && selectedBottle.getTop().getHeight() <= bottles[bottleNumber - 1].getSize()) {
      int limit = selectedBottle.getTop().getHeight();
      for (int i = 0; i < limit; i++) {
        ColorBlock CB = selectedBottle.pop();
        CB.setHeight(i + 1);
        bottles[bottleNumber - 1].push(CB);
      }
      return true;
    }
    if (bottles[bottleNumber - 1].isEmpty()) {
      int limit = selectedBottle.getTop().getHeight();
      for (int i = 0; i < limit; i++) {
        ColorBlock CB = selectedBottle.pop();
        CB.setHeight(i + 1);
        bottles[bottleNumber - 1].push(CB);
      }
      return true;
    }
    if (bottles[bottleNumber - 1] == selectedBottle) {
      return false;
    }
    if (selectedBottle.getTop().getColor() != bottles[bottleNumber - 1].getTop().getColor()) {
      return false;
    }
    if (selectedBottle.getTop().getHeight()
        + bottles[bottleNumber - 1].getColorBlocksCount() > bottles[bottleNumber - 1]
            .getSize()) {
      return false;
    }
    int limit = selectedBottle.getTop().getHeight();
    for (int i = 0; i < limit; i++) {
      ColorBlock CB = selectedBottle.pop();
      CB.setHeight(bottles[bottleNumber - 1].getTop().getHeight() + 1);
      bottles[bottleNumber - 1].push(CB);
    }
    if (hasWon()) {
      System.out.println("END");
    }
    return true;
  }

  public boolean hasWon() {
    for (int i = 0; i < bottles.length; i++) {
      if (!bottles[i].isEmpty() && bottles[i].getTop().getHeight() != bottles[i].getSize()) {
        return false;
      }
    }
    return true;
  }

  public void addEmptyBottle() {
    if (perm == 0) {
      Bottle[] newBottles = new Bottle[bottles.length + 1];
      Bottle temp = new Bottle(maxBottleSize);

      for (int i = 0; i < colors.length + 1; i++) {
        newBottles[i] = new Bottle(maxBottleSize);
      }
      newBottles[bottles.length] = new Bottle(maxBottleSize / 2);

      for (int i = 0; i < bottles.length; i++) {
        for (; !bottles[i].isEmpty();) {
          temp.push(bottles[i].pop());
        }
        for (; !temp.isEmpty();) {
          newBottles[i].push(temp.pop());
        }
      }

      bottles = newBottles;

      perm++;
    }
  }
}
