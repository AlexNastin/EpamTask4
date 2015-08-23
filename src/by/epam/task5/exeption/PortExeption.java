package by.epam.task5.exeption;

public class PortExeption extends Exception {

	private static final long serialVersionUID = -8753409650925685212L;

	public PortExeption(String message) {
		super(message);

	}

	public PortExeption(String message, Throwable e) {
		super(message, e);
	}

	public PortExeption(Throwable e) {
		super(e);
	}

}
