package toolbox;

public class calculator {

    double EarthRadius = 6378.388;

    public double calcDistance(double lon1, double lat1, double lon2, double lat2){
        // Calculates the Distance between 2 Points by using the "seitencosnussatz"
        // To get the Distance in Meters multiply with the Earth Radiant
        // Java is Calculating in Degree, so convert to it by using pi/180
        // Formula by https://www.kompf.de/gps/distcalc.html
        double dist;
        dist = EarthRadius * 1000 * 3.1415 / 180  * Math.acos(Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));

        return dist;
    }


}
