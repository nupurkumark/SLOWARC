

public class Vector2 {
    public double x, y;
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
    public double length() {
        return Math.sqrt( x * x + y * y);
    }
    
    public double doublelengthSqr() {
        return x * x + y * y;
    }
    
    public Vector2 plus(Vector2 rhs) {
        return new Vector2(x + rhs.x, y + rhs.y);
    }
    
    public void add(Vector2 rhs) {
        x += rhs.x;
        y += rhs.y;
    }
    
    public Vector2 minus(Vector2 rhs) {
        return new Vector2(x - rhs.x, y - rhs.y);
    }
    
    public void subtract(Vector2 rhs) {
        x -= rhs.x;
        y -= rhs.y;
    }
    
    public Vector2 times(double rhs) {
        return new Vector2(x * rhs, y * rhs);
    }
    
    public void mul(double rhs) {
        x *=  rhs;
        y *=  rhs;
    }
    
    public void clampToLength(double maxL) {
        double magnitude = Math.sqrt(x * x + y * y);
        if (magnitude > maxL) {
            x *=  maxL / magnitude;
            y *=  maxL / magnitude;
        }
    }
    
    public void setToLength(double newL) {
        double magnitude = Math.sqrt(x * x + y * y);
        x *=  newL / magnitude;
        y *=  newL / magnitude;
    }
    
    public void normalize() {
        double magnitude = Math.sqrt(x * x + y * y);
        x /=  magnitude;
        y /=  magnitude;
    }
    
    public Vector2 normalized() {
        double magnitude = Math.sqrt(x * x + y * y);
        return new Vector2(x / magnitude, y / magnitude);
    }
    
    public double distanceTo(Vector2 rhs) {
        double dx = rhs.x - x;
        double dy = rhs.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }


    // Static methods
    public static Vector2 interpolate(Vector2 a, Vector2 b, double t) {
        return a.plus((b.minus(a)).times(t));
    }

    public static double interpolate(double a, double b, double t) {
        return a + ((b - a) * t);
    }

    public static double dot(Vector2 a, Vector2 b) {
        return a.x * b.x + a.y * b.y;
    }

    public static double cross(Vector2 a, Vector2 b) {
        return a.x * b.y - a.y * b.x;
    }

    public static Vector2 projAB(Vector2 a, Vector2 b) {
        return b.times(a.x * b.x + a.y * b.y);
    }

    public static Vector2 perpendicular(Vector2 a) {
        return new Vector2( -a.y, a.x);
    }

    private static Boolean ccw(Vector2 A, Vector2 B, Vector2 C) {
        return((C.y - A.y) * (B.x - A.x)) >= ((B.y - A.y) * (C.x - A.x));
    }
    
    public static Boolean lineLineIntersect(Vector2 A, Vector2 B, Vector2 C, Vector2 D) {
        return ccw(A,C,D) != ccw(B,C,D) && ccw(A,B,C) != ccw(A,B,D);
    }
    

};


