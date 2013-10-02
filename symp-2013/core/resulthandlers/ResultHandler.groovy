public interface ResultHandler {
	public void done(CommandResultListener crl);
	public boolean isSynchronous();
	public boolean isDone();
}