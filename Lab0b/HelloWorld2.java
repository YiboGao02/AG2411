package Lab0b;

public class HelloWorld2 {
    public static void main(String[] args) {
        String locName;
        double xCrd;
        double yCrd;
        Point aPoint;
        
        locName = args[0];
        xCrd = Double.parseDouble(args[1]);
        yCrd = Double.parseDouble(args[2]);
        aPoint = new Point(locName, xCrd, yCrd);
        
        System.out.println(locName + "'s distance from origin " + aPoint.getDistanceFromOrigin());
        System.out.println(locName + "'s distance to origin " + aPoint.getDistanceToOrigin());
        System.out.println(locName + "'s direction from origin " + aPoint.getDirectionFromOrigin());
        System.out.println(locName + "'s direction to origin " + aPoint.getDirectionToOrigin());
    }
}
