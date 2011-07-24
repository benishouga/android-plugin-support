package jp.benishouga.plugin;

public class PluginErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PluginErrorException() {
        super();
    }

    public PluginErrorException(String message) {
        super(message);
    }

    public PluginErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginErrorException(Throwable cause) {
        super(cause);
    }

}
