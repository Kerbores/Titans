package club.zhcs.jxls.entity;

public class Point
{
  private int x;
  private int y;

  public int getX()
  {
    return this.x;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public int getY()
  {
     return this.y;
  }

  public Point()
  {
  }

  public Point(int x, int y)
  {
    this.x = x;
     this.y = y;
  }

  public void setY(int y)
  {
     this.y = y;
  }
}