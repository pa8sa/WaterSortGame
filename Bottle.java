public class Bottle {
  private int size;
  private int top;
  private boolean isSelected;
  private ColorBlock[] array;

  public Bottle(int size) {
    top = -1;
    this.size = size;
    this.array = new ColorBlock[size];
    this.isSelected = false;
  }

  public int getSize() {
    return size;
  }

  public Boolean isEmpty() {
    if (this.top == -1) {
      return true;
    }
    return false;
  }

  public Boolean isFull() {
    if (this.top == size - 1) {
      return true;
    }
    return false;
  }

  public ColorBlock pop() {
    if (this.isEmpty()) {
      return null;
    }
    top--;
    return array[top + 1];
  }

  public Boolean push(ColorBlock CB) {
    if (this.isFull()) {
      return false;
    }
    top++;
    array[top] = CB;
    return true;
  }

  public ColorBlock getTop() {
    if (this.isEmpty()) {
      return null;
    }
    return array[top];
  }

  public int getColorBlocksCount() {
    return top + 1;
  }

  public static void displayBottles(Bottle... bottles) {
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
        if (temp2[j].getSize() - temp2[j].getColorBlocksCount() - i > 0) {
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
  }

  public boolean getSelect() {
    return isSelected;
  }

  public void select() {
    this.isSelected = true;
  }

  public void deSelect() {
    if (this.isSelected == true) {
      this.isSelected = false;
    }
  }

  public static void selectNext(Bottle... bottles) {
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].isSelected) {
        bottles[i].deSelect();
        bottles[(i + 1) % bottles.length].select();
        return;
      }
    }
  }

  public static void selectPrevious(Bottle... bottles) {
    for (int i = 0; i < bottles.length; i++) {
      if (bottles[i].isSelected) {
        bottles[i].deSelect();
        bottles[(i + bottles.length - 1) % bottles.length].select();
        return;
      }
    }
  }
}
