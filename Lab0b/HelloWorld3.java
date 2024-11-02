package Lab0b;
import java.util.Scanner;

public class HelloWorld3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner input; // This object represents lines of Strings to be converted from a stream of bytes
		String str;
		String locName;
		String xCrdStr;
		String yCrdStr;
		double xCrd; // its x-coordinate
		double yCrd; // its y-coordinate
		Point aPoint; // point object that represents the location of interest
		boolean proceed;
		proceed = true;
		
		input = new Scanner(System.in); // System.in is a stream of bytes coming from the keyboard
		while (proceed) { // the rest will be repeated until the "proceed" variable turns false

			System.out.println("Creating a new location (y/n): ");
			str = input.nextLine(); // the first line of Strings is read

			if (str.equals("y")) { // the user wants to create a new location.

				System.out.println("Name? : ");
				locName = input.nextLine(); // the second line of Strings is read

				if (locName.equals("")) { // no input from the user
					System.out.println("Goodbye");
					System.exit(1);
				}

				else {

					System.out.println("X Coordinate? : ");
					xCrdStr = input.nextLine(); // the third line of Strings is read
					xCrd = Double.parseDouble(xCrdStr); // x-coordinate. parseDouble method converts a String to a double

					if (xCrdStr.equals("")) { // no input from the user
						System.out.println("Goodbye");
						System.exit(1);
					} else {
						System.out.println("Y Coordinate? : ");
						yCrdStr = input.nextLine(); // the forth line of Strings is read
						yCrd = Double.parseDouble(yCrdStr); // y-coordinate.

						if (yCrdStr.equals("")) { // no input from the user
							System.out.println("Goodbye");
							System.exit(1);
						} else {

							aPoint = new Point(locName, xCrd, yCrd); // a point object is instantiated
							// compute and show the summary of the location
							System.out.println(locName
									+ "�s distance from origin"
									+ aPoint.getDistanceFromOrigin());
							System.out.println(locName
									+ "�s distance to origin"
									+ aPoint.getDistanceToOrigin());
							System.out.println(locName
									+ "�s direction from origin"
									+ aPoint.getDirectionFromOrigin());
							System.out.println(locName
									+ "�s direction to origin"
									+ aPoint.getDirectionToOrigin());
							System.out.println();
							
							System.out
									.println("Change this location�s name? (y/n)");
							str = input.nextLine(); // the fifth line of Strings is read

							if (str.equals("y")) { // the user wants to change the location's name
								System.out
										.println("You can change the name only once. Type a new name. : ");
								locName = input.nextLine(); // the sixth line of Strings is read

								if (locName.equals("")) { // no input from the user
									System.out.println("Goodbye");
									System.exit(1);
								} else {
									aPoint.setName(locName); // aPoint gets renamed
									// compute and show the summary of the location
									System.out.println(locName
											+ "�s distance from origin"
											+ aPoint.getDistanceFromOrigin());
									System.out.println(locName
											+ "�s distance to origin"
											+ aPoint.getDistanceToOrigin());
									System.out.println(locName
											+ "�s direction from origin"
											+ aPoint.getDirectionFromOrigin());
									System.out.println(locName
											+ "�s direction to origin"
											+ aPoint.getDirectionToOrigin());
									System.out.println();
								}
							}
						}

					}

				}

			} else { // the user doesn't wantn to create a new location.
				proceed = false;
				System.out.println("Goodbye");

			}
		}

	}
}
