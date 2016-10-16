package intecmd;

public interface CommandInterface {

	void in(String[] data);

	@Deprecated
	String out();

	String help();

}
