package logic;

import java.io.IOException;

public interface Step {

	public boolean next() throws IOException, InterruptedException;
}
