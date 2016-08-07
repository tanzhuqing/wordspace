
public class SignedRequest {
	private FacebookApp app;
	private String rawSingnedRequest;
	private String payload;
	public SignedRequest(FacebookApp app, String rawSingnedRequest) {
		
		this.app = app;
		if (rawSingnedRequest==null) {
			return;
		}
		this.rawSingnedRequest = rawSingnedRequest;
		this.parse();
	}
	
	
	
	private void parse() {
		
	}

}
