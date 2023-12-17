public class ColorBlock {
  private String reset = "\u001B[0m";
  private String colorCode;
  private String color;
  private int height;

  public ColorBlock(String color, int height) {
    if (color.equals("red")) {
      this.colorCode = "\u001B[31m";
      this.color = "red";
    } else if (color.equals("green")) {
      this.colorCode = "\u001B[32m";
      this.color = "green";
    } else if (color.equals("yellow")) {
      this.colorCode = "\u001B[33m";
      this.color = "yellow";
    } else if (color.equals("blue")) {
      this.colorCode = "\u001B[34m";
      this.color = "blue";
    } else if (color.equals("magneta")) {
      this.colorCode = "\u001B[35m";
      this.color = "magneta";
    } else if (color.equals("cyan")) {
      this.colorCode = "\u001B[36m";
      this.color = "cyan";
    } else if (color.equals("white")) {
      this.colorCode = "\u001B[37m";
      this.color = "white";
    } else if (color.equals("gray")) {
      this.colorCode = "\u001B[90m";
      this.color = "gray";
    }

    this.height = height;
  }

  public String getColor() {
    return color;
  }

  public int getHeight() {
    return height;
  }

  public void setColor(String color) {
    this.color = color;
    if (color.equals("red")) {
      this.colorCode = "\u001B[31m";
    } else if (color.equals("green")) {
      this.colorCode = "\u001B[32m";
    } else if (color.equals("yellow")) {
      this.colorCode = "\u001B[33m";
    } else if (color.equals("blue")) {
      this.colorCode = "\u001B[34m";
    } else if (color.equals("magneta")) {
      this.colorCode = "\u001B[35m";
    } else if (color.equals("cyan")) {
      this.colorCode = "\u001B[36m";
    } else if (color.equals("white")) {
      this.colorCode = "\u001B[37m";
    } else if (color.equals("gray")) {
      this.colorCode = "\u001B[90m";
    }
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public String getBlock() {
    return this.colorCode + "██ " + this.reset;
  }

  public String getEmptyBlock() {
    return "\u001B[30m" + "██ " + this.reset;
  }
}
