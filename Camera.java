import java.awt.event.KeyEvent;

public class Camera implements PhysicsObject {
  // Holds the position of the camera in terms of x, y, z
  public Vector3 position = new Vector3(0, 0, 0);

  // Holds the rotation of the camera in terms of pitch axis, yaw axis, roll axis
  public Vector3 rotation = new Vector3(0, 0, 0);

  // The y value that the camera cannot go below
  public double height = 1;

  // The current velocity in the y axis
  // Consider changing this to an actual Vector3 to store all other axis
  public double yVelocity = 0;

  // The gravitational acceleration of the camera
  public double acceleration = 0.05;

  // The FOV of the camera
  public double FOV = Math.PI / 4;

  // The sensitivity of the camera to the movement of the mouse
  public double turnSensitivity = 1;

  // Returns a Vector3 pointing in the direction the camera is looking
  public Vector3 forward() {
    return Vector3.vectorFromAngle(rotation);
  }

  // Returns a Vector3 pointing to the right of where the camera is looking
  public Vector3 right() {
    Vector3 vector = new Vector3(rotation.x, rotation.y + Math.PI / 2, rotation.z);
    return Vector3.vectorFromAngle(vector);
  }

  // Updates the physics of the camera
  public void physicsUpdate() {
    yVelocity -= acceleration;
    position.y += yVelocity;
    if (position.y < height) {
      position.y = height;
    }

    Vector3 forward = forward();
    System.out.println("Rotation: {" + rotation.x + ", " + rotation.y + ", " + rotation.z + "} Forward: {" + forward.x
        + ", " + forward.y + ", " + forward.z + "}");

    if (Screen.buttonsDown.contains(KeyEvent.VK_W)) {
      position = Vector3.addVectors(position, forward());
    } else if (Screen.buttonsDown.contains(KeyEvent.VK_S)) {
      position = Vector3.addVectors(position, Vector3.multiplyVector(forward(), -1));
    }
    if (Screen.buttonsDown.contains(KeyEvent.VK_D)) {
      position = Vector3.addVectors(position, right());
    } else if (Screen.buttonsDown.contains(KeyEvent.VK_A)) {
      position = Vector3.addVectors(position, Vector3.multiplyVector(right(), -1));
    }
    if (Screen.buttonsDown.contains(KeyEvent.VK_SPACE)) {
      // position.y += 0.1;
      jump();
    } else if (Screen.buttonsDown.contains(KeyEvent.VK_SHIFT)) {
      position.y -= 0.1;
    }

    if (Screen.buttonsDown.contains(KeyEvent.VK_UP)) {
      rotation.x += turnSensitivity;
    } else if (Screen.buttonsDown.contains(KeyEvent.VK_DOWN)) {
      rotation.x -= turnSensitivity;
    }

    if (Screen.buttonsDown.contains(KeyEvent.VK_RIGHT)) {
      rotation.y += turnSensitivity;
    } else if (Screen.buttonsDown.contains(KeyEvent.VK_LEFT)) {
      rotation.y -= turnSensitivity;
    }

    rotation = Vector3.makeAngle(rotation);
  }

  public void jump() {
    yVelocity = 0.7;
  }
}