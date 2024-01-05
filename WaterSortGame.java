import java.util.Arrays;
import java.util.Random;

public class WaterSortGame {
  public static Stack commands = new Stack(100);
  public static Stack redo = new Stack(100);
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
            commands.push("selectNext");
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
            commands.push("selectPrevious");
            return;
          }
        }
      }
    }
  }

  public void select(int bottleNumber) {
    Bottle selectedBottle = Bottle.getSelectedBottle(bottles);
    int preIndex = -1;
    if (selectedBottle != null) {
      preIndex = Bottle.getIndexOfSelectedBottle(bottles);
      selectedBottle.deSelect();
    }
    bottles[bottleNumber - 1].select();
    commands.push("select " + (preIndex) + " " + (bottleNumber - 1));
  }

  public void deSelect() {
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].getSelect()) {
        bottles[i].deSelect();
        commands.push("deSelect " + (i));
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
      commands.push("pour " + Bottle.getIndexOfSelectedBottle(bottles) + " " + (bottleNumber - 1) + " " + limit);
      return true;
    }
    if (bottles[bottleNumber - 1].isEmpty()) {
      int limit = selectedBottle.getTop().getHeight();
      for (int i = 0; i < limit; i++) {
        ColorBlock CB = selectedBottle.pop();
        CB.setHeight(i + 1);
        bottles[bottleNumber - 1].push(CB);
      }
      commands.push("pour " + Bottle.getIndexOfSelectedBottle(bottles) + " " + (bottleNumber - 1) + " " + limit);
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
    commands.push("pour " + Bottle.getIndexOfSelectedBottle(bottles) + " " + (bottleNumber - 1) + " " + limit);
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
      commands.push("addEmptyBottle");
    }
  }

  public void swap(int bottleNumber) {
    int indexSelectedBottle = Bottle.getIndexOfSelectedBottle(bottles);

    if (indexSelectedBottle == bottleNumber - 1) {
      return;
    }

    Bottle temp1 = new Bottle(bottles[indexSelectedBottle].getSize());
    Bottle temp2 = new Bottle(bottles[bottleNumber - 1].getSize());
    Bottle temp3 = new Bottle(bottles[indexSelectedBottle].getSize());
    Bottle temp4 = new Bottle(bottles[bottleNumber - 1].getSize());

    for (; !bottles[indexSelectedBottle].isEmpty();) {
      temp1.push(bottles[indexSelectedBottle].pop());
    }
    for (; !bottles[bottleNumber - 1].isEmpty();) {
      temp2.push(bottles[bottleNumber - 1].pop());
    }
    for (; !temp1.isEmpty();) {
      temp3.push(temp1.pop());
    }
    for (; !temp2.isEmpty();) {
      temp4.push(temp2.pop());
    }

    bottles[indexSelectedBottle] = temp4;
    bottles[bottleNumber - 1] = temp3;
    commands.push("swap " + indexSelectedBottle + " " + (bottleNumber - 1));
  }

  public void replaceColor(String firstColor, String secondColor) {
    for (int i = 0; i < colors.length; i++) {
      if (colors[i].equals(secondColor)) {
        return;
      }
    }
    for (int i = 0; i < colors.length; i++) {
      if (colors[i].equals(firstColor)) {
        Bottle temp = new Bottle(maxBottleSize);
        for (int j = 0; j < bottles.length; j++) {
          if (!bottles[j].isEmpty()) {
            for (int k = 0; k < bottles[j].getColorBlocksCount(); k++) {
              ColorBlock CB = bottles[j].pop();
              if (CB.getColor().equals(firstColor)) {
                CB.setColor(secondColor);
              }
              temp.push(CB);
            }
            for (int l = 0; l < temp.getColorBlocksCount(); l++) {
              bottles[j].push(temp.pop());
            }
          }
        }
        commands.push("replaceColor " + firstColor + " " + secondColor);
        break;
      }
    }
  }

  public void undo() {
    if (!commands.isEmpty()) {
      String cmd = commands.pop();
      redo.push(cmd);
      if (cmd.split(" ")[0].equals("selectNext")) {
        selectPrevious();
        commands.pop();
      } else if (cmd.split(" ")[0].equals("selectPrevious")) {
        selectNext();
        commands.pop();
      } else if (cmd.split(" ")[0].equals("addEmptyBottle")) {
        perm = 0;
        bottles = Arrays.copyOf(bottles, bottles.length - 1);
      } else if (cmd.split(" ")[0].equals("deSelect")) {
        select(Integer.parseInt(cmd.split(" ")[1]));
        commands.pop();
      } else if (cmd.split(" ")[0].equals("select")) {
        if (Integer.parseInt(cmd.split(" ")[1]) == -1) {
          deSelect();
          commands.pop();
        } else {
          select(Integer.parseInt(cmd.split(" ")[1]) + 1);
          commands.pop();
        }
      } else if (cmd.split(" ")[0].equals("swap")) {
        select(Integer.parseInt(cmd.split(" ")[1]) + 1);
        commands.pop();
        swap(Integer.parseInt(cmd.split(" ")[2]) + 1);
        commands.pop();
      } else if (cmd.split(" ")[0].equals("replaceColor")) {
        replaceColor(cmd.split(" ")[1], cmd.split(" ")[2]);
        commands.pop();
      } else if (cmd.split(" ")[0].equals("pour")) {
        for (int i = 0; i < Integer.parseInt(cmd.split(" ")[3]); i++) {
          bottles[Integer.parseInt(cmd.split(" ")[1])].push(bottles[Integer.parseInt(cmd.split(" ")[2])].pop());
          bottles[Integer.parseInt(cmd.split(" ")[1])].getTop().setHeight(i);
        }
      }
    }
  }

  public void redo() {
    if (!redo.isEmpty()) {
      String cmd = redo.pop();
      if (cmd.split(" ")[0].equals("selectNext")) {
        selectNext();
      } else if (cmd.split(" ")[0].equals("selectPrevious")) {
        selectPrevious();
      } else if (cmd.split(" ")[0].equals("addEmptyBottle")) {
        addEmptyBottle();
      } else if (cmd.split(" ")[0].equals("deSelect")) {
        deSelect();
      } else if (cmd.split(" ")[0].equals("select")) {
        select(Integer.parseInt(cmd.split(" ")[2]) + 1);
      } else if (cmd.split(" ")[0].equals("swap")) {
        swap(Integer.parseInt(cmd.split(" ")[2]) + 1);
      } else if (cmd.split(" ")[0].equals("replaceColor")) {
        replaceColor(cmd.split(" ")[1], cmd.split(" ")[2]);
      } else if (cmd.split(" ")[0].equals("pour")) {
        pour(Integer.parseInt(cmd.split(" ")[2]) + 1);
      }
    }
  }
}
