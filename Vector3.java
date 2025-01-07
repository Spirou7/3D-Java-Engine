public class Vector3 {
    public double x;
    public double y;
    public double z;
  
    public Vector3(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  
    public Vector3(Vector3 copy) {
      this.x = copy.x;
      this.y = copy.y;
      this.z = copy.z;
    }

    public String toString(){
      return "{" + x + ", " + y + ", " + z + "}";
    }
  
    public static Vector3 addVectors(Vector3 a, Vector3 b) {
      return new Vector3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vector3 subtractVectors(Vector3 a, Vector3 b){
      return Vector3.addVectors(a, Vector3.multiplyVector(b, -1));
    }
  
    public static Vector3 multiplyVector(Vector3 a, double scale) {
      return new Vector3(a.x * scale, a.y * scale, a.z * scale);
    }
  
    public static Vector3 vectorFromAngle(Vector3 angle) {
      double distance = Math.cos(angle.x);
  
      double xDiff = Math.sin(angle.y) * distance;
      double yDiff = Math.sin(angle.x);
      double zDiff = Math.cos(angle.y) * distance;
  
      return new Vector3(xDiff, yDiff, zDiff);
    }
  
    public static Vector3 angleFromVector(Vector3 vect) {
      if (vect.x == 0 && vect.y == 0 && vect.z == 0) {
        return new Vector3(0, 0, 0);
      }
  
      double horzAngle = Math.atan(vect.x / vect.z);
  
      if (vect.z < 0 && vect.x < 0) {
        horzAngle = -Math.PI + horzAngle;
      } else if (vect.z < 0 && vect.x >= 0) {
        horzAngle = Math.PI + horzAngle;
      }
  
      if (vect.z == 0) {
        if (vect.x > 0) {
          horzAngle = Math.PI / 2;
        } else {
          horzAngle = -Math.PI / 2;
        }
      }
  
      double horzDistance = Math.sqrt((vect.x * vect.x) + (vect.z * vect.z));
  
      double vertAngle = Math.atan(vect.y / horzDistance);
  
      return new Vector3(vertAngle, horzAngle, 0);
    }
  
    // Returns a vector where:
    // the y value represents the horizontal angle between two vectors
    // the x value represents the vertical angle between two vectors
    public static Vector3 getAngleDifference(Vector3 a, Vector3 b) {
  
      // Issue is that the x-axis is being considered the 0 radian point, it should be
      // the z axis                          
  
      // tan angle = opp / adj
      double BhorzAngle = Math.atan(b.x / b.z);
      
      // IMPORTANT ISSUE: !!!!!!!!!!!!!
      // The camera is doing horizontal rotation first, so if something is SLIGHTLY behind the camera, it does the horizontal rotation
      if (b.z < 0) {
        if (b.x < 0) {  
          BhorzAngle = -Math.PI + Math.abs(BhorzAngle);
        } else {
          BhorzAngle = Math.PI - Math.abs(BhorzAngle);
        }
      }
      double AhorzAngle = Math.atan(a.x / a.z);
  
      if (a.z < 0) {
        if (a.x < 0) {
          AhorzAngle = -Math.PI + Math.abs(AhorzAngle);
        } else {
          AhorzAngle = Math.PI - Math.abs(AhorzAngle);
        }
      }
  
      // Find horizontal distance to the point
      double BhorzDistance = Math.sqrt(b.x * b.x + b.z * b.z);
      double AhorzDistance = Math.sqrt(a.x * a.x + a.z * a.z);
  
      double BvertAngle = Math.atan(b.y / BhorzDistance);
      double AvertAngle = Math.atan(a.y / AhorzDistance);
  
      return new Vector3(singleAngleSubtraction(BvertAngle, AvertAngle), singleAngleSubtraction(BhorzAngle, AhorzAngle), 0);
    }
  
    public static double singleAngleSubtraction(double angleB, double angleA) {
      if (Math.abs(angleA - angleB) > Math.PI) {
        // Make both positive
        angleA = (angleA < 0) ? 2 * Math.PI + angleA : angleA;
        angleB = (angleB < 0) ? 2 * Math.PI + angleB : angleB;
      }
  
      return angleB - angleA;
    }
  
    public static Vector3 makeAngle(Vector3 angle){
      Vector3 newAngle = new Vector3(angle.x, angle.y, angle.z);
  
      while(newAngle.x < 0){
        newAngle.x += Math.PI * 2; 
      }
      while(newAngle.x > Math.PI * 2){
        newAngle.x -= Math.PI * 2;
      }
  
      newAngle.y = newAngle.y;
      while(newAngle.y < 0){
        newAngle.y += Math.PI * 2; 
      }
      while(newAngle.y > Math.PI * 2){
        newAngle.y -= Math.PI * 2;
      }
  
      newAngle.z = newAngle.z;
      while(newAngle.z < 0){
        newAngle.z += Math.PI * 2; 
      }
      while(newAngle.z > Math.PI * 2){
        newAngle.z -= Math.PI * 2;
      }
  
      return newAngle;
    }
  }