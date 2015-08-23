package by.epam.task5.exeption;

public class ShipExeption extends Exception {

	private static final long serialVersionUID = 5485911830849939048L;

	public ShipExeption(String message) {
		super(message);

	}

	public ShipExeption(String message, Throwable e) {
		super(message, e);
	}

	public ShipExeption(Throwable e){
		super(e);
	}

}
